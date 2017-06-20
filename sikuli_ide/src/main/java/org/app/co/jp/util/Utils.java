/*
 * @(#) Utils.java
 * Product :
 * Version : 1.0 (2004/07/01)
 * Copyright 2005- by Renesas Technology Corp., All rights reserved.
 */

package org.app.co.jp.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.security.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.com.ScheduleCommon;
import org.app.co.jp.com.UserException;
import org.app.co.jp.dao.JobListDao;
import org.app.co.jp.dao.OperationDao;
import org.app.co.jp.dao.OperationDataDao;
import org.app.co.jp.util.bean.DefaultComboBoxModel;
import org.app.co.jp.util.bean.SelectBean;

/**
 */
public abstract class Utils {
	
	static BasicLogger logger = BasicLogger.getLogger();

	public static final String FLG_Y = "Y";

	public static final String FLG_N = "N";

	public static final String FLG_1 = "1";

	public static final String FLG_0 = "0";
	
	public static List<Window> allWindow = new ArrayList<Window>();
	
	public static List<Boolean> allStatus = new ArrayList<Boolean>();
	
	public static int intExcelNo = 1;
	
	public static void addWindow (Window window) {
		if(allWindow.indexOf(window) < 0) {
			allWindow.add(window);
		}
	}
	
	public static void removeWindow (Window window) {
		if(allWindow.indexOf(window) >= 0) {
			allWindow.remove(window);
		}
	}
	
	public static void clearWindow () {
		allWindow.clear();
	}
	
	public static void showAgainWindow () {
		for (Window window : allWindow) {
			window.setVisible(allStatus.get(allWindow.indexOf(window)));
		}
	}
	
	public static void hidenWindow() {
		allStatus.clear();
		for (Window window : allWindow) {
			allStatus.add(window.isVisible());
			window.setVisible(false);
		}
	}

