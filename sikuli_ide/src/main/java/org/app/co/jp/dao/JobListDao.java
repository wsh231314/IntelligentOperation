package org.app.co.jp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.app.co.jp.com.BaseDao;
import org.app.co.jp.com.ComDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.XMLUtils;

public class JobListDao extends BaseDao{

	//
	public static final String JOB_LIST_XML = "JOB_LIST.xml";
	
	BasicLogger logger = BasicLogger.getLogger();
	
	/**
	 * 
	 * 
	 * @param mailId
	 * @param strMailName
	 * @throws IOException
	 */
	public String createJobListId(String strScriptId, String strScriptName, String strScriptPath, String strEvidencePath, String strEndMail, String strErrorMail, String strDate) throws IOException {
		
		XMLUtils util = new XMLUtils();
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		strFilePath = jobDir.concat(JOB_LIST_XML);
		
		//JOB id
		ComDao comDao = new ComDao();
		String strJobId = "JOB".concat("_").concat(comDao.getJobSeq());
		
		FileInputStream fisList = null;
		
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			
			if (!file.exists()) {
				util.createBlankXml(strFilePath, "jobs");
			}
			fisList = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fisList);
			util.preCheckAddDoc(document, "", "//jobs");
			Element rootScriptList = (Element)document.selectSingleNode("jobs");
			Element rootScript = rootScriptList.addElement("job");
			rootScript.addElement("id").setText(strJobId);
			rootScript.addElement("script_id").setText(strScriptId);
			rootScript.addElement("script_name").setText(strScriptName);
			rootScript.addElement("script_path").setText(strScriptPath);
			rootScript.addElement("script_evidence").setText(strEvidencePath);
			rootScript.addElement("script_end_mail").setText(strEndMail);
			rootScript.addElement("script_error_mail").setText(strErrorMail);
			rootScript.addElement("script_date").setText(strDate);
			rootScript.addElement("status").setText("0");
			FileOutputStream out = new FileOutputStream(strFilePath);
			writer.setOutputStream(out);
			writer.write(document);
			out.close();
		} catch(IOException e) {
			logger.exception(e);
			e.printStackTrace();
			throw e;
		} catch (DocumentException e) {
			logger.exception(e);
			e.printStackTrace();
		} finally {
			if (fisList != null) {
				try {
					fisList.close();
				} catch (IOException e) {
					logger.exception(e);
					e.printStackTrace();
					throw e;
				}
			}
			
		}
		
		return strJobId;
	}
	
	/**
	 * update mail name
	 * 
	 * @param mailId
	 * @param strMailName
	 * @return
	 * @throws IOException
	 */
	public void updateStatus(String strJobId, String strStatus) throws IOException {
		
		XMLUtils util = new XMLUtils();
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		strFilePath = jobDir.concat(JOB_LIST_XML);
		
		FileInputStream fisList = null;
		
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			
			if (!file.exists()) {
				util.createBlankXml(strFilePath, "jobs");
			}
			fisList = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fisList);
			Element element = (Element)document.selectSingleNode("//jobs/job[id='" + strJobId  + "']");
			element.element("status").setText(strStatus);
			FileOutputStream out = new FileOutputStream(strFilePath);
			writer.setOutputStream(out);
			writer.write(document);
			out.close();
		} catch(IOException e) {
			logger.exception(e);
			e.printStackTrace();
			throw e;
		} catch (DocumentException e) {
			logger.exception(e);
			e.printStackTrace();
		} finally {
			if (fisList != null) {
				try {
					fisList.close();
				} catch (IOException e) {
					logger.exception(e);
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
	/**
	 *
	 */
	public List<Map<String, String>> searchList(String strSearchScriptName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = jobDir.concat(JOB_LIST_XML);
		String strXPATH = "";
		if (strSearchScriptName == null || strSearchScriptName.equals("")) {
			strXPATH = "//jobs/job";
		} else {
			strXPATH = "//jobs/job[contains(script_name, \"" + strSearchScriptName + "\")]";
		}
		
		@SuppressWarnings("rawtypes")
		List customerList = utils.searchNode(strFilePath, strXPATH);
		
		if (customerList != null) {
			for (int i = 0; i < customerList.size(); i++) {
				Node node = (Node)customerList.get(i);
				String strId = node.selectSingleNode("id").getText();
				String strScriptId = node.selectSingleNode("script_id").getText();
				String strScriptName = node.selectSingleNode("script_name").getText();
				String strScriptPath = node.selectSingleNode("script_path").getText();
				String strEvidencePath = node.selectSingleNode("script_evidence").getText();
				String strEndMail = node.selectSingleNode("script_end_mail").getText();
				String strErrorMail = node.selectSingleNode("script_error_mail").getText();
				String strDate = node.selectSingleNode("script_date").getText();
				String strStatus = node.selectSingleNode("status").getText();
				
				
				String strButton1 = "Cancel";
				String strButton2 = "Del";
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("ID", strId);
				map.put("SCRIPT_ID", strScriptId);
				map.put("SCRIPT_NAME", strScriptName);
				map.put("SCRIPT_PATH", strScriptPath);
				map.put("SCRIPT_EVIDENCE", strEvidencePath);
				map.put("SCRIPT_END_MAIL", strEndMail);
				map.put("SCRIPT_ERROR_MAIL", strErrorMail);
				map.put("SCRIPT_DATE", strDate);
				map.put("STATUS", strStatus);
				
				if (strStatus.equals("0")) {
					map.put("STATUS_TEXT", "For deal");
				} else if (strStatus.equals("1")) {
					map.put("STATUS_TEXT", "In process");
				} else if (strStatus.equals("2")) {
					map.put("STATUS_TEXT", "Cancel");
				} else if (strStatus.equals("3")) {
					map.put("STATUS_TEXT", "Overtime");
				} else if (strStatus.equals("4")) {
					map.put("STATUS_TEXT", "Error");
				} else if (strStatus.equals("Z")) {
					map.put("STATUS_TEXT", "Done");
				}
				map.put("DEAL_1", strButton1);
				map.put("DEAL_2", strButton2);
				
				result.add(map);
			}
		}
		
		result.sort((h1, h2) -> h2.get("SCRIPT_DATE").compareTo(h1.get("SCRIPT_DATE")));
		
		return result;
	}
	
	/**
	 */
	public void deleteByList(String strJobId) {
		
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		strFilePath = jobDir.concat(JOB_LIST_XML);
		
		//
		FileInputStream fis = null;
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			if (!file.exists()) {
				return;
			}
			fis = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fis);
			String strXPATH = "//jobs/job[id='".concat(strJobId).concat("']");
			Node node = document.selectSingleNode(strXPATH);
			Element root = node.getParent();
			root.remove(node);
			FileOutputStream out = new FileOutputStream(strFilePath);
			writer.setOutputStream(out);
			writer.write(document);
			out.close();
		} catch (Exception e) {
			logger.exception(e);
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.exception(e);
					e.printStackTrace();
				}
			}
		}
	}
}
