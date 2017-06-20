package org.app.co.jp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import org.app.co.jp.com.BaseDao;
import org.app.co.jp.com.ComDao;
import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Check;
import org.app.co.jp.util.Utils;
import org.app.co.jp.util.XMLUtils;

public class OperationDao extends BaseDao{

	//
	public static final String DATA_LIST_XML = "DATA_LIST.xml";
	
	public static final String OPERATION_LIST_XML = "OPERATION_LIST.xml";
	
	BasicLogger logger = BasicLogger.getLogger();
	
	/**
	 * 
	 * 
	 * @param strPattern
	 * @param strType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchList(String strOperationId) {
		if (Utils.isEmpty(strOperationId)) {
			return new ArrayList<Map<String, Object>>();
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(DATA_LIST_XML);
		String strXPATH = "//datas/data";
		
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (commonList != null) {
			for (int i = 0; i < commonList.size(); i++) {
				Node node = (Node)commonList.get(i);
				// id
				String strId = node.selectSingleNode("id").getText();
				String strComment = node.selectSingleNode("comment").getText();
				String strNum = node.selectSingleNode("number").getText();
				String strScenarioId = node.selectSingleNode("scenario_id").getText();
				String strButton1 = "Detail";
				String strButton2 = "Copy";
				String strButton3 = "Del";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("DATA_ID", strId);
				map.put("DATA_COMMENT", strComment);
				map.put("DATA_NUMBER", strNum);
				map.put("SCENARIO_ID", strScenarioId);
				map.put("DEAL_1", strButton1);
				map.put("DEAL_2", strButton2);
				map.put("DEAL_3", strButton3);
				map.put("DEAL_FLG", CommonConstant.DEAL_DEFAULT);
				result.add(map);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param strPattern
	 * @param strType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchCopyList(String strOperationId) {
		if (Utils.isEmpty(strOperationId)) {
			return new ArrayList<Map<String, Object>>();
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		XMLUtils utils = new XMLUtils();
		OperationDataDao dao = new OperationDataDao();
		
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(DATA_LIST_XML);
		String strXPATH = "//datas/data";
		
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (commonList != null) {
			for (int i = 0; i < commonList.size(); i++) {
				Node node = (Node)commonList.get(i);
				// id
				String strId = node.selectSingleNode("id").getText();
				String strComment = node.selectSingleNode("comment").getText();
				String strNum = node.selectSingleNode("number").getText();
				String strScenarioId = node.selectSingleNode("scenario_id").getText();
				String strButton1 = "Detail";
				String strButton2 = "Copy";
				String strButton3 = "Del";
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Map<String, String>> ruleMap = dao.search(strOperationId, strId);
				map.put("DATA_ID", "");
				map.put("DATA_COMMENT", strComment);
				map.put("DATA_NUMBER", strNum);
				map.put("SCENARIO_ID", strScenarioId);
				map.put("DEAL_1", strButton1);
				map.put("DEAL_2", strButton2);
				map.put("DEAL_3", strButton3);
				map.put("DEAL_FLG", CommonConstant.DEAL_INSERT);
				map.put("DATA_RULES", ruleMap);
				result.add(map);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param strPattern
	 * @param strType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchAllInfoList(String strOperationId) {
		if (Utils.isEmpty(strOperationId)) {
			return new ArrayList<Map<String, Object>>();
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		XMLUtils utils = new XMLUtils();
		OperationDataDao dao = new OperationDataDao();
		
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(DATA_LIST_XML);
		String strXPATH = "//datas/data";
		
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (commonList != null) {
			for (int i = 0; i < commonList.size(); i++) {
				Node node = (Node)commonList.get(i);
				// id
				String strId = node.selectSingleNode("id").getText();
				String strComment = node.selectSingleNode("comment").getText();
				String strNum = node.selectSingleNode("number").getText();
				String strScenarioId = node.selectSingleNode("scenario_id").getText();
				String strButton1 = "Detail";
				String strButton2 = "Copy";
				String strButton3 = "Del";
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Map<String, String>> ruleMap = dao.search(strOperationId, strId);
				map.put("DATA_ID", strId);
				map.put("DATA_COMMENT", strComment);
				map.put("DATA_NUMBER", strNum);
				map.put("SCENARIO_ID", strScenarioId);
				map.put("DEAL_1", strButton1);
				map.put("DEAL_2", strButton2);
				map.put("DEAL_3", strButton3);
				map.put("DEAL_FLG", CommonConstant.DEAL_DEFAULT);
				map.put("DATA_RULES", ruleMap);
				result.add(map);
			}
		}
		return result;
	}
	
	/**
	 *
	 */
	public void deleteByList(List<Map<String, Object>> deleteList, String strOperationId) {
		if (deleteList == null || deleteList.size() == 0) {
			return;
		}
		
		XMLWriter writer = new XMLWriter();
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(DATA_LIST_XML);
		String xmlPath = operationDir.concat(strOperationId).concat("\\\\");

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
			
			for (int i = 0; i < deleteList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>)deleteList.get(i);
				
				// id
				String strId = (String)map.get("DATA_ID");
				String strXPATH = "//datas/data[id='".concat(strId).concat("']");
				Node node = document.selectSingleNode(strXPATH);
				Element root = node.getParent();
				root.remove(node);
				File xmlFile = new File(xmlPath.concat(strId).concat(".xml"));
				if (xmlFile.exists()) {
					xmlFile.delete();
				}
			}
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
	 * @param dealList
	 * @param strOperationId
	 * @param strOperationName
	 * @throws Exception
	 */
	public void createByList(List<Map<String, Object>> dealList, String strOperationId, String strOperationName) throws Exception{
		if (dealList == null || dealList.size() == 0) {
			return;
		}
		
		//
		ComDao comDao = new ComDao();
		XMLUtils util = new XMLUtils();
		XMLWriter writer = new XMLWriter();
		String strFilePath = "";
		String xmlPath = "";
		strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(DATA_LIST_XML);
		xmlPath = operationDir.concat(strOperationId).concat("\\\\");
			
		if (!(new File(xmlPath).exists())) {
			new File(xmlPath).mkdir();
			String strOperationFilePath = operationDir.concat(OPERATION_LIST_XML);
			
			//
			FileInputStream fisList = null;
			try {
				SAXReader reader = new SAXReader();
				File file = new File(strOperationFilePath);
				if (!file.exists()) {
					util.createBlankXml(strOperationFilePath, "operations");
				}
				fisList = new FileInputStream(new File(strOperationFilePath));
				Document document = reader.read(fisList);
				util.preCheckAddDoc(document, "", "//operations");
				Element rootPatternList = (Element)document.selectSingleNode("operations");
				Element rootPattern = rootPatternList.addElement("operation");
				
				rootPattern.addElement("id").setText(strOperationId);
				rootPattern.addElement("name").setText(strOperationName);
				FileOutputStream out = new FileOutputStream(strOperationFilePath);
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
			String strOperationFilePath = operationDir.concat(OPERATION_LIST_XML);
			
			//
			FileInputStream fisList = null;
			try {
				SAXReader reader = new SAXReader();

				fisList = new FileInputStream(new File(strOperationFilePath));
				Document document = reader.read(fisList);
				Element idElement = (Element)document.selectSingleNode("//operations/operation[id='".concat(strOperationId).concat("']/name"));
				idElement.setText(strOperationName);
				FileOutputStream out = new FileOutputStream(strOperationFilePath);
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
		
		DocumentFactory factory = DocumentFactory.getInstance();
		//
		FileInputStream fis = null;
		FileInputStream fisUpd = null;
		try {
			SAXReader reader = new SAXReader();
			File file = new File(strFilePath);
			if (!file.exists()) {
				util.createBlankXml(strFilePath, "datas");
			}
			fis = new FileInputStream(new File(strFilePath));
			Document document = reader.read(fis);
			
			for (int i = 0; i < dealList.size(); i++) {
				Map<String, Object> map = dealList.get(i);
				String dealFlg = (String)map.get("DEAL_FLG");
				if (CommonConstant.DEAL_DEFAULT.equals(dealFlg)) {
					continue;
				}
				// id
				String strDataId = (String)map.get("DATA_ID");
				String strDataComment = (String)map.get("DATA_COMMENT");
				if (Check.isNull(strDataId)) {
					Document newDoc = factory.createDocument();
					strDataId = "DATA".concat("_").concat(comDao.getOperationDataSeq(strOperationId, CommonConstant.PATTERN_CUSTOMER));
					@SuppressWarnings("unchecked")
					Map<String, Map<String, String>> ruleMap = (Map<String, Map<String, String>>)map.get("DATA_RULES");
					
					Element rootData = newDoc.addElement("SCENARIO").addAttribute("ID", strDataId);
					
					rootData.addElement("COMMENT").setText(strDataComment);
					rootData.addElement("NUMBER").setText((String)map.get("DATA_NUMBER"));
					
					Iterator<String> iterator = ruleMap.keySet().iterator();
					while (iterator.hasNext()) {
						String key = iterator.next();
						Element rootColumn = rootData.addElement("FIELD").addAttribute("ID", key);
						
						Map<String, String> mapProperty = ruleMap.get(key);
						Iterator<String> iteratorPro = mapProperty.keySet().iterator();
						
						while (iteratorPro.hasNext()) {
							String keyPro = (String)iteratorPro.next();
							rootColumn.addElement(keyPro).setText((String)mapProperty.get(keyPro));
						}
						
					}
					
					FileOutputStream out = new FileOutputStream(xmlPath.concat(strDataId).concat(".xml"));
					writer.setOutputStream(out);
					writer.write(newDoc);
					out.close();
					
					util.preCheckAddDoc(document, "", "//datas");
					Element rootNode = (Element)document.selectSingleNode("//datas");
					Element rootTableList = rootNode.addElement("data");
					rootTableList.addElement("id").setText(strDataId);
					rootTableList.addElement("comment").setText((String)map.get("DATA_COMMENT"));
					rootTableList.addElement("number").setText((String)map.get("DATA_NUMBER"));
					rootTableList.addElement("scenario_id").setText((String)map.get("SCENARIO_ID"));
				} else {
					fisUpd = new FileInputStream(new File(xmlPath.concat(strDataId).concat(".xml")));
					Document updDoc = reader.read(fisUpd);
					
					@SuppressWarnings("unchecked")
					Map<String, Map<String, String>> ruleMap = (Map<String, Map<String, String>>)map.get("DATA_RULES");
					Element rootTable = (Element)updDoc.selectSingleNode("SCENARIO");
					
					rootTable.selectSingleNode("COMMENT").setText(strDataComment);
					rootTable.selectSingleNode("NUMBER").setText((String)map.get("DATA_NUMBER"));
					
					Iterator<String> iterator = ruleMap.keySet().iterator();
					while (iterator.hasNext()) {
						String key = iterator.next();
						String columnXPATH = "//SCENARIO/FIELD[@ID='".concat(key).concat("']");
						
						@SuppressWarnings("unchecked")
						List<Node> rootColumnList = (List<Node>)updDoc.selectNodes(columnXPATH);
						//
						Element rootColumn = null;
						if (rootColumnList != null && !rootColumnList.isEmpty()) {
							rootColumn = (Element)rootColumnList.get(0);
						} else {
							rootColumn = rootTable.addElement("FIELD").addAttribute("ID", key);
						}
						
						Map<String, String> mapProperty = ruleMap.get(key);
						Iterator<String> iteratorPro = mapProperty.keySet().iterator();
						while (iteratorPro.hasNext()) {
							String keyPro = (String)iteratorPro.next();
							
							@SuppressWarnings("unchecked")
							List<Node> proList = (List<Node>)updDoc.selectNodes(columnXPATH.concat("/").concat(keyPro));
							if (proList != null && !proList.isEmpty()) {
								((Element)proList.get(0)).setText((String)mapProperty.get(keyPro));
							} else {
								rootColumn.addElement(keyPro).setText((String)mapProperty.get(keyPro));
							}
						}
						
					}
					
					FileOutputStream out = new FileOutputStream(xmlPath.concat(strDataId).concat(".xml"));
					writer.setOutputStream(out);
					writer.write(updDoc);
					out.close();
					Element idNode = (Element)document.selectSingleNode("//datas/data[id='".concat(strDataId).concat("']"));
					idNode.selectSingleNode("comment").setText((String)map.get("DATA_COMMENT"));
					idNode.selectSingleNode("number").setText((String)map.get("DATA_NUMBER"));
				}
			}
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
	
	/**
	 * 
	 * @param strPattern
	 * @param strType
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getDefinitionInfoByOperationId(String strOperationId) throws Exception {
		if (Utils.isEmpty(strOperationId)) {
			return new ArrayList<Map<String, String>>();
		}
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		XMLUtils utils = new XMLUtils();
		ScenarioDao scenarioDao = new ScenarioDao();
		
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(DATA_LIST_XML);
		String strXPATH = "//datas/data";
		
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (commonList != null && commonList.size() > 0) {
			
			Node node = (Node)commonList.get(0);
			
			String strScenarioId = node.selectSingleNode("scenario_id").getText();
			
			List<Map<String, Object>> list = scenarioDao.searchList(strScenarioId);
			
			for (Map<String, Object> map : list) {
				
				String strPageIdKey = (String)map.get("PAGE_ID");
				String strFieldIdKey = (String)map.get("FIELD_ID");
				
				Map<String, String> definitionMap = new HashMap<String, String>();
				
				String strOperation = (String)map.get("OPERATION");
				
				PageDao pageDao = new PageDao();
				Map<String, String> propetyMap = pageDao.getFiledPropeties(strPageIdKey, strFieldIdKey);
				
				definitionMap.put("OPERATION_TYPE", strOperation);
				definitionMap.put("OPERATION_PIC_PATH", propetyMap.get("path"));
				definitionMap.put("OPERATION_PIC_VALUE", propetyMap.get("value"));
				definitionMap.put("OPERATION_PIC", strPageIdKey.concat("_").concat(strFieldIdKey).concat(".png"));
				definitionMap.put("OPERATION_CAPTURE", (String)map.get("CAPTURE"));
				definitionMap.put("OPERATION_TEST_EXEC", (String)map.get("TEST_EXEC"));
				definitionMap.put("OPERATION_COMMAND", (String)map.get("COMMAND"));
				
				result.add(definitionMap);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 
	 * @param strPattern
	 * @param strType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String  getScenatioIdByOperationId(String strOperationId) {
		if (Utils.isEmpty(strOperationId)) {
			return "";
		}
		
		String result = "";
		XMLUtils utils = new XMLUtils();
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(DATA_LIST_XML);
		String strXPATH = "//datas/data";
		
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (commonList != null) {
			Node node = (Node)commonList.get(0);
			// scenario_id
			result = node.selectSingleNode("scenario_id").getText();
		}
		return result;
	}
}
