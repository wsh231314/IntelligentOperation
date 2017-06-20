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
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.app.co.jp.com.BaseDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Utils;
import org.app.co.jp.util.XMLUtils;

public class OperationListDao extends BaseDao{

	//
	public static final String OPERATION_LIST_XML = "OPERATION_LIST.xml";
	
	BasicLogger logger = BasicLogger.getLogger();
	
	/**
	 *
	 */
	public List<Map<String, String>> searchList(String strOperationName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = operationDir.concat(OPERATION_LIST_XML);
		String strXPATH = "";
		if (Utils.isEmpty(strOperationName)) {
			strXPATH = "//operations/operation";
		} else {
			strXPATH = "//operations/operation[contains(name, \"" + strOperationName + "\")]";
		}
		
		@SuppressWarnings("unchecked")
		List<Node> customerList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (customerList != null) {
			for (int i = 0; i < customerList.size(); i++) {
				Node node = (Node)customerList.get(i);
				String strId = node.selectSingleNode("id").getText();
				String strName = node.selectSingleNode("name").getText();
				String strButton1 = "Detail";
				String strButton2 = "Copy";
				String strButton3 = "Del";
				Map<String, String> map = new HashMap<String, String>();
				// 
				map.put("OPERATION_ID", strId);
				map.put("OPERATION_NAME", strName);
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
	public void deleteByList(String strOperationId) {
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		String xmlPath = "";
		strFilePath = operationDir.concat(OPERATION_LIST_XML);
		xmlPath = operationDir.concat(strOperationId);

		FileInputStream fis = null;
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			if (!file.exists()) {
				return;
			}
			fis = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fis);
			String strXPATH = "//operations/operation[id='".concat(strOperationId).concat("']");
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
	 * @return 
	 */
	public String getOperationNameById(String strOperationId) {
		String strFilePath = "";
		String strName = "";
		strFilePath = operationDir.concat(OPERATION_LIST_XML);

		FileInputStream fis = null;
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			if (!file.exists()) {
				return strName;
			}
			fis = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fis);
			String strXPATH = "//operations/operation[id='".concat(strOperationId).concat("']/name");
			Node node = document.selectSingleNode(strXPATH);
			strName = node.getText();
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
		return strName;
	}
}
