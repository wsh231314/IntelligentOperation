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
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Utils;
import org.app.co.jp.util.XMLUtils;

public class MailListDao extends BaseDao{

	//
	public static final String MAIL_LIST_XML = "MAIL_LIST.xml";
	
	BasicLogger logger = BasicLogger.getLogger();
	
	/**
	 * 
	 * 
	 * @param mailId
	 * @param strMailName
	 * @throws IOException
	 */
	public void createMailListId(String mailId, String strMailName) throws IOException {
		
		XMLUtils util = new XMLUtils();
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		strFilePath = mailDir.concat(MAIL_LIST_XML);
		
		// script folder
		String folder  = mailDir.concat(mailId);
		
		// if not exists, create folder 
		File folderFile = new File(folder); 
		if (folderFile.exists()) {
			folderFile.mkdir();
		}
		
		FileInputStream fisList = null;
		
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			
			if (!file.exists()) {
				util.createBlankXml(strFilePath, "mails");
			}
			fisList = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fisList);
			util.preCheckAddDoc(document, "", "//mails");
			Element rootScriptList = (Element)document.selectSingleNode("mails");
			Element rootScript = rootScriptList.addElement("mail");
			rootScript.addElement("id").setText(mailId);
			rootScript.addElement("name").setText(strMailName);
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
	 * update mail name
	 * 
	 * @param mailId
	 * @param strMailName
	 * @return
	 * @throws IOException
	 */
	public void updateMailName(String mailId, String strMailName) throws IOException {
		
		XMLUtils util = new XMLUtils();
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		strFilePath = mailDir.concat(MAIL_LIST_XML);
		
		FileInputStream fisList = null;
		
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			
			if (!file.exists()) {
				util.createBlankXml(strFilePath, "mails");
			}
			fisList = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fisList);
			Element element = (Element)document.selectSingleNode("//mails/mail[id='" + mailId  + "']");
			element.element("name").setText(strMailName);
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
	public List<Map<String, String>> searchList(String strMailName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = mailDir.concat(MAIL_LIST_XML);
		String strXPATH = "";
		if (strMailName == null || strMailName.equals("")) {
			strXPATH = "//mails/mail";
		} else {
			strXPATH = "//mails/mail[contains(name, \"" + strMailName + "\")]";
		}
		
		@SuppressWarnings("rawtypes")
		List customerList = utils.searchNode(strFilePath, strXPATH);
		
		if (customerList != null) {
			for (int i = 0; i < customerList.size(); i++) {
				Node node = (Node)customerList.get(i);
				String strId = node.selectSingleNode("id").getText();
				String strName = node.selectSingleNode("name").getText();
				String strButton1 = "Detail";
				String strButton2 = "Copy";
				String strButton3 = "Del";
				HashMap<String, String> map = new HashMap<String, String>();
				// 
				map.put("MAIL_ID", strId);
				map.put("MAIL_NAME", strName);
				map.put("MAIL_SELECT", "false");
				map.put("DEAL_1", strButton1);
				map.put("DEAL_2", strButton2);
				map.put("DEAL_3", strButton3);
				result.add(map);
			}
		}
		return result;
	}
	
	/**
	 */
	public void deleteByList(String strMailId) {
		
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		String xmlPath = "";
		strFilePath = mailDir.concat(MAIL_LIST_XML);
		xmlPath = mailDir.concat(strMailId);
		
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
			String strXPATH = "//mails/mail[id='".concat(strMailId).concat("']");
			Node node = document.selectSingleNode(strXPATH);
			Element root = node.getParent();
			root.remove(node);
			Utils.deleteDirectory(xmlPath);
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
	
	/**
	 * 
	 * 
	 * @param strMailId
	 * @return
	 * @throws Exception 
	 */
	public String getMailName(String strMailId) throws Exception {
		String strResult = "";
		
		String strMailFilePath = mailDir.concat(MAIL_LIST_XML);
		//
		FileInputStream fisList = null;
		try {
			SAXReader reader = new SAXReader();

			fisList = new FileInputStream(new File(strMailFilePath));
			Document document = reader.read(fisList);
			Element idElement = (Element)document.selectSingleNode("//mails/mail[id='".concat(strMailId).concat("']/name"));
			
			if (idElement != null) {
				strResult = idElement.getText();
			}
		} catch(Exception e) {
			logger.exception(e);
			e.printStackTrace();
			throw e;
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
		
		return strResult;
	}
}
