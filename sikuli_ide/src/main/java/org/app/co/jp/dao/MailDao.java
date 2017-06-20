package org.app.co.jp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.app.co.jp.com.BaseDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.XMLUtils;

public class MailDao extends BaseDao{

	//
	public static final String MAIL_XML = "MAIL.xml";
	
	public static final String MAIL_LIST_XML = "MAIL_LIST.xml";
	
	BasicLogger logger = BasicLogger.getLogger();
	
	
	/**
	 */
	public Map<String, String> searchById(String strMailId) {
		Map<String, String> result = new HashMap<String, String>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = "";
		strFilePath = mailDir.concat(strMailId).concat("\\\\").concat(MAIL_XML);
		
		String strXPATH = "//mails/mail";
		
		@SuppressWarnings("rawtypes")
		List commonList = utils.searchNode(strFilePath, strXPATH);
		if (commonList != null && !commonList.isEmpty()) {
			Node node = (Node)commonList.get(0);
			// id
			String strId = node.selectSingleNode("id").getText();
			String strSubject = node.selectSingleNode("subject").getText();
			String strTo = node.selectSingleNode("to").getText();
			String strCc = node.selectSingleNode("cc").getText();
			String strBcc = node.selectSingleNode("bcc").getText();
			String strAttachment = node.selectSingleNode("attachment").getText();
			String strContent = node.selectSingleNode("content").getText();
			result.put("ID", strId);
			result.put("SUBJECT", strSubject);
			result.put("TO", strTo);
			result.put("CC", strCc);
			result.put("BCC", strBcc);
			result.put("ATTACHMENT", strAttachment);
			result.put("CONTENT", strContent);
		}
		return result;
	}
	
	
	
	/**
	 */
	public void dealMail(Map<String, String> paramMap, String strMailId, String strMailName) throws Exception{
		if (paramMap == null) {
			return;
		}
		//
		XMLUtils util = new XMLUtils();
		XMLWriter writer = new XMLWriter();
		String	strFilePath = mailDir.concat(strMailId).concat("\\\\").concat(MAIL_XML);
		String	xmlMailPath = mailDir.concat(strMailId).concat("\\\\");
		
		if (!(new File(xmlMailPath).exists())) {
			new File(xmlMailPath).mkdir();
			String strMailFilePath = mailDir.concat(MAIL_LIST_XML);
			//
			FileInputStream fisList = null;
			try {
				SAXReader reader = new SAXReader();
				File file = new File(strMailFilePath);
				if (!file.exists()) {
					util.createBlankXml(strMailFilePath, "mails");
				}
				fisList = new FileInputStream(new File(strMailFilePath));
				Document document = reader.read(fisList);
				util.preCheckAddDoc(document, "", "//mails");
				Element rootPatternList = (Element)document.selectSingleNode("mails");
				Element rootPattern = rootPatternList.addElement("mail");
				rootPattern.addElement("id").setText(strMailId);
				rootPattern.addElement("name").setText(strMailName);
				FileOutputStream out = new FileOutputStream(strMailFilePath);
				writer.setOutputStream(out);
				writer.write(document);
				out.close();
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
		} else {
			String strPageFilePath = mailDir.concat(MAIL_LIST_XML);
			//
			FileInputStream fisList = null;
			try {
				SAXReader reader = new SAXReader();

				fisList = new FileInputStream(new File(strPageFilePath));
				Document document = reader.read(fisList);
				Element idElement = (Element)document.selectSingleNode("//mails/mail[id='".concat(strMailId).concat("']/name"));
				idElement.setText(strMailName);
				FileOutputStream out = new FileOutputStream(strPageFilePath);
				writer.setOutputStream(out);
				writer.write(document);
				out.close();
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
		}
		//
		FileInputStream fis = null;
		FileInputStream fisUpd = null;
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			if (!file.exists()) {
				util.createBlankXml(strFilePath, "mails");
			} else {
				file.delete();
				util.createBlankXml(strFilePath, "mails");
			}
			
			fis = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fis);
			
			// id
			String strId = paramMap.get("ID");
			String strSubject = paramMap.get("SUBJECT");
			String strTo = paramMap.get("TO");
			String strCc = paramMap.get("CC");
			String strBcc = paramMap.get("BCC");
			String strAttachment = paramMap.get("ATTACHMENT");
			String strContent = paramMap.get("CONTENT");
			
			util.preCheckAddDoc(document, "", "//mails");
			Element rootNode = (Element)document.selectSingleNode("//mails");
			Element rootTableList = rootNode.addElement("mail");
			rootTableList.addElement("id").setText(strId);
			rootTableList.addElement("subject").setText(strSubject);
			rootTableList.addElement("to").setText(strTo);
			rootTableList.addElement("cc").setText(strCc);
			rootTableList.addElement("bcc").setText(strBcc);
			rootTableList.addElement("attachment").setText(strAttachment);
			rootTableList.addElement("content").setText(strContent);
			
			FileOutputStream out = new FileOutputStream(strFilePath);
			writer.setOutputStream(out);
			writer.write(document);
			out.close();
		} catch (Exception e) {
			logger.exception(e);
			e.printStackTrace();
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.exception(e);
					e.printStackTrace();
					throw e;
				}
			}
			if (fisUpd != null) {
				try {
					fisUpd.close();
				} catch (IOException e) {
					logger.exception(e);
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
}