	/**
	 */
	public static String getEncryptionCode(byte[] orignal) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		return toHexString(md.digest(orignal));
	}

	/**
	 */
	public static String pad(String str, char padChar, int count) {
		StringBuffer buf = new StringBuffer(str);
		for (int i = getLength(str); i < count; i++) {
			buf.append(padChar);
		}
		return buf.toString();
	}

	/**
	 */
	public static String padFront(String str, char padChar, int count) {
		StringBuffer buf = new StringBuffer(str);
		for (int i = getLength(str); i < count; i++) {
			buf.insert(0, padChar);
		}
		return buf.toString();
	}

	/**
	 */
	public static String replaceString(String str1, String str2, String str3) {
		StringBuffer buffer = new StringBuffer();
		int index = str1.indexOf(str2);

		if (index == -1) {
			return str1;
		}

		buffer.append(str1.substring(0, index));
		buffer.append(str3);
		buffer.append(str1.substring(index + str2.length()));
		return buffer.toString();
	}

	/**
	 */
	public static String getIntegralPart(String str) {
		if (!Check.isNumericalValue(str)) {
			return null;
		}
		int index = str.indexOf(".");
		if (index == -1) {
			return str;
		}
		return str.substring(0, index);
	}

	/**
	 */
	public static String getDecimalPart(String str) {
		if (!Check.isNumericalValue(str)) {
			return null;
		}
		int index = str.indexOf(".");
		if (index == -1) {
			return "0";
		}
		return str.substring(index + 1);
	}

	/**
	 */
	public static String toHexString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int b = bytes[i] & 0xFF;
			int hi = b >> 4;
			int lo = b & 0x0F;
			buf.append(hexDigits[hi]);
			buf.append(hexDigits[lo]);
		}
		return buf.toString();
	}

	/**
	 */
	protected static final char[] hexDigits = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F'
	};

	/**
	 */
	public static String formatNumber(String value, String pattern, int fractionDigits){
		for (int i = 0; value != null && i < value.length(); i++) {
			if (value.charAt(i) == '\u0045'
				|| value.charAt(i) == '\u0065') {
				return value;
			}
		}
		try{
			DecimalFormat df = new DecimalFormat(pattern);
			df.setMaximumFractionDigits(fractionDigits);
			df.setMinimumFractionDigits(fractionDigits);
			return df.format(Double.parseDouble(value));
		}catch (Exception e){
			try {
				pattern = pattern.replaceAll("\\.","/");
				pattern = pattern.replaceAll(",","\\.");
				pattern = pattern.replaceAll("/",",");
				DecimalFormat df = new DecimalFormat(pattern);
				df.setMaximumFractionDigits(fractionDigits);
				df.setMinimumFractionDigits(fractionDigits);
				String val = df.format(Double.parseDouble(value));
				val = val.replaceAll("\\.","/");
				val = val.replaceAll(",","\\.");
				val = val.replaceAll("/",",");
				return val;				
			}catch (Exception pe){
				return value;
			}
		}
	}

	/**
	 */
	public static String getNumber(String value){
		if(Check.isNull(value)){
			return value;
		}
		for (int i = 0; value != null && i < value.length(); i++) {
			if (value.charAt(i) == '\u0045'
				|| value.charAt(i) == '\u0065') {
				return value;
			}
		}
		try {
			BigDecimal num =  new BigDecimal(value);
			return num.toString();
		} catch (NumberFormatException e) {
			return value;
		}
	}

	/**
	 */
	public static String getNumber(String value, String pattern){
		if(Check.isNull(value)){
			return value;
		}
		String chkStr = new String(value);
		
		if(pattern.indexOf(".")<pattern.indexOf(",")){
			chkStr = chkStr.replaceAll("\\.","#");
			chkStr = chkStr.replaceAll(",","\\.");
			chkStr = chkStr.replaceAll("#",",");
		}

		if(Pattern.matches("^[+-]?[0-9][0-9,]*\\.?[0-9]*", chkStr)
			&& Pattern.matches(".*[0-9]$", chkStr)
			&& !Pattern.matches(".*((,.?.?,))+.*", chkStr)
			&& !Pattern.matches(".*,.?.$", chkStr)){
			
			return getNumber(chkStr.replaceAll(",",""));
			
		}else if(Pattern.matches("^[+-]?\\.[0-9][0-9]*", chkStr)){
			return getNumber(chkStr);
			
		}else{
			return value;
		}
	}
	
	/**
	 */
	public static String getUpperCase(String value) {
		if (value == null) {
			return value;
		}
		char chArg[] = value.toCharArray();
		char variable[] = new char[chArg.length];
		for (int i = 0; i < chArg.length; i++) {
			variable[i] = Character.toUpperCase(chArg[i]);
		}
		return new String(variable).trim();
	}

	/**
	 */
	public static String deleteNewParagraph(String str){
		
		if (str.lastIndexOf("\r") != -1 || str.lastIndexOf("\n") != -1 || str.lastIndexOf("\r\n") != -1) {
			str = str.replaceAll("\r","");
			str = str.replaceAll("\n","");
			str = str.replaceAll("\r\n","");
		}
		
		return str;
	}

	/**
	 */
	public static int getLength(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		int ret = 0;
		for (int i = 0; i < str.length(); i++) {
			ret += getCharLength(str.charAt(i));
		}
		return ret;
	}

	/**
	 */
	public static String[] separateString(String str, int separateTerm) {
		if (str == null) return null;

		String[] separateStr = new String[1];

		StringBuffer buffer = new StringBuffer(separateTerm);
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			int intByte = getCharLength(c);
			if (count + intByte > separateTerm) {
				count = 0;
				int size = separateStr.length;
				String[] newSeparateStr = new String[size + 1];
				System.arraycopy(separateStr, 0, newSeparateStr, 0, size);
				newSeparateStr[size - 1] = buffer.toString();
				separateStr = newSeparateStr;
				buffer = new StringBuffer(separateTerm);
			}
			count += intByte;
			buffer.append(c);
		}
		separateStr[separateStr.length - 1] = buffer.toString();

		return separateStr;
	}

	/**
	 */
	public static int getCharLength(char c) {
		if (c <= '\u007f') {
			return 1;
		} else if (c >= '\uFF61' && c <= '\uFF9F') {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 */
	public static String replaceAll(String str, String pattern, String replacement) {

		String tmp = str;
		StringBuffer buf = new StringBuffer();

		int iTmp = tmp.indexOf(pattern);

		while (iTmp > -1) {
			buf.append(tmp.substring(0, iTmp));
			buf.append(replacement);
			tmp = tmp.substring(iTmp + pattern.length());
			iTmp = tmp.indexOf(pattern);
		}

		buf.append(tmp);

		return buf.toString();
	}

	/**
	 */
	public static String convertFlg(String value) {

		if (value.equals(FLG_0)) {
			return FLG_N;
		} else if (value.equals(FLG_1)) {
			return FLG_Y;
		}
		return "";
	}

	/**
	 */
	public static String reconvertFlg(String value) {

		if (value.equals(FLG_N)) {
			return FLG_0;
		} else if (value.equals(FLG_Y)) {
			return FLG_1;
		} else if (value.equals(FLG_0)) {
			return FLG_0;
		} else if (value.equals(FLG_1)) {
			return FLG_1;
		}
		return "";
	}
	
	/** 
	 */
	public static void deleteDirectory(String strDirectory) {
		File file = new File(strDirectory);
		if (file.isDirectory()) {
			File []fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					deleteDirectory(fileList[i].getAbsolutePath());
				} else {
					fileList[i].delete();
				}
			}
			file.delete();
		} else {
			if (file.isFile()) {
				file.delete();
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param strFromFolder
	 * @param strToFolder
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void copyFileByFolder(String strFromFolder, String strToFolder, String strFromId, String strToId) throws FileNotFoundException, IOException {
		
		File fromFolder = new File(strFromFolder);
		File toFolder = new File(strToFolder);
		
		if (!toFolder.exists()) {
			toFolder.mkdirs();
		}
		
		byte [] cache = new byte[1024];
		
		for (File file : fromFolder.listFiles()) {
			try (FileInputStream input = new FileInputStream(file)) {
				File outputFile = new File(strToFolder.concat("////").concat(file.getName().replaceAll(strFromId, strToId)));
				try (FileOutputStream out = new FileOutputStream(outputFile)) {
					int i = 0;
					while ((i = input.read(cache)) > 0) {
						out.write(cache, 0, i);
					}
				}
				
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param strFromFile
	 * @param strToFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void copyFile(String strFromFile, String strToFile) throws FileNotFoundException, IOException {

		byte [] cache = new byte[1024];

		try (FileInputStream input = new FileInputStream(new File(strFromFile))) {
			try (FileOutputStream out = new FileOutputStream(new File(strToFile))) {
				int i = 0;
				while ((i = input.read(cache)) > 0) {
					out.write(cache, 0, i);
				}
			}

		}
	}

	/**
	 * create 'try finally' function 
	 * 
	 * @param strPythonPath
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static String addOuterDealPython(String strPythonPath, String[] argv) throws FileNotFoundException, IOException {
		// temp path
		String tempPath = strPythonPath.substring(0, strPythonPath.lastIndexOf(".")).concat("temp").concat(".py");
		
		String strEndMail = "";
		String strErrorMail = "";
		
    	if (argv != null && argv.length > 0) {
    		
    		if (argv.length > 2) {
    			strEndMail = argv[2];
    		}
    		
    		if (argv.length > 3) {
    			strErrorMail = argv[3];
    		}
    	}

		try {
			try (FileInputStream input = new FileInputStream(strPythonPath); BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
				try (FileOutputStream out = new FileOutputStream(tempPath); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out))) {
					// header
					bw.write("import threading");
					bw.newLine();
					bw.newLine();
					bw.write("def wait_close_pop():");
					bw.newLine();
					bw.write("    captureNow()");
					bw.newLine();
					bw.write("    wait(1)");
					bw.newLine();
					bw.write("    type(Key.ENTER)");
					
					bw.newLine();
					bw.newLine();
					
					bw.write("if Env.isLockOn(Key.CAPS_LOCK):");
					bw.newLine();
					bw.write("    type(Key.CAPS_LOCK)");
					bw.newLine();
					
					bw.newLine();
					bw.write("try:");
					bw.newLine();
					bw.write("    initEvidence()");
					bw.newLine();

					// content
					String line = "";
					while ((line = br.readLine()) != null) {
						if (hasExtendOperation(line)) {
							analysisCommand(line, argv, bw, strPythonPath);
						} else {
							bw.write("    ".concat(line));
							bw.newLine();
						}
					}

					// footer
					bw.write("    timer = threading.Timer(3, wait_close_pop)");
					bw.newLine();
					bw.write("    timer.start()");
					bw.newLine();
					
					if (!Utils.isEmpty(strEndMail)) {
						bw.write("    mail_by_id('".concat(strEndMail).concat("')"));
						bw.newLine();
					}
					
					bw.write("    popup('the end! \\n this information will disappear after 3 seconds.')");
					bw.newLine();
					bw.write("except Exception, ex:");
					bw.newLine();
					bw.write("    timer = threading.Timer(3, wait_close_pop)");
					bw.newLine();
					bw.write("    timer.start()");
					bw.newLine();
					
					if (!Utils.isEmpty(strErrorMail)) {
						bw.write("    mail_by_id('".concat(strErrorMail).concat("')"));
						bw.newLine();
					}
					
					bw.write("    popup('error! \\n this information will disappear after 3 seconds.')");
					bw.newLine();
					bw.write("finally:");
					bw.newLine();
					bw.write("    endEvidence()");
					bw.newLine();
				}
			}
		} catch (Exception e) {
			logger.exception(e);
			Utils.deleteDirectory(tempPath);
			
			try (FileOutputStream out = new FileOutputStream(tempPath); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out))) {
				bw.write("popup('" + e.getMessage() + "')");
			}
		}

		return tempPath;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}
		if ("".equals(str) || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFileExists(String str) {
		if (str == null) {
			return false;
		}
		if ("".equals(str) || "".equals(str.trim())) {
			return false;
		}
		if (!(new File(str)).exists()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static DefaultComboBoxModel getComponentList() {
		
		Vector<SelectBean> selectList = new Vector<SelectBean>();
		SelectBean blank = new SelectBean();
		blank.setCode("");
		blank.setName("");
		selectList.add(blank);
		
		String componentList[] = CommonConstant.OPTION_COMPONENT_TYPE.split(",");
		
		for (String strComp : componentList) {
			SelectBean bean = new SelectBean();
			bean.setCode(strComp);
			bean.setName(strComp);
			selectList.add(bean);
		}
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(selectList);
		
		return model;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static DefaultComboBoxModel getOperationList() {
		
		
		String componentList[] = CommonConstant.OPTION_OPERATION.split(",");
		
		Vector<SelectBean> selectList = new Vector<SelectBean>();
		SelectBean blank = new SelectBean();
		blank.setCode("");
		blank.setName("");
		selectList.add(blank);
		
		for (String strComp : componentList) {
			SelectBean bean = new SelectBean();
			bean.setCode(strComp);
			bean.setName(strComp);
			selectList.add(bean);
		}
		DefaultComboBoxModel model = new DefaultComboBoxModel(selectList);
		
		return model;
	}
	

    public static void initDisplaySchema () {
        //
        try {
            //
            Font lblFont = new Font(null, Font.BOLD, 11);
            Font btnFont = new Font(null, Font.BOLD, 10);
            Font chkFont = new Font(null, Font.BOLD, 10);

            //
            Border lblBorder = BorderFactory.createLineBorder(Color.BLACK);

            // panel
            //UIManager.put("Panel.background", new Color(200,255,255));

            UIManager.put("Button.font", btnFont);
            //UIManager.put("Button.background", new Color(236,198,236));

            UIManager.put("Label.font", lblFont);
            UIManager.put("Label.border", lblBorder);

            UIManager.put("CheckBox.font", chkFont);
//            UIManager.put("CheckBox.background", new Color(200,255,255));

            UIManager.put("ComboBox.font", chkFont);
//            UIManager.put("ComboBox.background", new Color(200,255,255));

            //
            UIManager.put("TextField.disabletextcolor", Color.GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isTextOperation(String strOperation, String strCommand) {
    	
    	if (isEmpty(strCommand)) {
    		if ("type".equals(strOperation) || "paste".equals(strOperation)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public static boolean isBlockStartOperation(String strOperation) {
    	
    	if ("if exists".equals(strOperation) || "if not exists".equals(strOperation) || "while exists".equals(strOperation) || "while not exists".equals(strOperation)) {
    		return true;
    	}
    	
    	return false;
    }
    
    public static boolean isBlockEndOperation(String strOperation) {
    	
    	if ("block end".equals(strOperation)) {
    		return true;
    	}
    	
    	return false;
    }
    
    public static boolean hasExtendOperation(String strLine) {
    	
    	if (Utils.isEmpty(strLine)) {
    		return false;
    	}
    	
    	if (strLine.indexOf("executeOperationId") >= 0) {
    		return true;
    	}
    	
    	return false;
    }
    
    public static void analysisCommand(String strLine , String[] argv, BufferedWriter bw, String strPythonPath) throws Exception {
    	
    	String evidencePath = "";
    	
    	boolean testExec = false;
    	
    	if (argv != null && argv.length > 0) {
    		evidencePath = argv[0];
    		
    		if (argv.length > 1) {
    			testExec = Boolean.parseBoolean(argv[1]);
    		}
    	}
    	
    	int strLastPreBlankIndex = 0;
    	
    	for (int i = 0; i < strLine.length(); i++) {
    		if (String.valueOf(strLine.charAt(i)).equals(" ")) {
    			strLastPreBlankIndex = i + 1;
    		} else {
    			break;
    		}
    	}
    	
    	String strPreBlankString = "    ".concat(strLine.substring(0, strLastPreBlankIndex));
    	
    	String strOperationId = getOperationId(strLine);
    	String strParameterExcelFile = getExcelFileName(strLine);
    	
    	boolean blnCheckBefore = checkOperationId(strOperationId);
    	
    	boolean blnInitFile = false;
    	
    	if (!blnCheckBefore) {
    		throw new UserException("The operation definition has changed. please set it again!");
    	}
    	
    	String strExcelPath = "";
    	
    	if (isEmpty(strParameterExcelFile)) {
    		AutoOperationDataUtil dataUtil = new AutoOperationDataUtil();
    		strExcelPath = dataUtil.createOperationData(strOperationId, evidencePath, intExcelNo);
    		intExcelNo++;
    	} else {
    		strExcelPath = strParameterExcelFile;
    		blnInitFile = true;
    	}
   	
    	List<Map<String, String>> operationDefinitionList = getOperationDefinitionData(strOperationId);
    	
    	CreateOperationScript(strOperationId, strPreBlankString, strExcelPath, operationDefinitionList, blnInitFile, bw, strPythonPath, testExec);
    }
    
    /**
     * extract the operation id from the command
     * 
     * @param strLine
     * @return
     * @throws UserException 
     */
    public static String getOperationId(String strLine) throws UserException {
    	String strOperationID = "";
    	strLine = strLine.replaceAll(" ", "");
    	
    	String [] divValue = strLine.split("'");
    	if (divValue.length != 3 && divValue.length != 5) {
    		throw new UserException("Operation's parameter is not right! :" + strLine);
    	}
    	
    	strOperationID = strLine.substring(strLine.indexOf("\'") + 1, strLine.indexOf("\'", strLine.indexOf("\'") + 1));
    	
    	return strOperationID;
    	
    }
    
    /**
     * extract the parameter excel path from the command
     * 
     * @param strLine
     * @return
     * @throws UserException 
     */
    public static String getExcelFileName(String strLine) throws UserException {
    	String ExcelFileName = "";
    	strLine = strLine.replaceAll(" ", "");
    	
    	String [] divValue = strLine.split("'");
    	if (divValue.length != 3 && divValue.length != 5) {
    		throw new UserException("Operation's parameter is not right! :" + strLine);
    	}
    	
    	if (divValue.length == 5) {
    		ExcelFileName = divValue[3];
    	}
    	
    	return ExcelFileName;
    }
    
    /**
     * 
     * 
     * @param strOperationId
     * @return
     */
    @SuppressWarnings("unchecked")
	private static boolean checkOperationId(String strOperationId) {
    	
    	OperationDao operationDao = new OperationDao();
    	OperationDataDao operationDataDao = new OperationDataDao();
    	
    	List<Map<String, Object>> detailList= operationDao.searchAllInfoList(strOperationId);
    	
    	Set<String> baseKeySet = new HashSet<String>();
    	String strScenarioId = "";
    	
    	List<Map<String, Set<String>>> checkKeySetList = new  ArrayList<Map<String, Set<String>>>();
    	
    	for (Map<String, Object> map : detailList) {
    		Map<String, Map<String, String>> ruleMap = (Map<String, Map<String, String>>) map.get("DATA_RULES");
    		String strDataId = String.valueOf(map.get("DATA_ID"));
    		strScenarioId = String.valueOf(map.get("SCENARIO_ID"));
    		
    		Set<String> keySet = new HashSet<String>(ruleMap.keySet());
    		
    		Map<String, Set<String>> keyMap= new HashMap<String, Set<String>> ();
    		
    		keyMap.put(strDataId, keySet);
    		
    		checkKeySetList.add(keyMap);
    	}
    	
    	try {
			List<Map<String, String>> baseList = operationDataDao.getColumns(strScenarioId);
			
			for (Map<String, String> map : baseList) {
				String strCode = strScenarioId.concat("-").concat(map.get("STEP_ID")).concat("-").concat(map.get("PAGE_ID")).concat("-").concat(map.get("FIELD_ID"));
				baseKeySet.add(strCode);
			}
			
			for (Map<String, Set<String>> dataMap : checkKeySetList) {
				for (Set<String> checkSet : dataMap.values() ) {
					if (!checkSet.containsAll(baseKeySet) || !baseKeySet.containsAll(checkSet)) {
						return false;
					};
				}
			}
		} catch (Exception e) {
			logger.exception(e);
			e.printStackTrace();
		}
    	return true;
    }
    
    /**
     * 
     * 
     * @param strOperationId
     * @return
     * @throws Exception 
     */
	private static List<Map<String, String>> getOperationDefinitionData(String strOperationId) throws Exception {
    	
    	OperationDao operationDao = new OperationDao();
    	
    	return operationDao.getDefinitionInfoByOperationId(strOperationId);
    }
    
    private static boolean CreateOperationScript(String strOperationId, String strPreBlankString, String strExcelPath, List<Map<String, String>> operationDefinitionList, boolean blnInitFile, BufferedWriter bw, String strPythonPath, boolean testExec) throws Exception {
    	
    	int intLoop = 0;
    	
    	File inputFile = new File(strExcelPath);
    	
    	if (!inputFile.exists() || !inputFile.isFile()) {
    		throw new UserException("The file is not exists:" + strExcelPath);
    	}
    	
    	POIFSFileSystem poiFileSystem = new POIFSFileSystem( new FileInputStream(new File(strExcelPath)));
    	
    	HSSFWorkbook book = new HSSFWorkbook(poiFileSystem);
    	
    	HSSFSheet dataSheet = book.getSheetAt(0);
    	int intLastRow = dataSheet.getLastRowNum();
    	
    	int intDataStartRow = 0;
    	int intDataEndRow = 0;
    	
    	for (int i = 0; i <= intLastRow; i++) {
    		
    		String strFirstString = "";
    		
    		HSSFRow row = dataSheet.getRow(i);
    		if (row != null) {
    			HSSFCell cell = row.getCell(0);
    			if (cell != null) {
    				strFirstString = cell.getStringCellValue();
    			}
    		}
    		
    		if (strFirstString.indexOf("DATA_START") >= 0) {
    			intDataStartRow = i;
    		}
    		
    		if (strFirstString.indexOf("DATA_END") >= 0) {
    			intDataEndRow = i;
    			break;
    		}
    	}
    	
    	if (blnInitFile) {
    		if (intDataStartRow == 0 || intDataEndRow == 0 || intDataStartRow > intDataEndRow) {
    			throw new UserException("The file's format is not right:" + strExcelPath);
    		}
    	} else {
    		if (intDataStartRow == 0 &&  intDataEndRow == 0) {
    			OperationDao operationDao = new OperationDao();
    			List<Map<String, Object>> result = operationDao.searchList(strOperationId);
    			for (Map<String, Object> map : result) {
    				String strNumber = (String)map.get("DATA_NUMBER");
    				intLoop = intLoop + Integer.parseInt(strNumber);
    			}
    		}
    	}
    	
    	intLoop = intDataEndRow - intDataStartRow + 1;
    	
    	bw.write(strPreBlankString.concat("import xlrd"));
    	bw.newLine();
    	bw.write(strPreBlankString.concat("data = xlrd.open_workbook('") + strExcelPath + "')");
    	bw.newLine();
    	bw.write(strPreBlankString.concat("sheet = data.sheets()[0]"));
    	bw.newLine();
    	
    	bw.write(strPreBlankString.concat("intDataStartRow = " + String.valueOf(intDataStartRow)));
    	bw.newLine();
    	
    	String strCommandLine = strPreBlankString.concat("for i in range(" + String.valueOf(intLoop) + "):");
    	bw.write(strCommandLine);
    	bw.newLine();
    	
    	strPreBlankString = "    ".concat(strPreBlankString);
    	
    	bw.write(strPreBlankString.concat("iColumn = 1"));
    	bw.newLine();
    	
    	
    	
    	String imgHolder = (new File(strPythonPath)).getParent().concat("/");
    	
    	Stack<Object> blockLength = new Stack<Object>();
    	
    	
    	for (Map<String, String> map : operationDefinitionList) {
    		
			String strOperation = map.get("OPERATION_TYPE");
			String strOperationValue = map.get("OPERATION_PIC_VALUE");
			String strOperationPath = map.get("OPERATION_PIC_PATH");
			String strOperationFile = map.get("OPERATION_PIC");
			String strOperationCapture = map.get("OPERATION_CAPTURE");
			String strOperationTestExec = map.get("OPERATION_TEST_EXEC");
			String strOperationCommand = map.get("OPERATION_COMMAND");
			
			File strImgFile = new File(imgHolder.concat(strOperationFile));
			
			// if image not in the image holder, move it. 
			copyFile(strOperationPath, strImgFile.getAbsolutePath());
			
			// if 
			if (testExec && !Utils.isEmpty(strOperationTestExec) && Boolean.valueOf(strOperationTestExec)) {
				strOperation = "hover";
			}
			
			// if the step is the input field, get the data from file
			if (Utils.isTextOperation(strOperation, strOperationCommand)) {
				if (blockLength.isEmpty()) {
					bw.write(strPreBlankString.concat("strOperationData = sheet.row(intDataStartRow + i)[iColumn].value"));
					bw.newLine();
				} else {
					bw.write(strPreBlankString.concat("strOperationData = sheet.row(intDataStartRow + i)[iColumn + " + getNowColumn(blockLength) + "].value"));
					bw.newLine();
					addOneColumnAtBlock(blockLength);
				}
				strCommandLine = strPreBlankString.concat(strOperation).concat("(").concat(strOperationValue.replace("\"", "'").replace(strOperationPath, strOperationFile)).concat(", strOperationData)");
				bw.write(strCommandLine);
				bw.newLine();
				
				if (Boolean.valueOf(strOperationCapture)) {
					bw.write(strPreBlankString.concat("captureNow()"));
					bw.newLine();
				}
				if (blockLength.isEmpty()) {
					bw.write(strPreBlankString.concat("iColumn = iColumn + 1"));
					bw.newLine();
				}
			} else {
				if (Utils.isBlockStartOperation(strOperation)) {
					// length
					if (blockLength.isEmpty()) {
						blockLength.add(new Long(0));
						addStartStack(blockLength);
					} else {
						addStartStack(blockLength);
					}
					// add block mark
					strCommandLine = strPreBlankString.concat(strOperation).concat("(").concat(strOperationValue.replace("\"", "'").replace(strOperationPath, strOperationFile)).concat(") :");
					// add block blank
					strPreBlankString = "    ".concat(strPreBlankString);
					
					bw.write(strCommandLine);
					bw.newLine();
					if (Boolean.valueOf(strOperationCapture)) {
						bw.write(strPreBlankString.concat("captureNow()"));
						bw.newLine();
					}
				} else if (Utils.isBlockEndOperation(strOperation)) {
					strPreBlankString = strPreBlankString.substring(4);
					
					removeStackAtEnd(blockLength, null);
					
					Object obj = blockLength.peek();
					if (obj instanceof Long) {
						bw.write(strPreBlankString.concat("iColumn = iColumn + ").concat(String.valueOf((Long)obj)));
						bw.newLine();
						
						// clear the block
						blockLength.pop();
					}
					
					
				} else {
					if (Utils.isEmpty(strOperationCommand)) {
						strCommandLine = strPreBlankString.concat(strOperation).concat("(").concat(strOperationValue.replace("\"", "'").replace(strOperationPath, strOperationFile)).concat(")");
						bw.write(strCommandLine);
						bw.newLine();
					} else {
						strCommandLine = strPreBlankString.concat(strOperation).concat("(").concat(strOperationCommand).concat(")");
						bw.write(strCommandLine);
						bw.newLine();
					}
					
					if (Boolean.valueOf(strOperationCapture)) {
						bw.write(strPreBlankString.concat("captureNow()"));
						bw.newLine();
					}
				}
			}
    	}
    	
    	return true;
    }
    
    @SuppressWarnings("unchecked")
	private static Stack<Object> addStartStack(Stack<Object> stack) {
    	
		Object obj = stack.peek();
		
		if (obj instanceof Stack) {
			addStartStack((Stack<Object>)obj);
		} else if (obj instanceof Long) {
			Stack<Object> stackTemp = new Stack<Object>();
			stackTemp.add(new Long((Long)stack.pop()));
			stack.add(stackTemp);
			return stack;
		}
		
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	private static void removeStackAtEnd(Stack<Object> stack, Stack<Object> parent) {
    	
		Object obj = stack.peek();
		
		if (obj instanceof Stack) {
			removeStackAtEnd((Stack<Object>)obj, stack);
		} else if (obj instanceof Long) {
			if (parent != null) {
				parent.pop();
				parent.add(obj);
			}
		}
		
    }
    
    @SuppressWarnings("unchecked")
	private static void addOneColumnAtBlock(Stack<Object> stack) {
		Object obj = stack.peek();
		
		if (obj instanceof Stack) {
			addOneColumnAtBlock((Stack<Object>)obj);
		} else if (obj instanceof Long) {
			Long value = (Long)obj;
			value = value  + 1L;
			stack.pop();
			stack.add(value);
		}
    }
    
    @SuppressWarnings("unchecked")
	private static String getNowColumn(Stack<Object> stack) {
		Object obj = stack.peek();
		String strReult = "";
		
		if (obj instanceof Stack) {
			strReult = getNowColumn((Stack<Object>)obj);
		} else if (obj instanceof Long) {
			return String.valueOf((Long)obj);
		}
		
		return strReult;
    }
    
    public static void addJob(String strScriptId, String strScriptName, String strScriptPath, String strEvidencePath, String strEndMail, String strErrorMail, String strDate) throws Exception {
    	// add job to XML
    	JobListDao dao = new JobListDao();
    	String strJobId = dao.createJobListId(strScriptId, strScriptName, strScriptPath, strEvidencePath, strEndMail, strErrorMail, strDate);
    	
    	// add job to schedule
    	ScheduleCommon.init();
    	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date strExecuteDate =  sdf.parse(strDate);
    	
    	ScheduleCommon.addToSchedule(strExecuteDate, strJobId, strScriptPath, strEvidencePath, false, strEndMail, strErrorMail);
    }
    
    @SuppressWarnings("deprecation")
	public static void initSchedule() throws Exception {
    	// get list
    	JobListDao dao = new JobListDao();
    	List<Map<String, String>> result = dao.searchList("");
    	
    	ScheduleCommon.init();
    	
    	// 
    	for (Map<String, String> map : result) {
    		// status 
    		if (!map.get("STATUS").equals("0")) {
    			continue;
    		}
    		
    		// time
    		String strDate = map.get("SCRIPT_DATE");
    		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date strExecuteDate = sdf.parse(strDate);
			
			Date nowDate = new Date();
			nowDate.setMinutes(nowDate.getMinutes() + 5);
			
			if (strExecuteDate.before(nowDate)) {
				dao.updateStatus(map.get("ID"), "3");
				continue;
			}
			
			// add job to schedule
			ScheduleCommon.addToSchedule(strExecuteDate, map.get("ID"), map.get("SCRIPT_PATH"), map.get("SCRIPT_EVIDENCE"), false, map.get("SCRIPT_END_MAIL"), map.get("SCRIPT_ERROR_MAIL"));
    	}
    }
}