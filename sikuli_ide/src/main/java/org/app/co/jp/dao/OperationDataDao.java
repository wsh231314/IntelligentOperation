package org.app.co.jp.dao;

import org.app.co.jp.com.BaseDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Check;
import org.app.co.jp.util.Utils;
import org.app.co.jp.util.XMLUtils;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.*;

public class OperationDataDao extends BaseDao{

	//
	BasicLogger logger = BasicLogger.getLogger();
	
	public static final String SCENARIO_XML = "SCENARIO.xml";
	
	/**
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> search(String strOperationId, String strDataId) {
		Map<String, Map<String, String>> resultMap = new LinkedHashMap<String, Map<String, String>>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(strDataId).concat(".xml");
		
		ScenarioDao scenarioDao = new ScenarioDao();
		String strXPATH = "//SCENARIO/FIELD";
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		
		if (commonList != null) {
			for (int i = 0; i < commonList.size(); i++) {
				Element node = (Element)commonList.get(i);
				String strFieldId = node.attributeValue("ID");
				
				String [] strKeyInfo = strFieldId.split("-");
				
				String strOperation = "";
				String strCommand = "";
				try {
					strOperation = scenarioDao.getScenarioOperationType(strKeyInfo[0], strKeyInfo[1]);
					strCommand = scenarioDao.getScenarioOperationCommand(strKeyInfo[0], strKeyInfo[1]);
				} catch (Exception e) {
					logger.exception(e);
					e.printStackTrace();
				}
				
				if (Utils.isTextOperation(strOperation, strCommand)) {
					String strFirm = node.selectSingleNode("FIELD_FIRM").getText();
					String strPrefix = node.selectSingleNode("FIELD_PREFIX").getText();
					String strRadom = node.selectSingleNode("FIELD_RADOM").getText();
					String strLength = node.selectSingleNode("FIELD_LENGTH").getText();
					Map<String, String> map = new HashMap<String, String>();
					map.put("FIELD_FIRM", strFirm);
					map.put("FIELD_PREFIX", strPrefix);
					map.put("FIELD_RADOM", strRadom);
					map.put("FIELD_LENGTH", strLength);
					resultMap.put(strFieldId, map);
				}
			}
		}
		return resultMap;
	}
	
	/**
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> searchAllStep(String strOperationId, String strDataId) {
		Map<String, Map<String, String>> resultMap = new LinkedHashMap<String, Map<String, String>>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(strDataId).concat(".xml");
		
		String strXPATH = "//SCENARIO/FIELD";
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		
		if (commonList != null) {
			for (int i = 0; i < commonList.size(); i++) {
				Element node = (Element)commonList.get(i);
				String strFieldId = node.attributeValue("ID");
				
				String strFirm = node.selectSingleNode("FIELD_FIRM").getText();
				String strPrefix = node.selectSingleNode("FIELD_PREFIX").getText();
				String strRadom = node.selectSingleNode("FIELD_RADOM").getText();
				String strLength = node.selectSingleNode("FIELD_LENGTH").getText();
				Map<String, String> map = new HashMap<String, String>();
				map.put("FIELD_FIRM", strFirm);
				map.put("FIELD_PREFIX", strPrefix);
				map.put("FIELD_RADOM", strRadom);
				map.put("FIELD_LENGTH", strLength);
				resultMap.put(strFieldId, map);
			}
		}
		return resultMap;
	}
	
	/**
	 * 
	 * 
	 * @param strScenarioId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getColumns(String strScenarioId) throws Exception {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		XMLUtils utils = new XMLUtils();
		String strFilePath = "";
		strFilePath = scenarioDir.concat(strScenarioId).concat("\\\\").concat(SCENARIO_XML);
		String strXPATH = "//steps/step";
		
		PageDao pageDao = new PageDao();
		PageListDao pageListDao = new PageListDao();
		ScenarioDao scenarioDao = new ScenarioDao();
		
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (commonList != null) {
			for (int i = 0; i < commonList.size(); i++) {
				Element node = (Element)commonList.get(i);

				String strStepId = node.selectSingleNode("id").getText();
				String strPageId = node.selectSingleNode("page_id").getText();
				String strFieldId = node.selectSingleNode("field_id").getText();

				String strPageName = pageListDao.getPageName(strPageId);
				String[] strPageInfo = pageDao.getFiledNameAndType(strPageId, strFieldId);
				String strOperation = scenarioDao.getScenarioOperationType(strScenarioId, strStepId);
				String strCommand = scenarioDao.getScenarioOperationCommand(strScenarioId, strStepId);

				if (Utils.isTextOperation(strOperation, strCommand)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("STEP_ID", strStepId);
					map.put("PAGE_ID", strPageId);
					map.put("PAGE_NAME", strPageName);
					map.put("FIELD_ID", strFieldId);
					map.put("FIELD_NAME", strPageInfo[0]);
					map.put("FIELD_TYPE", strPageInfo[1]);

					resultList.add(map);
				}
			}
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasScenarioId(String strScenarioId) throws Exception {
		XMLUtils utils = new XMLUtils();
		String strFilePath = "";
		strFilePath = scenarioDir.concat(strScenarioId).concat("\\\\").concat(SCENARIO_XML);
		String strXPATH = "//steps/step";
		
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		if (commonList != null) {
			if (commonList.size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * data number
	 * 
	 * @param strOperationId
	 * @param strDataId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getDataNumber(String strOperationId, String strDataId) {
		int iNum = 1;
		if (Check.isNull(strOperationId)) {
			return iNum;
		}
		XMLUtils utils = new XMLUtils();
		String strFilePath = operationDir.concat(strOperationId).concat("\\\\").concat(strDataId).concat(".xml");
		
		String strXPATH = "//SCENARIO/NUMBER";
		List<Node> commonList = (List<Node>)utils.searchNode(strFilePath, strXPATH);
		
		if (commonList != null && !commonList.isEmpty()) {
			Node node = commonList.get(0);
			String strNum = node.getText();
			try {
				iNum = Integer.parseInt(strNum);
			} catch(Exception e) {
				iNum = 1;
			}
		}

		return iNum;
	}
}
