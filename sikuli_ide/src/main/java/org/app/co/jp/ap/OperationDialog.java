package org.app.co.jp.ap;

import org.app.co.jp.com.ComDao;
import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.dao.OperationDao;
import org.app.co.jp.dao.OperationDataDao;
import org.app.co.jp.dao.ScenarioListDao;
import org.app.co.jp.util.AutoOperationDataFormatUtil;
import org.app.co.jp.util.AutoOperationDataUtil;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Check;
import org.app.co.jp.util.GridUtils;
import org.app.co.jp.util.Utils;
import org.app.co.jp.util.bean.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OperationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel detailSheet = null;
	private JButton btnConfirm = null;
	private JButton btnClose = null;
	
	private File initExcelFile;
	private JButton preButton = null;
	private JButton afterButton = null;
	private JLabel dataIdInit;
	private JLabel lblOperationName;
	private JLabel dataCommentInit;
	public GridUtils grid = null;
	private JLabel titleDataId;
	private JLabel titleDataComment;
	private JLabel titleRecordNum = null;
	private JLabel pageInfoLbl = null;
	
	BasicLogger logger = BasicLogger.getLogger();
	private JTextField txtOperationName = null;
	private JLabel titleDeal = null;
	private JButton btnDetailInit = null;
	private JButton btnCopyInit = null;
	private JLabel patternId = null;
	private JButton btnDelInit = null;
	private JLabel recordNumInit = null;
	private JButton addData = null;
	
	private String strOperationId = "";
	private String strOperationName = "";
	private String strMode = "";
	
	private List<Map<String, Object>> deleteList = new ArrayList<Map<String, Object>>();
	
	private OperationListDialog _parent;
	
	private JTextField txtScenarioId;
	private JTextField txtScenarioName;
	
	private JLabel lblScenarioId;
	
	private int iMemoRow = -1;
	
	/**
	 * This method initializes 
	 * 
	 */
	public OperationDialog(OperationListDialog parent, String strOperationId, String strOperationName, String strMode) {
		super();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				Utils.removeWindow(OperationDialog.this);
				_parent.setVisible(true);
			}
		});
		
		try {
			this._parent = parent;
			this.strOperationId = strOperationId;
			this.strOperationName = strOperationName;
			this.strMode = strMode;
			initialize();
		} catch(Exception e) {
			e.printStackTrace();
			logger.exception(e);
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(800,600));
        this.setContentPane(getJPanel());

        List<String> title = new ArrayList<String>();
        title.add("Table Id");
        title.add("Comment");
        title.add("Rec num");
        title.add("Detail");
        title.add("Copy");
        title.add("Del");

        List<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(dataIdInit);
        componentList.add(dataCommentInit);
        componentList.add(recordNumInit);
        componentList.add(btnDetailInit);
        componentList.add(btnCopyInit);
        componentList.add(btnDelInit);

        String []arrColumn = {"DATA_ID", "DATA_COMMENT", "DATA_NUMBER", "DEAL_1", "DEAL_2", "DEAL_3"};
        String []arrTitle = {"DATA_ID", "DATA_COMMENT", "DATA_NUMBER", "DEAL_1"};
        // init grid
        grid = new GridUtils(detailSheet, title, componentList, arrColumn, preButton, afterButton, 15, arrTitle);
        // set title
        grid.setPageInfo(pageInfoLbl);
        
        searchDetailList();
        
        txtOperationName.setText(strOperationName);
        
        setTitle("Operation create page");
        
		if (CommonConstant.MODE_NEW.equals(strMode)) {
			// confirm button
			btnConfirm.setText("Create");
		} else if (CommonConstant.MODE_COPY.equals(strMode)) {
			btnConfirm.setText("Copy");
		} else {
			btnConfirm.setText("Update");
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			titleDeal = new JLabel();
			titleDeal.setBounds(new Rectangle(580, 135, 200, 20));
			titleDeal.setHorizontalAlignment(SwingConstants.CENTER);
			titleDeal.setText("Operation");
			titleDeal.setBackground(new Color(255, 204, 204));
			pageInfoLbl = new JLabel();
			pageInfoLbl.setBounds(new java.awt.Rectangle(224,480,315,30));
			pageInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
			pageInfoLbl.setText("");
			titleRecordNum = new JLabel();
			titleRecordNum.setBounds(new Rectangle(510, 135, 70, 20));
			titleRecordNum.setHorizontalAlignment(SwingConstants.CENTER);
			titleRecordNum.setBackground(new Color(255,204,204));
			titleRecordNum.setText("Rec num");
			titleDataComment = new JLabel();
			titleDataComment.setBounds(new Rectangle(100, 135, 410, 20));
			titleDataComment.setHorizontalAlignment(SwingConstants.CENTER);
			titleDataComment.setBackground(new Color(255,204,204));
			titleDataComment.setText("Data Comment");
			titleDataId = new JLabel();
			titleDataId.setBounds(new Rectangle(10, 135, 90, 20));
			titleDataId.setHorizontalAlignment(SwingConstants.CENTER);
			titleDataId.setBackground(new Color(255,160,204));
			titleDataId.setText("Data ID");
			lblOperationName = new JLabel();
			lblOperationName.setBounds(new Rectangle(10, 40, 120, 20));
			lblOperationName.setText("Operation Name");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(10,10,500,24));
			jLabel.setText("Create the definitions which used to create data in this Operation");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getBtnConfirm(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(getDetailSheet(), null);
			jPanel.add(getPreButton(), null);
			jPanel.add(getAfterButton(), null);
			jPanel.add(lblOperationName, null);
			jPanel.add(titleDataId, null);
			jPanel.add(titleDataComment, null);
			jPanel.add(titleRecordNum, null);
			jPanel.add(pageInfoLbl, null);
			jPanel.add(getSearchPatternName(), null);
			jPanel.add(titleDeal, null);
			jPanel.add(getAddData(), null);
			
			lblScenarioId = new JLabel();
			lblScenarioId.setText("Scenario Id");
			lblScenarioId.setBounds(new Rectangle(10, 40, 120, 20));
			lblScenarioId.setBounds(10, 90, 120, 20);
			jPanel.add(lblScenarioId);
			
			txtScenarioId = new JTextField();
			txtScenarioId.setEditable(false);
			txtScenarioId.setBounds(new Rectangle(140, 40, 462, 20));
			txtScenarioId.setBounds(140, 90, 100, 20);
			jPanel.add(txtScenarioId);
			
			JLabel lblScenarioName = new JLabel();
			lblScenarioName.setText("Scenario Name");
			lblScenarioName.setBounds(new Rectangle(10, 40, 120, 20));
			lblScenarioName.setBounds(270, 90, 120, 20);
			jPanel.add(lblScenarioName);
			
			txtScenarioName = new JTextField();
			txtScenarioName.setEditable(false);
			txtScenarioName.setBounds(new Rectangle(140, 40, 462, 20));
			txtScenarioName.setBounds(400, 90, 200, 20);
			jPanel.add(txtScenarioName);
			
			JButton btnExcel = new JButton("Excel Format");
			btnExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String strScenarioId = txtScenarioId.getText();
					
					if (Utils.isEmpty(strScenarioId)) {
						JOptionPane.showMessageDialog(OperationDialog.this, "Please check the scenario Id.");
						return;
					}
					
					try {
						FileSelect jfc = new FileSelect();
						jfc.setVisible(true);
						int returnVal = jfc.showOpenDialog(OperationDialog.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							if (jfc.getSelectedFile().getName().indexOf(".xls") < 0) {
								JOptionPane.showMessageDialog(OperationDialog.this, "Please select EXCEL files!");
								return;
							}
							
							AutoOperationDataFormatUtil dataUtil = new AutoOperationDataFormatUtil();
							
							dataUtil.createOperationDataFormat(strScenarioId, txtOperationName.getText().trim(), jfc.getSelectedFile().getAbsolutePath());
						}
					} catch (Exception e1) {
						//
						logger.exception(e1);
					}
				}
			});
			btnExcel.setBounds(110, 520, 130, 30);
			jPanel.add(btnExcel);
		}
		return jPanel;
	}

	/**
	 * This method initializes excelSheet	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDetailSheet() {
		if (detailSheet == null) {
			recordNumInit = new JLabel();
			recordNumInit.setBounds(new java.awt.Rectangle(497,0,70,20));
			recordNumInit.setText("");
			recordNumInit.setBackground(new Color(255, 204, 204));
			dataCommentInit = new JLabel();
			dataCommentInit.setBounds(new Rectangle(90, 0, 407, 20));
			dataCommentInit.setText("JLabel");
			dataIdInit = new JLabel();
			dataIdInit.setHorizontalAlignment(SwingConstants.CENTER);
			dataIdInit.setBounds(new java.awt.Rectangle(3,0,87,20));
			dataIdInit.setText("JLabel");
			detailSheet = new JPanel();
			detailSheet.setBounds(new Rectangle(10, 155, 770, 300));
			detailSheet.setLayout(null);
			detailSheet.add(dataIdInit, null);
			detailSheet.add(dataCommentInit, null);
			detailSheet.add(getBtnDetailInit(), null);
			detailSheet.add(getBtnCopyInit(), null);
			detailSheet.add(getBtnDelInit(), null);
			detailSheet.add(recordNumInit, null);
		}
		return detailSheet;
	}

	/**
	 * This method initializes btnExcelCreate	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new JButton();
			btnConfirm.setText("Confirm");
			btnConfirm.setSize(new Dimension(90,30));
			btnConfirm.setLocation(new java.awt.Point(10,520));
			btnConfirm.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings({ "unchecked" })
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						OperationDao dao = new OperationDao();
						grid.freshData();
						List<Map<String, Object>> valueList = (List<Map<String, Object>>)grid.getValueList();
						if ((valueList == null || valueList.size() == 0) && deleteList.size() == 0) {
							JOptionPane.showMessageDialog(OperationDialog.this, "Please add data definition!");
							return;
						}
						
						if (deleteList.size() > 0){
							int iResult = JOptionPane.showConfirmDialog(OperationDialog.this, "We will delete some data table definition. Is it OK?");
							if (iResult != JOptionPane.YES_OPTION) {
								return;
							}
							dao.deleteByList(deleteList, strOperationId);
						}
						
						if (strMode.equals(CommonConstant.MODE_NEW) ||
								strMode.equals(CommonConstant.MODE_COPY)
								) {
							ComDao comDao = new ComDao();
							strOperationId = "OPERATION".concat("_").concat(comDao.getOperationSeq(CommonConstant.PATTERN_CUSTOMER));
						}
						
						if (valueList != null && valueList.size() > 0) {
							dao.createByList(valueList, strOperationId, txtOperationName.getText().trim());
						}
						
						JOptionPane.showMessageDialog(OperationDialog.this, "Completed!");
						
						setVisible(false);
						Utils.removeWindow(OperationDialog.this);
						_parent.setVisible(true);
						
						_parent.searchDetailList();
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(OperationDialog.this, "Failed!");
						logger.exception(e1);
					}
				}
			});
		}
		return btnConfirm;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("Close");
			btnClose.setSize(new Dimension(90,30));
			btnClose.setLocation(new java.awt.Point(690,520));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					Utils.removeWindow(OperationDialog.this);
					_parent.setVisible(true);
				}
			});
		}
		return btnClose;
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
			preButton.setSize(new Dimension(90,30));
			preButton.setLocation(new java.awt.Point(10,480));
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
			afterButton.setLocation(new java.awt.Point(690,480));
			afterButton.setText("Next Page");
			afterButton.setSize(new Dimension(90,30));
		}
		return afterButton;
	}

	/**
	 * This method initializes searchPatternName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchPatternName() {
		if (txtOperationName == null) {
			txtOperationName = new JTextField();
			txtOperationName.setBounds(new Rectangle(140, 40, 462, 20));
		}
		return txtOperationName;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDetailInit() {
		if (btnDetailInit == null) {
			btnDetailInit = new JButton();
			btnDetailInit.setBounds(new java.awt.Rectangle(567,0,70,20));
			btnDetailInit.setText("Detail");
			btnDetailInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnDetailInit.setPreferredSize(new Dimension(70, 30));
			btnDetailInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("unchecked")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iRow = 0;
					List<Object> compList = (List<Object>)grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List<Object>)compList.get(i)).get(3))) {
							iRow = i;
						}
					}
					//
					String strDataId = ((JLabel)((List<Object>)compList.get(iRow)).get(0)).getText();
					//
					String strDataComment = ((JLabel)((List<Object>)compList.get(iRow)).get(1)).getText();
					
					int dateRow = grid.getDataRow(iRow);
					Map<String, Object> valueMap = (Map<String, Object>)grid.getValueList().get(dateRow);
					// 
					OperationScenarioDataDialog dialog = new OperationScenarioDataDialog(OperationDialog.this, strDataId, strDataComment, CommonConstant.MODE_UPDATE, txtScenarioId.getText().trim(), txtScenarioName.getText().trim());
					String dealFlg = (String)valueMap.get("DEAL_FLG");
					if (CommonConstant.DEAL_DEFAULT.equals(dealFlg)) {
						OperationDataDao dao = new OperationDataDao();
						dialog.getPropertyMap().putAll(dao.search(strOperationId, strDataId));
					} else {
						dialog.getPropertyMap().putAll((Map<String, Map<String, String>>)valueMap.get("DATA_RULES"));
					}
					dialog.setCommentStr((String)valueMap.get("DATA_COMMENT"));
					dialog.setNumberStr((String)valueMap.get("DATA_NUMBER"));
					
					iMemoRow = dateRow;
					
					setVisible(false);
					Utils.addWindow(dialog);
					dialog.setVisible(true);;
				}
			});
		}
		return btnDetailInit;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCopyInit() {
		if (btnCopyInit == null) {
			btnCopyInit = new JButton();
			btnCopyInit.setBounds(new java.awt.Rectangle(637,0,70,20));
			btnCopyInit.setText("Copy");
			btnCopyInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnCopyInit.setPreferredSize(new Dimension(70, 30));
			btnCopyInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("unchecked")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int iRow = 0;
					List<Object> compList = (List<Object>)grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List<Object>)compList.get(i)).get(4))) {
							iRow = i;
						}
					}
					//
					String strDataId = ((JLabel)((List<Object>)compList.get(iRow)).get(0)).getText();
					//
					String strDataComment = ((JLabel)((List<Object>)compList.get(iRow)).get(1)).getText();
					
					int dateRow = grid.getDataRow(iRow);
					Map<String, Object> valueMap = (Map<String, Object>)grid.getValueList().get(dateRow);
					// 
					OperationScenarioDataDialog dialog = new OperationScenarioDataDialog(OperationDialog.this, strDataId, strDataComment, CommonConstant.MODE_COPY, txtScenarioId.getText().trim(), txtScenarioName.getText().trim());
					String dealFlg = (String)valueMap.get("DEAL_FLG");
					if (CommonConstant.DEAL_DEFAULT.equals(dealFlg)) {
						OperationDataDao dao = new OperationDataDao();
						dialog.getPropertyMap().putAll(dao.search(strOperationId, strDataId));
					} else {
						dialog.getPropertyMap().putAll((Map<String, Map<String, String>>)valueMap.get("DATA_RULES"));
					}
					dialog.setCommentStr((String)valueMap.get("DATA_COMMENT"));
					dialog.setNumberStr((String)valueMap.get("DATA_NUMBER"));
					
					iMemoRow = dateRow;
					
					setVisible(false);
					Utils.addWindow(dialog);
					dialog.setVisible(true);
					
				}
			});
		}
		return btnCopyInit;
	}
	
	/**
	 *
	 */
	private void searchDetailList() {
		if (strOperationId != null && !strOperationId.equals("")) {
			OperationDao dao = new OperationDao();
			if (CommonConstant.MODE_COPY.equals(strMode)) {
				List<Map<String, Object>> list = dao.searchCopyList(strOperationId);
				grid.setData(list);
			} else {
				List<Map<String, Object>> list = dao.searchList(strOperationId);
				grid.setData(list);
			}
			setScenarioByList();
		}
	}
	
	/**
	 *
	 */
	private void setDisplayControl() {
		
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelInit() {
		if (btnDelInit == null) {
			btnDelInit = new JButton();
			btnDelInit.setBounds(new java.awt.Rectangle(707,0,60,20));
			btnDelInit.setPreferredSize(new Dimension(70, 30));
			btnDelInit.setText("Del");
			btnDelInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnDelInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings({"unchecked" })
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iResult = JOptionPane.showConfirmDialog(OperationDialog.this, "Do you want to delete?");
					if (iResult != JOptionPane.YES_OPTION) {
						return;
					}
					int iRow = 0;
					List<Object> compList = grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List<Object>)compList.get(i)).get(5))) {
							iRow = i;
						}
					}
					// 
					int dateRow = grid.getDataRow(iRow);
					List<Map<String, Object>> dataList = grid.getValueList();
					Map<String, Object> deleteMap = dataList.get(dateRow);
					//
					String strId = (String)deleteMap.get("DATA_ID");
					if (!Check.isNull(strId)) {
						deleteList.add(deleteMap);
					}
					dataList.remove(dateRow);
					grid.setData(dataList);
					setDisplayControl();
				}
			});
		}
		return btnDelInit;
	}

	/**
	 * This method initializes addRecord	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddData() {
		if (addData == null) {
			addData = new JButton();
			addData.setBounds(new java.awt.Rectangle(670,40,110,20));
			addData.setText("Add");
			addData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OperationScenarioDataDialog dialog = new OperationScenarioDataDialog(OperationDialog.this, "", "", CommonConstant.MODE_NEW, txtScenarioId.getText().trim(), txtScenarioName.getText().trim());
					
					iMemoRow = -1;
					setVisible(false);
					Utils.addWindow(dialog);
					dialog.setVisible(true);
				}
			});
		}
		return addData;
	}
	
	public void setPatternName(String strPatternName) {
		txtOperationName.setText(strPatternName);
	}
	public void setPatternId(String strOperationId) {
		patternId.setText(strOperationId);
	}
	
	@SuppressWarnings("unchecked")
	public void mapData(OperationScenarioDataDialog dialog, String strSonMode) {
		if (dialog.isBlnConfirm()) {
			if (CommonConstant.MODE_NEW.equals(strSonMode) ||  CommonConstant.MODE_COPY.equals(strSonMode)) {
				List<Map<String, Object>> dataList = (List<Map<String, Object>>)grid.getValueList();
				Map<String, Object> updateMap = new HashMap<String, Object>();
				updateMap.put("DATA_RULES", dialog.getPropertyMap());
				updateMap.put("DATA_COMMENT", dialog.getComment());
				updateMap.put("DATA_NUMBER", dialog.getNumber());
				updateMap.put("SCENARIO_ID", dialog.getScenarioId());
				updateMap.put("DEAL_1", "Detail");
				updateMap.put("DEAL_2", "Copy");
				updateMap.put("DEAL_3", "Del");
				updateMap.put("DEAL_FLG", CommonConstant.DEAL_INSERT);
				dataList.add(updateMap);
				grid.setData(dataList);
				setScenarioByList();
			} else {
				List<Map<String, Object>> dataList = (List<Map<String, Object>>)grid.getValueList();
				Map<String, Object> updateMap = dataList.get(iMemoRow);
				updateMap.put("DATA_RULES", dialog.getPropertyMap());
				updateMap.put("DATA_COMMENT", dialog.getComment());
				updateMap.put("DATA_NUMBER", dialog.getNumber());
				updateMap.put("SCENARIO_ID", dialog.getScenarioId());
				updateMap.put("DEAL_FLG", CommonConstant.DEAL_UPDATE);
				grid.setData(dataList);
				setScenarioByList();
			
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setScenarioByList() {
		
		List<Map<String, Object>> list = grid.getValueList();
		
		if (list != null && list.size() > 0) {
			Map<String, Object> map = list.get(0);
			String ScenarioId = (String)map.get("SCENARIO_ID");
			
			ScenarioListDao scenarioListDao = new ScenarioListDao();
			try {
				String strScenarioName = scenarioListDao.getScenarioName(ScenarioId);
				
				txtScenarioId.setText(ScenarioId);
				txtScenarioName.setText(strScenarioName);
			} catch (Exception e) {
				logger.exception(e);
				e.printStackTrace();
			}
		} else {
			txtScenarioId.setText("");
			txtScenarioName.setText("");
		}
	}
}  //  @jve:decl-index=0:visual-constraint="6,6"
