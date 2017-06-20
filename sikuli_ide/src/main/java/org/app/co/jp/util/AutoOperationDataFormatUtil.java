package org.app.co.jp.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.app.co.jp.dao.OperationDataDao;

import java.io.*;
import java.util.*;

/**
 *
 * @author a5062903
 *
 */
public class AutoOperationDataFormatUtil {

	private Map<String, Map<String, String>> allValueMap = new LinkedHashMap<String, Map<String, String>>();
	
	private HSSFCellStyle headerStyle = null;
	
	/**
	 * @throws Exception
	 */
	public void createOperationDataFormat(String strScenarioId, String strOperationName, String strFilePath) throws Exception {
		
		Map<String, String> testSignal = new HashMap<String, String>();
		testSignal.put("TEST_SIGNAL_MARK", strOperationName);
		allValueMap.put("TEST_SIGNAL_MARK", testSignal);
		
		int iRowNow = 1;
		HSSFWorkbook book = new HSSFWorkbook();
		book.createSheet("OperationData");
		book.setSheetName(0, "OperationData");
		HSSFSheet sheet = book.getSheetAt(0);
		
		boolean blnPrintHeader = false;
		
		Set<String> keySet = allValueMap.keySet();
		
		HSSFRow sheetDataRow = null;
		
		for (String key : keySet) {
			HSSFRow sheetSignalRow = getRow(sheet, iRowNow);
			
			if ("TEST_SIGNAL_MARK".equals(key)) {
				HSSFCell cell = getCell(sheetSignalRow, 1);
				
				HSSFFont font = book.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				cell.getCellStyle().setFont(font);

				cell.setCellValue(allValueMap.get("TEST_SIGNAL_MARK").get("TEST_SIGNAL_MARK"));
				iRowNow++;
			} 

			if (!blnPrintHeader) {
				OperationDataDao dataDao = new OperationDataDao();
				List<Map<String, String>> fieldNamesList = dataDao.getColumns(strScenarioId);
				HSSFRow sheetPageRow = getRow(sheet, iRowNow);
				iRowNow++;
				HSSFRow sheetFiledRow = getRow(sheet, iRowNow);
				iRowNow++;
				int iColumn = 1;
				String strPageNameOld = "";
				for (Map<String, String> fieldMap : fieldNamesList) {
					HSSFCell pageCell = getCell(sheetPageRow, iColumn);
					String strPageName = fieldMap.get("PAGE_NAME");
					if (!strPageName.equals(strPageNameOld)) {

						HSSFCellStyle style = getHeadStyle(book);
						pageCell.setCellStyle(style);

						pageCell.setCellValue(strPageName);
						strPageNameOld = strPageName;
					}

					HSSFCell fieldCell = getCell(sheetFiledRow, iColumn);
					String strFieldName = fieldMap.get("FIELD_NAME");

					HSSFCellStyle style = getHeadStyle(book);
					fieldCell.setCellStyle(style);

					fieldCell.setCellValue(strFieldName);
					
					sheet.autoSizeColumn(iColumn);

					iColumn++;
				}

				blnPrintHeader = true;
			}
		}
		
		sheetDataRow = getRow(sheet, iRowNow);
		iRowNow++;

		HSSFCell valueStartCell = getCell(sheetDataRow, 0);
		valueStartCell.setCellValue("DATA_START");
		
		sheet.autoSizeColumn(0);

		sheetDataRow = getRow(sheet, iRowNow);
		iRowNow++;
		HSSFCell valueEndCell = getCell(sheetDataRow, 0);
		valueEndCell.setCellValue(valueEndCell.getStringCellValue() + "DATA_END");
		
		File file = new File(strFilePath);
		OutputStream out = new FileOutputStream(file);
		book.write(out);
		out.close();
	}

	/**
	 */
	private HSSFCellStyle getHeadStyle(HSSFWorkbook book) {
		if (headerStyle == null) {
			headerStyle = book.createCellStyle();
			headerStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.PALE_BLUE.index);
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		} 
		return headerStyle;
	}
	
	/**
	 */
	public boolean isNullOrBlankString (String strValue) {
		if (strValue == null || strValue.replace(' ', ' ').trim().equals("")) {
			return true;
		}
		return false;
	}
	
	/**
	 *
	 */
	private HSSFRow getRow(HSSFSheet sheet, int iRow) {
		HSSFRow result = null;
		result = sheet.getRow(iRow);
		if (result == null) {
			result = sheet.createRow(iRow);
		}
		return result;
	}
	
	/**
	 *
	 * @param row
	 * @param iColumn
	 */
	@SuppressWarnings("deprecation")
	private HSSFCell getCell(HSSFRow row, int iColumn) {
		HSSFCell result = null;
		result = row.getCell((short)iColumn);
		if (result == null) {
			result = row.createCell((short)iColumn);
			result.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
		return result;
	}
}
