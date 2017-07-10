package org.app.co.jp.ap;

import org.app.co.jp.dao.PageDao;
import org.app.co.jp.dao.PageListDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.GridUtils;
import org.app.co.jp.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.List;

public class OperationInfoListDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel excelSheet = null;
	private JButton jButton = null;
	
	private File initExcelFile;
	private JButton preButton = null;
	private JButton afterButton = null;
	private JLabel detailNo = null;
	private JLabel detailPageName;
	GridUtils grid = null;
	private JLabel titleNo;
	private JLabel titlePageName;
	private JLabel titleFixValue;
	private JLabel pageInfoLbl = null;
	
	BasicLogger logger = BasicLogger.getLogger();
	private JLabel titlePattern;
	private JLabel titleFieldName;
	private JLabel detailFieldName;
	private JLabel dataId;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel scenarioName;
	private JLabel dataComment;
	private JLabel detailFixValue;
	private JLabel detailPattern = null;
	private JLabel detailLength = null;
	private JLabel detailRadom = null;
	private JLabel titleRadom;
	private JLabel titleLength;
	
	private String strDataId = "";
	private String strScenarioName = "";
	private String strDataComment = "";
	private List<Map<String, String>> propertyList = new ArrayList<Map<String, String>>();
	
	private OperationScenarioDataDialog _parent;
	/**
	 * This method initializes 
	 * 
	 */
	public OperationInfoListDialog(OperationScenarioDataDialog parent, String strDataId, String strScenarioName, String strDataComment, Map<String, Map<String, String>> propertyMap) {
		super();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				Utils.removeWindow(OperationInfoListDialog.this);
				_parent.setVisible(true);
			}
		});
		
		try {
			this.strDataId = strDataId;
			this.strScenarioName = strScenarioName;
			this.strDataComment = strDataComment;
			this._parent = parent;
			//
			if (propertyMap != null && !propertyMap.isEmpty()) {
				//
				int i = 1;
				Iterator<String> iterator = propertyMap.keySet().iterator();
				while (iterator.hasNext()) {
					Map<String, String> propertyColumnMap = new HashMap<String, String>();
					String key = iterator.next().toString();
					//
					Map<String, String> keyMap = propertyMap.get(key);
					// No
					propertyColumnMap.put("NO", String.valueOf(i));
					//
					String [] strKeyInfo = key.split("-");
					
					PageListDao pageListDao = new PageListDao();
					String pageName = pageListDao.getPageName(strKeyInfo[1]);
					
					PageDao pageDao = new PageDao();
					
					String []strFieldName = pageDao.getFiledNameAndType(strKeyInfo[2], strKeyInfo[3]);
					
					propertyColumnMap.put("PAGE_NAME", pageName);
					propertyColumnMap.put("FIELD_NAME", strFieldName[0]);
					
					propertyColumnMap.putAll(keyMap);
					
					//
					propertyList.add(propertyColumnMap);
					//
					i++;
				}
			}
			initialize();
		} catch(Exception e) {
			logger.exception(e);
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(800,600));
        this.setContentPane(this.getJPanel());
        ArrayList<String> title = new ArrayList<String>();
        title.add("No");
        title.add("Page Name");
        title.add("Field Name");
        title.add("Fixed Value");
        title.add("Pattern");
        title.add("Radom");
        title.add("Length");

        ArrayList<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(detailNo);
        componentList.add(detailPageName);
        componentList.add(detailFieldName);
        componentList.add(detailFixValue);
        componentList.add(detailPattern);
        componentList.add(detailRadom);
        componentList.add(detailLength);
        
        String []arrColumn = {"NO", "PAGE_NAME", "FIELD_NAME", "FIELD_FIRM", "FIELD_PREFIX", "FIELD_RADOM", "FIELD_LENGTH"};
        String []arrTitle = {"NO", "PAGE_NAME", "FIELD_NAME", "FIELD_FIRM", "FIELD_PREFIX", "FIELD_RADOM", "FIELD_LENGTH"};
        grid = new GridUtils(excelSheet, title, componentList, arrColumn, preButton, afterButton, 15, arrTitle);
        grid.setPageInfo(pageInfoLbl);
        
        grid.setData(propertyList);
        
        dataId.setText(strDataId);
        scenarioName.setText(strScenarioName);
        dataComment.setText(strDataComment);
        
        setTitle("Table Definition Information");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			titleLength = new JLabel();
			titleLength.setBounds(new java.awt.Rectangle(728,65,45,20));
			titleLength.setHorizontalAlignment(SwingConstants.CENTER);
			titleLength.setText("Length");
			titleLength.setBackground(new Color(255, 204, 204));
			titleRadom = new JLabel();
			titleRadom.setBounds(new java.awt.Rectangle(667,65,60,20));
			titleRadom.setHorizontalAlignment(SwingConstants.CENTER);
			titleRadom.setText("Radom");
			titleRadom.setBackground(new Color(255, 204, 204));
			dataComment = new JLabel();
			dataComment.setBounds(new java.awt.Rectangle(298,29,300,20));
			dataComment.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			scenarioName = new JLabel();
			scenarioName.setBounds(new java.awt.Rectangle(99,29,200,20));
			scenarioName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(298,10,300,20));
			jLabel4.setText("Comment");
			jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(99,10,200,20));
			jLabel3.setText("Scenario Name");
			jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dataId = new JLabel();
			dataId.setBounds(new java.awt.Rectangle(10,29,90,20));
			dataId.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			titleFieldName = new JLabel();
			titleFieldName.setBounds(new java.awt.Rectangle(214,65,130,20));
			titleFieldName.setHorizontalAlignment(SwingConstants.CENTER);
			titleFieldName.setText("Field Name");
			titlePattern = new JLabel();
			titlePattern.setBounds(new java.awt.Rectangle(576,65,90,20));
			titlePattern.setHorizontalAlignment(SwingConstants.CENTER);
			titlePattern.setText("Pattern");
			titlePattern.setBackground(new Color(255, 204, 204));
			pageInfoLbl = new JLabel();
			pageInfoLbl.setBounds(new java.awt.Rectangle(224,491,315,30));
			pageInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
			pageInfoLbl.setText("JLabel");
			titleFixValue = new JLabel();
			titleFixValue.setBounds(new java.awt.Rectangle(345,65,230,20));
			titleFixValue.setHorizontalAlignment(SwingConstants.CENTER);
			titleFixValue.setBackground(new Color(255,204,204));
			titleFixValue.setText("Fixed Value");
			titlePageName = new JLabel();
			titlePageName.setBounds(new java.awt.Rectangle(63,65,150,20));
			titlePageName.setHorizontalAlignment(SwingConstants.CENTER);
			titlePageName.setText("Column");
			titleNo = new JLabel();
			titleNo.setBounds(new java.awt.Rectangle(10,65,50,20));
			titleNo.setHorizontalAlignment(SwingConstants.CENTER);
			titleNo.setText("No");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(10,10,90,20));
			jLabel.setText("ID");
			jLabel.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(this.getJButton(), null);
			jPanel.add(getExcelSheet(), null);
			jPanel.add(getPreButton(), null);
			jPanel.add(getAfterButton(), null);
			jPanel.add(titleNo, null);
			jPanel.add(titlePageName, null);
			jPanel.add(titleFixValue, null);
			jPanel.add(pageInfoLbl, null);
			jPanel.add(titlePattern, null);
			jPanel.add(titleFieldName, null);
			jPanel.add(dataId, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(scenarioName, null);
			jPanel.add(dataComment, null);
			jPanel.add(titleRadom, null);
			jPanel.add(titleLength, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes excelSheet	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getExcelSheet() {
		if (excelSheet == null) {
			detailRadom = new JLabel();
			detailRadom.setBounds(new java.awt.Rectangle(658,0,60,20));
			detailRadom.setText("");
			detailLength = new JLabel();
			detailLength.setBounds(new java.awt.Rectangle(719,0,45,20));
			detailLength.setText("");
			detailPattern = new JLabel();
			detailPattern.setBounds(new java.awt.Rectangle(567,0,90,20));
			detailPattern.setText("");
			detailFixValue = new JLabel();
			detailFixValue.setBounds(new java.awt.Rectangle(336,0,230,20));
			detailFixValue.setText("");
			detailFieldName = new JLabel();
			detailFieldName.setBounds(new java.awt.Rectangle(205,0,130,20));
			detailFieldName.setText("");
			detailPageName = new JLabel();
			detailPageName.setBounds(new java.awt.Rectangle(54,0,150,20));
			detailPageName.setText("JLabel");
			detailNo = new JLabel();
			detailNo.setBounds(new java.awt.Rectangle(3,0,50,20));
			detailNo.setText("JLabel");
			excelSheet = new JPanel();
			excelSheet.setBounds(new java.awt.Rectangle(10,85,770,399));
			excelSheet.setLayout(null);
			excelSheet.add(detailNo, null);
			excelSheet.add(detailPageName, null);
			excelSheet.add(detailFieldName, null);
			excelSheet.add(detailFixValue, null);
			excelSheet.add(detailPattern, null);
			excelSheet.add(detailLength, null);
			excelSheet.add(detailRadom, null);
		}
		return excelSheet;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Close");
			jButton.setSize(new java.awt.Dimension(90,30));
			jButton.setLocation(new java.awt.Point(690,526));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					Utils.removeWindow(OperationInfoListDialog.this);
					_parent.setVisible(true);
				}
			});
		}
		return jButton;
	}

	/**
	 */
	public File getExcelFile() {
		return initExcelFile;
	}

	/**
	 */
	public void setExcelFile(File excelFile) {
		this.initExcelFile = excelFile;
	}

	/**
	 * This method initializes preButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPreButton() {
		if (preButton == null) {
			preButton = new JButton();
			preButton.setText("Prev Page");
			preButton.setSize(new java.awt.Dimension(90,30));
			preButton.setLocation(new java.awt.Point(11,491));
		}
		return preButton;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAfterButton() {
		if (afterButton == null) {
			afterButton = new JButton();
			afterButton.setLocation(new java.awt.Point(690,491));
			afterButton.setText("Next Page");
			afterButton.setSize(new java.awt.Dimension(90,30));
		}
		return afterButton;
	}
}  //  @jve:decl-index=0:visual-constraint="6,6"
