package org.app.co.jp.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.com.UserException;
import org.app.co.jp.dao.OperationDao;
import org.app.co.jp.dao.OperationDataDao;
import org.app.co.jp.dao.OperationListDao;
import org.app.co.jp.dao.PageDao;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author a5062903
 *
 */
public class AutoOperationDataUtil {

	private Map<String, Map<String, String>> allValueMap = new LinkedHashMap<String, Map<String, String>>();
	
	private HSSFCellStyle headerStyle = null;
	
	private HSSFCellStyle dataStyle = null;
	
	/**
	 * @throws Exception
	 */
	public String createOperationData(String strOperationId, String strFilePath, int intExcelNo) throws Exception {
		
		createDataByOperationId(strOperationId);
		
		String strOutputPath = createExcelFile(strOperationId, strFilePath, intExcelNo);
		
		return strOutputPath;
	}
	
	/** *
	 * @throws UserException 
	 */
	private void createDataByOperationId(String strOperationId) throws UserException {
		Map<String, Map<String, Map<String, String>>> dataMap = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		
		OperationDao operationDao = new OperationDao();
		OperationListDao operationListDao = new OperationListDao();
		OperationDataDao dao = new OperationDataDao();
		
		List<Map<String, Object>> operationList = operationDao.searchList(strOperationId);
		
		if (operationList == null || operationList.isEmpty() ) {
			throw new UserException("The operation definition not exists. please set it again!");
		} else {
			
			for (Map<String, Object> map : operationList) {
				String strDataId = (String) map.get("DATA_ID");
				
				Map<String, Map<String, String>> valueMap = dao.search(strOperationId, strDataId);
				
				dataMap.put(strDataId, valueMap);
			}
		}
		
		String operationName = operationListDao.getOperationNameById(strOperationId);
		
		allValueMap.clear();
		
		Map<String, String> testSignal = new HashMap<String, String>();
		testSignal.put("TEST_SIGNAL_MARK", operationName);
		allValueMap.put("TEST_SIGNAL_MARK", testSignal);
		
		Iterator<String> dataIterator = dataMap.keySet().iterator();
		while (dataIterator.hasNext()) {
			String strDataId = dataIterator.next().toString();
			
			int strNumber = dao.getDataNumber(strOperationId, strDataId);

			setTableInfo(strOperationId, strDataId, dataMap.get(strDataId), strNumber);
		}
	}
	
	/**
	 *
	 * 
	 * @param patternId
	 * @param strTableId
	 * @param strTypeId
	 * @param tableName
	 * @param valueMap
	 * @param before
	 */
	private void setTableInfo(String operationId, String strDataId, Map<String, Map<String, String>> fieldMap, int iNumber) {
		if (fieldMap != null && !fieldMap.isEmpty()) {
		    
			for (int i = 0; i < iNumber; i++) {
				Iterator<String> keyIterator = fieldMap.keySet().iterator();
				
				Map<String, String> selfMap = new LinkedHashMap<String, String>();
				
				while(keyIterator.hasNext()) {
					String strField = keyIterator.next().toString();
					String value = "";
					Map<String, String> propertyMap = fieldMap.get(strField);
					String strKotei = (String)propertyMap.get("FIELD_FIRM");
					if (!Check.isNull(strKotei)) {
						value = strKotei;
					} else {
						String strRadom = (String)propertyMap.get("FIELD_RADOM");
						if (!Check.isNull(strRadom)) {
							value = getRadomValue(strRadom);
						} else {
							String strPrefix = (String)propertyMap.get("FIELD_PREFIX");
							String strLength = (String)propertyMap.get("FIELD_LENGTH");
							value = getAutoCreateValue(strDataId, strField, strPrefix, strLength);
						}
					}
					selfMap.put(strField, value);
				}
				allValueMap.put(strDataId.concat("_").concat(String.valueOf(i + 1)), selfMap);
			}
		}
	}
    
    /**
     *
     * @param strRadomString
     * @return
     */
    public String getRadomValue(String strRadomString) {
    	String []radomList = strRadomString.split(",");
    	//
    	int index = (int)(Math.random() * radomList.length);
    	if (index == radomList.length) {
    		index = index - 1;
    	}
    	
    	return radomList[index];
    }
    
    /**
     *
     * @param length
     * @return
     */
    public String getRadomValue(int length) {
    	String selectString = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9";
    	StringBuffer result = new StringBuffer();
    	for (int i = 0; i < length; i++) {
    		result.append(getRadomValue(selectString));
    	}
    	return result.toString();
    }
    
