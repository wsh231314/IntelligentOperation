package org.app.co.jp.ap;

import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.dao.OperationDataDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Check;
import org.app.co.jp.util.Utils;
import org.app.co.jp.util.bean.DefaultComboBoxModel;
import org.app.co.jp.util.bean.SelectBean;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OperationScenarioDataDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JButton btnDataCreate = null;
	private JButton jButton = null;
	
	private File initExcelFile;
	private JLabel lblScenario;
	BasicLogger logger = BasicLogger.getLogger();
	private JTextField txtScenarioId = null;
	private String strMode = "";
	private String strDataId = "";
	
	private JButton btnConfirm = null;
	private JPanel tablePanel = null;
	private JLabel columnLbl = null;
	
	@SuppressWarnings("rawtypes")
	private JComboBox fieldComBox = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTextField txtFixedValue = null;
	private JTextField txtPattern = null;
	private JTextField txtRadom = null;
	private JTextField txtLength = null;
	private JLabel lblScenarioName = null;
	
	private Map<String, Map<String, String>> propertyMap = new LinkedHashMap<String, Map<String, String>>();
	
	private List<Map<String, String>> columnList = null;
	
	private boolean blnConfirm = false;
	
	private String strOlderValue = "";
	private JLabel lblDataComment;
	private JLabel jLabel7 = null;
	private JTextField txtDataComment = null;
	private JTextField txtDataNumber = null;
	private JButton btnDetail = null;
	
	private String strScenarioId;
	private String strScenarioName;
	private OperationDialog _parent;
	
	
	/**
	 * This method initializes 
	 * 
	 */
	public OperationScenarioDataDialog(OperationDialog parent, String strDataId, String strDataName, String strMode, String strScenarioId, String strScenarioName) {
		super();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				Utils.removeWindow(OperationScenarioDataDialog.this);
				_parent.setVisible(true);
			}
		});
		
		try {
			this._parent = parent;
			this.strDataId = strDataId;
			this.strMode = strMode;
			this.strScenarioId = strScenarioId;
			this.strScenarioName = strScenarioName;
			initialize();
		} catch(Exception e) {
			logger.exception(e);
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(650,450));
        this.setContentPane(getJPanel());
        
        blnConfirm = false;
        
        setDisplayData();
       
		if (CommonConstant.MODE_NEW.equals(strMode)) {
			btnDataCreate.setText("Create");
		} else if (CommonConstant.MODE_COPY.equals(strMode)) {
			btnDataCreate.setText("Copy");
		} else {
			btnDataCreate.setText("Update");
		}
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(10, 135, 80, 20));
			jLabel7.setText("Rec Num");
			lblDataComment = new JLabel();
			lblDataComment.setBounds(new Rectangle(10, 105, 80, 20));
			lblDataComment.setText("Comment");
			lblScenario = new JLabel();
			lblScenario.setBounds(new java.awt.Rectangle(10,40,80,20));
			lblScenario.setText("Scenario");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(10,10,500,24));
			jLabel.setText("Input the field's definition");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getBtnDataCreate(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(lblScenario, null);
			jPanel.add(getSearchPatternName(), null);
			jPanel.add(getBtnConfirm(), null);
			jPanel.add(getTablePanel(), null);
			jPanel.add(lblDataComment, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTxtDataComment(), null);
			jPanel.add(getTxtDataNumber(), null);
			
			lblScenarioName = new JLabel();
			lblScenarioName.setBounds(new Rectangle(10, 105, 80, 20));
			lblScenarioName.setBounds(100, 70, 300, 20);
			jPanel.add(lblScenarioName);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnExcelCreate	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDataCreate() {
		if (btnDataCreate == null) {
			btnDataCreate = new JButton();
			btnDataCreate.setText("Confirm");
			btnDataCreate.setSize(new java.awt.Dimension(150,30));
			btnDataCreate.setLocation(new java.awt.Point(10,370));
			btnDataCreate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						if (Check.isNull(txtDataComment.getText().trim())) {
							JOptionPane.showMessageDialog(OperationScenarioDataDialog.this, "Please input the comment!");
							return;
						}
						if (Check.isNull(txtDataNumber.getText().trim())) {
							JOptionPane.showMessageDialog(OperationScenarioDataDialog.this, "Please input the record number!");
							return;
						}
						try {
							Integer.parseInt(txtDataNumber.getText().trim());
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(OperationScenarioDataDialog.this, "The record number is not right!");
							return;
						}
						
						if (!Check.isNull(strOlderValue)) {
							Map<String, String> selectMap = propertyMap.get(strOlderValue);
							//
							selectMap.put("FIELD_FIRM", txtFixedValue.getText().trim());
							selectMap.put("FIELD_PREFIX", txtPattern.getText().trim());
							selectMap.put("FIELD_RADOM", txtRadom.getText().trim());
							selectMap.put("FIELD_LENGTH", txtLength.getText().trim());
						}
						
						blnConfirm = true;
						
						setVisible(false);
						Utils.removeWindow(OperationScenarioDataDialog.this);
						_parent.mapData(OperationScenarioDataDialog.this, strMode);
						_parent.setVisible(true);
						
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(OperationScenarioDataDialog.this, "Failed!");
						logger.exception(e1);
					}
				}
			});
		}
		return btnDataCreate;
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
			jButton.setLocation(new java.awt.Point(540,370));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					Utils.removeWindow(OperationScenarioDataDialog.this);
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
	 * This method initializes searchPatternName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchPatternName() {
		if (txtScenarioId == null) {
			txtScenarioId = new JTextField();
			txtScenarioId.setBounds(new java.awt.Rectangle(100,40,200,20));
		}
		return txtScenarioId;
	}

	/**
	 *
	 */
	private void setDisplayData() {
		propertyClear();
		if (Utils.isEmpty(strScenarioId)) {
			setDisplayControl(CommonConstant.MODE_NEW);
		} else {
			setDisplayControl(CommonConstant.MODE_UPDATE);
			txtScenarioId.setText(strScenarioId);
			lblScenarioName.setText(strScenarioName);
			//
			try {
				setColumnCobValue();
			} catch (Exception e) {
				logger.exception(e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *
	 */
	private void setDisplayControl(String sMode) {
		if (CommonConstant.MODE_NEW.equals(sMode)) {
			txtScenarioId.setEditable(true);
			btnConfirm.setEnabled(true);
			
			txtDataComment.setEditable(false);
			txtDataNumber.setEditable(false);
			fieldComBox.setEnabled(false);
			txtFixedValue.setEditable(false);
			txtPattern.setEditable(false);
			txtRadom.setEditable(false);
			txtLength.setEditable(false);
			btnDataCreate.setEnabled(false);
			btnDetail.setEnabled(false);
		} else {
			txtScenarioId.setEditable(false);
			btnConfirm.setEnabled(false);
			
			txtDataComment.setEditable(true);
			txtDataNumber.setEditable(true);
			fieldComBox.setEnabled(true);
			txtFixedValue.setEditable(true);
			txtPattern.setEditable(true);
			txtRadom.setEditable(true);
			txtLength.setEditable(true);
			btnDataCreate.setEnabled(true);
			btnDetail.setEnabled(true);
		}
	}
	
	/**
	 *
	 */
	private void propertyClear() {
		txtFixedValue.setText("");
		txtPattern.setText("");
		txtRadom.setText("");
		txtLength.setText("");
	}
	
	
	/**
	 * This method initializes btnConfirm	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnConfirm() {
		if (btnConfirm == null) {
			btnConfirm = new JButton();
			btnConfirm.setBounds(new Rectangle(310, 40, 100, 20));
			btnConfirm.setText("Confirm");
			btnConfirm.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						if (setColumnCobValue()) {
							setDisplayControl(CommonConstant.MODE_UPDATE);
							
							for (Map<String, String> map : columnList) {
								HashMap<String, String> mapValue = new HashMap<String, String>();
								
								mapValue.put("FIELD_FIRM", "");
								mapValue.put("FIELD_PREFIX", "");
								mapValue.put("FIELD_RADOM", "");
								mapValue.put("FIELD_LENGTH", "");
								
								String strCode = txtScenarioId.getText().trim().concat("-").concat(map.get("STEP_ID")).concat("-").concat(map.get("PAGE_ID")).concat("-").concat(map.get("FIELD_ID"));
								propertyMap.put(strCode, mapValue);
							}
						}
					} catch (Exception e1) {
						logger.exception(e1);
						e1.printStackTrace();
					} 
				}
			});
		}
		return btnConfirm;
	}

	/**
	 * This method initializes tablePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTablePanel() {
		if (tablePanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(10, 134, 110, 20));
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel5.setText("Length");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(10, 104, 110, 20));
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel4.setText("Radom");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(10, 74, 110, 20));
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel3.setText("Patten");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(10,44,110,20));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel1.setText("Fixed value");
			columnLbl = new JLabel();
			columnLbl.setBounds(new Rectangle(10, 10, 300, 20));
			columnLbl.setText("Please select the field");
			tablePanel = new JPanel();
			tablePanel.setLayout(null);
			tablePanel.setBounds(new Rectangle(10, 160, 620, 190));
			tablePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			tablePanel.add(columnLbl, null);
			tablePanel.add(getFieldComBox(), null);
			tablePanel.add(jLabel1, null);
			tablePanel.add(jLabel3, null);
			tablePanel.add(jLabel4, null);
			tablePanel.add(jLabel5, null);
			tablePanel.add(getTxtFixedValue(), null);
			tablePanel.add(getTxtPattern(), null);
			tablePanel.add(getTxtRadom(), null);
			tablePanel.add(getTxtLength(), null);
			tablePanel.add(getBtnDetail(), null);
		}
		return tablePanel;
	}

	/**
	 * This method initializes fieldComBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox getFieldComBox() {
		if (fieldComBox == null) {
			fieldComBox = new JComboBox();
			fieldComBox.setBounds(new java.awt.Rectangle(310,10,300,20));
			fieldComBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectBean bean = (SelectBean)fieldComBox.getSelectedItem();
					if (!Check.isNull(strOlderValue)) {
						Map<String, String> selectMap = propertyMap.get(strOlderValue);
						//
						selectMap.put("FIELD_FIRM", txtFixedValue.getText().trim());
						selectMap.put("FIELD_PREFIX", txtPattern.getText().trim());
						selectMap.put("FIELD_RADOM", txtRadom.getText().trim());
						selectMap.put("FIELD_LENGTH", txtLength.getText().trim());
					}
					strOlderValue = bean.getCode();
					
					if (Check.isNull(bean.getCode())) {
						txtFixedValue.setText("");
						txtPattern.setText("");
						txtRadom.setText("");
						txtLength.setText("");
					} else {
						Map selectMap = (Map)propertyMap.get(bean.getCode());
						//
						txtFixedValue.setText((String)selectMap.get("FIELD_FIRM"));
						txtPattern.setText((String)selectMap.get("FIELD_PREFIX"));
						txtRadom.setText((String)selectMap.get("FIELD_RADOM"));
						txtLength.setText((String)selectMap.get("FIELD_LENGTH"));
					}
				}
			});
		}
		return fieldComBox;
	}

	/**
	 * This method initializes txtKoteiTi	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtFixedValue() {
		if (txtFixedValue == null) {
			txtFixedValue = new JTextField();
			txtFixedValue.setBounds(new java.awt.Rectangle(130,44,400,20));
		}
		return txtFixedValue;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtPattern() {
		if (txtPattern == null) {
			txtPattern = new JTextField();
			txtPattern.setBounds(new Rectangle(130, 74, 400, 20));
		}
		return txtPattern;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtRadom() {
		if (txtRadom == null) {
			txtRadom = new JTextField();
			txtRadom.setBounds(new Rectangle(130, 104, 400, 20));
		}
		return txtRadom;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtLength() {
		if (txtLength == null) {
			txtLength = new JTextField();
			txtLength.setBounds(new Rectangle(130, 134, 400, 20));
		}
		return txtLength;
	}
	
	/**
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private boolean setColumnCobValue() throws Exception {
		//
		OperationDataDao dao = new OperationDataDao();
		String strId = txtScenarioId.getText().trim().toUpperCase();
		txtScenarioId.setText(strId);
		Vector<SelectBean> selectList = new Vector<SelectBean>();

		if (!dao.hasScenarioId(strId)) {
			JOptionPane.showMessageDialog(OperationScenarioDataDialog.this, "Scenario is not exists or no step has been defined!");
			return false;
		}

		List<Map<String, String>>  columnList = dao.getColumns(strId) ;

		SelectBean blank = new SelectBean();
		blank.setCode("");
		blank.setName("");
		selectList.add(blank);
		for (Map<String, String> map : columnList) {
			SelectBean bean = new SelectBean();

			String strCode = txtScenarioId.getText().trim().concat("-").concat(map.get("STEP_ID")).concat("-").concat(map.get("PAGE_ID")).concat("-").concat(map.get("FIELD_ID"));
			String strName = map.get("STEP_ID").concat(":").concat(map.get("PAGE_NAME")).concat("#").concat(map.get("FIELD_NAME"));

			bean.setCode(strCode);
			bean.setName(strName);
			selectList.add(bean);
		}
		this.columnList = columnList;
		// 
		DefaultComboBoxModel modal = new DefaultComboBoxModel(selectList);
		// 
		fieldComBox.setModel(modal);
		return true;
	}

	/**
	 */
	public boolean isBlnConfirm() {
		return blnConfirm;
	}

	/**
	 */
	public void setBlnConfirm(boolean blnConfirm) {
		this.blnConfirm = blnConfirm;
	}

	/**
	 */
	public Map<String, Map<String, String>> getPropertyMap() {
		return propertyMap;
	}

	/**
	 */
	public void setPropertyMap(Map<String, Map<String, String>> propertyMap) {
		this.propertyMap = propertyMap;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtDataComment() {
		if (txtDataComment == null) {
			txtDataComment = new JTextField();
			txtDataComment.setBounds(new Rectangle(100, 105, 400, 20));
		}
		return txtDataComment;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtDataNumber() {
		if (txtDataNumber == null) {
			txtDataNumber = new JTextField();
			txtDataNumber.setBounds(new Rectangle(100, 135, 70, 20));
		}
		return txtDataNumber;
	}
	
	public String getNumber() {
		return txtDataNumber.getText().trim();
	}
	
	public String getComment() {
		return txtDataComment.getText().trim();
	}
	
	public String getScenarioId() {
		return txtScenarioId.getText().trim();
	}
	
	public void setCommentStr(String comment) {
		txtDataComment.setText(comment);
	}
	
	public void setNumberStr(String name) {
		txtDataNumber.setText(name);
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDetail() {
		if (btnDetail == null) {
			btnDetail = new JButton();
			btnDetail.setText("Detail");
			btnDetail.setBounds(new Rectangle(526, 165, 86, 19));
			btnDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						if (!Check.isNull(strOlderValue)) {
							Map<String, String> selectMap = propertyMap.get(strOlderValue);
							//
							selectMap.put("FIELD_FIRM", txtFixedValue.getText().trim());
							selectMap.put("FIELD_PREFIX", txtPattern.getText().trim());
							selectMap.put("FIELD_RADOM", txtRadom.getText().trim());
							selectMap.put("FIELD_LENGTH", txtLength.getText().trim());
						}
						
						OperationInfoListDialog dialog = new OperationInfoListDialog(OperationScenarioDataDialog.this, strDataId, lblScenarioName.getText(), txtDataComment.getText(), propertyMap);

				        // --------------------------------------------------------
				        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
				        // -------------------------------------------------------
						dialog.setLocationRelativeTo(null);
						setVisible(false);
						Utils.addWindow(dialog);
						dialog.setVisible(true);
					} catch (Exception e1) {
						//
						logger.exception(e1);
					}
				}
			});
		}
		return btnDetail;
	}
}  //  @jve:decl-index=0:visual-constraint="6,6"