    /**
     *
     * @param strTable
     * @param strColumn
     * @param strPrefix
     * @param strLength
     * @return
     */
    private String getAutoCreateValue(String strDataId, String strFieldId, String strPrefix, String strLength) {
    	PageDao pegeDao = new PageDao();
    	//
    	String value = "";
    	int length = 0;
    	
    	String [] strKeyInfo = strFieldId.split("-");
    	
    	
    	Map<String, String> map = pegeDao.getFiledPropeties(strKeyInfo[2], strKeyInfo[3]);
    	
    	if (!Check.isNull(strLength)) {
    		length = Integer.parseInt(strLength);
    	} else {
    		String strTempLength = map.get("length");
    		if (!Check.isNull(strTempLength)) {
    			length = Integer.parseInt(strTempLength);
    		} else {
    			length = (int)(Math.random() * 10);
    		}
    	}

    	String strType = map.get("type").toString();
    	if (CommonConstant.DATA_TYPE_CHAR.equals(strType) 
    			|| CommonConstant.DATA_TYPE_VARCHAR2.equals(strType) 
    			|| CommonConstant.DATA_TYPE_NUMBER.equals(strType)) {
    		//
    		if (CommonConstant.DATA_TYPE_NUMBER.equals(strType)) {
    			
    			String strPrecision = map.get("precision");
    			String strScale = map.get("precision");
    			
    			if (Check.isNull(strPrecision)) {
    				strPrecision = String.valueOf(length);
    			}
    			
    			if (Check.isNull(strScale)) {
    				strScale = "0";
    			}
    			
    			//
    			long lPrecision = (long)(Math.random() * Math.pow(10, Integer.parseInt(strPrecision)));
    			long lScale = (long)(Math.random() * Math.pow(10, Integer.parseInt(strScale)));
    			if (lScale != 0) {
    				value = String.valueOf(lPrecision).concat(".").concat(String.valueOf(lScale));
    			} else {
    				value = String.valueOf(lPrecision);
    			}
    		} else {
    			if (!Check.isNull(strPrefix)) {
    				if (strPrefix.length() >= length) {
    					value = strPrefix.substring(0, length);
    				} else {
    					value = strPrefix.concat(getRadomValue(length - strPrefix.length()));
    				}
    			} else {
    				value = getRadomValue(length);
    			}
    		}
    	} else if (strType.indexOf(CommonConstant.DATA_TYPE_TIMESTAMP) > 0) {
    		value = "sysdate";
    	} else {
			if (!Check.isNull(strPrefix)) {
				if (strPrefix.length() >= length) {
					value = strPrefix.substring(0, length);
				} else {
					value = strPrefix.concat(getRadomValue(length - strPrefix.length()));
				}
			} else {
				value = getRadomValue(length);
			}
    	}

    	return value;
    }

	/**
	 */
	private String createExcelFile(String strOperationId, String saveFilePath, int intExcelNo) throws Exception {
		int iRowNow = 1;
		HSSFWorkbook book = new HSSFWorkbook();
		book.createSheet("OperationData");
		book.setSheetName(0, "OperationData");
		HSSFSheet sheet = book.getSheetAt(0);
		
		boolean blnPrintHeader = false;
		boolean blnStart = false;
		
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
			} else {
				if (!blnPrintHeader) {
					OperationDataDao dataDao = new OperationDataDao();
					OperationDao operationDao = new OperationDao();
					String strScenarioId = operationDao.getScenatioIdByOperationId(strOperationId);
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
						
						iColumn++;
						
						sheet.autoSizeColumn(iColumn);
					}
					
					blnPrintHeader = true;
				}
				
				Map<String, String> fieldValueMap = allValueMap.get(key);
				
				int iColumn = 1;
				sheetDataRow = getRow(sheet, iRowNow);
				iRowNow++;
				
				if (!blnStart) {
					HSSFCell valueStartCell = getCell(sheetDataRow, 0);
					valueStartCell.setCellValue("DATA_START");
					blnStart= true;
				}
				
				
				for (String strValue : fieldValueMap.values()) {
					HSSFCell valueCell = getCell(sheetDataRow, iColumn);
					
					HSSFCellStyle style = getDataStyle(book);
					valueCell.setCellStyle(style);
					
					valueCell.setCellValue(strValue);
					iColumn++;
				}
			}
		}
		
		if (sheetDataRow != null) {
			HSSFCell valueEndCell = getCell(sheetDataRow, 0);
			valueEndCell.setCellValue(valueEndCell.getStringCellValue() + "DATA_END");
		}
		
		sheet.autoSizeColumn(0);
		
        Date now = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String ymd = date.format(now);
        SimpleDateFormat time = new SimpleDateFormat("HHmmss");
        String hhmmss = time.format(now);
        
        String strEvidencePath = saveFilePath.concat("data_").concat(ymd).concat(hhmmss).concat("_").concat(String.valueOf(intExcelNo)).concat(".xls");
		
		File file = new File(strEvidencePath);
		OutputStream out = new FileOutputStream(file);
		book.write(out);
		out.close();
		
		return strEvidencePath;
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
	 *
	 * @param book
	 * @return
	 */
	private HSSFCellStyle getDataStyle(HSSFWorkbook book) {
		if (dataStyle == null) {
			dataStyle = book.createCellStyle();
			dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		} 
		return dataStyle;
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
