package org.app.co.jp.ap;

import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.dao.MailListDao;
import org.app.co.jp.dao.ScriptListDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.GridUtils;
import org.app.co.jp.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MailListDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel excelSheet = null;
	private JButton jButton = null;
	
	private JButton preButton = null;
	private JButton afterButton = null;
	private JLabel lblMailSelect;
	private JButton btnMailSelect = null;
	GridUtils grid = null;
	private JLabel titleMail;
	private JLabel titleMailName;
	private JLabel pageInfoLbl = null;
	
	BasicLogger logger = BasicLogger.getLogger();
	private JTextField searchMailName = null;
	private JButton btnAddMail = null;
	private JLabel titleDeal = null;
	
	private JLabel mailIdInit;
	private JLabel mailNameInit;
	private JLabel titleMailSelect;
	private JButton btnDetailInit = null;
	private JButton btnCopyInit = null;
	private JButton btnDelInit = null;
	private JCheckBox mailSelectInit;
	
	private String strEndMailId = "";
	private String strErrorMailId = "";
	private boolean blnSetMailFlg = false;
	private JLabel lblScriptId;
	private JLabel lblScriptName;
	private JPanel scriptPanel;
	private JPanel endMailPanel;
	private JTextField txtScriptId;
	private JTextField txtScriptName;
	private JLabel lblMailId;
	private JTextField txtEndMailId;
	private JLabel lblEndMailName;
	private JTextField txtEndMailName;
	private JButton btnEndMailSet;
	private JButton btnEndMailDetail;
	private JButton btnEndmailClear;
	private JPanel errorMailPanel;
	private JLabel label;
	private JTextField txtErrorMailId;
	private JLabel label_2;
	private JTextField txtErrorMailName;
	private JButton btnErrorMailSet;
	private JButton btnErrorMailDetail;
	private JButton btnErrorMailClear;
	private JButton btnUpdate;
	private String strScriptId;
	private String strScriptName;
	
	/**
	 * This method initializes 
	 * 
	 */
	public MailListDialog(String strEndMailId, String strErrorMailId, String strScriptId, String strScriptName, boolean blnSetMailFlg) {
		super();
		
		this.strEndMailId = strEndMailId;
		this.strErrorMailId = strErrorMailId;
		this.strScriptId = strScriptId;
		this.strScriptName = strScriptName;
		this.blnSetMailFlg = blnSetMailFlg;
		
		try {
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
        this.setSize(new Dimension(870, 830));
        
        if (!blnSetMailFlg) {
        	this.setSize(new Dimension(870, 565));
        }
        
        
        this.setContentPane(getJPanel());

        List<String> title = new ArrayList<String>();
        title.add("Mail ID");
        title.add("Mail Name");
        title.add("Mail Select");
        title.add("Detail");
        title.add("Copy");
		title.add("Del");

        List<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(mailIdInit);
        componentList.add(mailNameInit);
        componentList.add(mailSelectInit);
        componentList.add(btnDetailInit);
        componentList.add(btnCopyInit);
        componentList.add(btnDelInit);
        String []arrColumn = {"MAIL_ID", "MAIL_NAME", "MAIL_SELECT", "DEAL_1", "DEAL_2", "DEAL_3"};
        String []arrTitle = {"MAIL_ID", "MAIL_NAME", "MAIL_SELECT", "DEAL_1"};
        grid = new GridUtils(excelSheet, title, componentList, arrColumn, preButton, afterButton, 15, arrTitle);
        grid.setPageInfo(pageInfoLbl);
        
        searchDetailList();
        
        txtScriptId.setText(strScriptId);
        txtScriptName.setText(strScriptName);
        
        if (blnSetMailFlg) {
        	txtEndMailId.setText(strEndMailId);
        	txtErrorMailId.setText(strErrorMailId);
        	
        	// get name
        	MailListDao mailListDao = new MailListDao();
        	try {
				txtEndMailName.setText(mailListDao.getMailName(strEndMailId));
				txtErrorMailName.setText(mailListDao.getMailName(strErrorMailId));
			} catch (Exception e) {
				logger.exception(e);
			}
        }
        
        setTitle("Mail List Page");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			titleDeal = new JLabel();
			titleDeal.setBounds(new Rectangle(570, 73, 260, 20));
			titleDeal.setHorizontalAlignment(SwingConstants.CENTER);
			titleDeal.setText("Operation");
			titleDeal.setBackground(new Color(255, 204, 204));
			pageInfoLbl = new JLabel();
			pageInfoLbl.setBounds(new Rectangle(224, 450, 315, 30));
			pageInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
			pageInfoLbl.setText("JLabel");
			titleMailName = new JLabel();
			titleMailName.setBounds(new Rectangle(110, 73, 360, 20));
			titleMailName.setHorizontalAlignment(SwingConstants.CENTER);
			titleMailName.setBackground(new Color(255,204,204));
			titleMailName.setText("Mail Name");
			titleMail = new JLabel();
			titleMail.setBounds(new Rectangle(11, 73, 100, 22));
			titleMail.setHorizontalAlignment(SwingConstants.CENTER);
			titleMail.setText("Mail ID");
			lblMailSelect = new JLabel();
			lblMailSelect.setBounds(new Rectangle(10, 40, 120, 20));
			lblMailSelect.setText("Mail Name");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 10, 400, 24));
			jLabel.setText("View the Mail which has been defined");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getExcelSheet(), null);
			jPanel.add(getPreButton(), null);
			jPanel.add(getAfterButton(), null);
			jPanel.add(lblMailSelect, null);
			jPanel.add(getBtnMailSelect(), null);
			jPanel.add(titleMail, null);
			jPanel.add(titleMailName, null);
			jPanel.add(pageInfoLbl, null);
			jPanel.add(getSearchMailName(), null);
			jPanel.add(getBtnAddMail(), null);
			jPanel.add(titleDeal, null);
			
			titleMailSelect = new JLabel();
			titleMailSelect.setText("Select");
			titleMailSelect.setHorizontalAlignment(SwingConstants.CENTER);
			titleMailSelect.setBounds(new Rectangle(570, 75, 210, 20));
			titleMailSelect.setBackground(new Color(255, 204, 204));
			titleMailSelect.setBounds(470, 73, 100, 20);
			jPanel.add(titleMailSelect);
			jPanel.add(getScriptPanel());
			jPanel.add(getBtnUpdate());
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
			mailNameInit = new JLabel();
			mailNameInit.setBounds(new Rectangle(100, 0, 360, 20));
			mailNameInit.setText("JLabel");
			mailIdInit = new JLabel();
			mailIdInit.setHorizontalAlignment(SwingConstants.CENTER);
			mailIdInit.setBounds(new java.awt.Rectangle(3,0,100,20));
			mailIdInit.setText("JLabel");
			excelSheet = new JPanel();
			excelSheet.setBounds(new Rectangle(10, 95, 830, 350));
			excelSheet.setLayout(null);
			excelSheet.add(mailIdInit, null);
			excelSheet.add(mailNameInit, null);
			excelSheet.add(getBtnDetailInit(), null);
			excelSheet.add(getBtnCopyInit(), null);
			excelSheet.add(getBtnDelInit(), null);
			excelSheet.add(getMailSelectInit());
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
			jButton.setSize(new Dimension(90,30));
			jButton.setLocation(new Point(710, 490));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return jButton;
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
			preButton.setSize(new Dimension(130, 30));
			preButton.setLocation(new Point(10, 450));
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
			afterButton.setLocation(new Point(710, 450));
			afterButton.setText("Next Page");
			afterButton.setSize(new Dimension(130, 30));
		}
		return afterButton;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnMailSelect() {
		if (btnMailSelect == null) {
			btnMailSelect = new JButton();
			btnMailSelect.setText("Search");
			btnMailSelect.setLocation(new Point(266, 40));
			btnMailSelect.setSize(new Dimension(110,20));
			btnMailSelect.setPreferredSize(new Dimension(70, 30));
			btnMailSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						searchDetailList();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(MailListDialog.this, "Search Failed!");
						logger.exception(e1);
					}
				}
			});
		}
		return btnMailSelect;
	}

	/**
	 * This method initializes searchPatternName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchMailName() {
		if (searchMailName == null) {
			searchMailName = new JTextField();
			searchMailName.setBounds(new Rectangle(130, 40, 130, 20));
		}
		return searchMailName;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAddMail() {
		if (btnAddMail == null) {
			btnAddMail = new JButton();
			btnAddMail.setBounds(new Rectangle(730, 40, 110, 20));
			btnAddMail.setText("Add Mail");
			btnAddMail.setPreferredSize(new Dimension(70, 30));
			btnAddMail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MailDialog dialog = new MailDialog("", "", CommonConstant.MODE_NEW);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					
					searchDetailList();
				}
			});
		}
		return btnAddMail;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDetailInit() {
		if (btnDetailInit == null) {
			btnDetailInit = new JButton();
			btnDetailInit.setBounds(new Rectangle(557, 0, 95, 20));
			btnDetailInit.setText("Detail");
//			btnDetailInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnDetailInit.setPreferredSize(new Dimension(70, 30));
			btnDetailInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iRow = 0;
					List compList = grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(3))) {
							iRow = i;
						}
					}
					String strMailId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					String strMailName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					MailDialog dialog = new MailDialog(strMailId, strMailName, CommonConstant.MODE_UPDATE);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					
					int iPageNo = grid.getPageNo();
					
					dialog.setModal(true);
					dialog.setVisible(true);
					searchDetailList();
					
					grid.setPageNo(iPageNo);
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
			btnCopyInit.setBounds(new Rectangle(657, 0, 90, 20));
			btnCopyInit.setText("Copy");
//			btnCopyInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnCopyInit.setPreferredSize(new Dimension(70, 30));
			btnCopyInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iRow = 0;
					List compList = grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(4))) {
							iRow = i;
						}
					}
					String strMailId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					String strMailName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					MailDialog dialog = new MailDialog(strMailId, strMailName, CommonConstant.MODE_COPY);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					
					dialog.setModal(true);
					dialog.setVisible(true);
					searchDetailList();
				}
			});
		}
		return btnCopyInit;
	}
	
	/**
	 *
	 */
	public void searchDetailList() {
		MailListDao dao = new MailListDao();
		String strSearchTxt = searchMailName.getText().trim();
		List<Map<String, String>> list = dao.searchList(strSearchTxt);
		//
		grid.setData(list);
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelInit() {
		if (btnDelInit == null) {
			btnDelInit = new JButton();
			btnDelInit.setBounds(new Rectangle(752, 0, 70, 20));
			btnDelInit.setPreferredSize(new Dimension(70, 30));
			btnDelInit.setText("Del");
//			btnDelInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnDelInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iResult = JOptionPane.showConfirmDialog(MailListDialog.this, "Do you want to delete?");
					if (iResult != JOptionPane.YES_OPTION) {
						return;
					}
					int iRow = 0;
					List compList = grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(5))) {
							iRow = i;
						}
					}
					String strMailId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					MailListDao dao = new MailListDao();
					dao.deleteByList(strMailId);
			        searchDetailList();
				}
			});
		}
		return btnDelInit;
	}
	private JCheckBox getMailSelectInit() {
		if (mailSelectInit == null) {
			mailSelectInit = new JCheckBox("");
			mailSelectInit.setHorizontalAlignment(SwingConstants.CENTER);
			mailSelectInit.setBounds(460, 0, 80, 20);
		}
		return mailSelectInit;
	}

	public String getStrEndMailId() {
		return strEndMailId;
	}

	public void setStrEndMailId(String strEndMailId) {
		this.strEndMailId = strEndMailId;
	}

	public String getStrErrorMailId() {
		return strErrorMailId;
	}

	public void setStrErrorMailId(String strErrorMailId) {
		this.strErrorMailId = strErrorMailId;
	}
	
	private JLabel getLblScriptId() {
		if (lblScriptId == null) {
			lblScriptId = new JLabel();
			lblScriptId.setBounds(10, 30, 100, 22);
			lblScriptId.setText("Script ID");
		}
		return lblScriptId;
	}
	private JLabel getLblScriptName() {
		if (lblScriptName == null) {
			lblScriptName = new JLabel();
			lblScriptName.setBounds(225, 30, 110, 22);
			lblScriptName.setText("Script Name");
		}
		return lblScriptName;
	}
	private JPanel getScriptPanel() {
		if (scriptPanel == null) {
			scriptPanel = new JPanel();
			scriptPanel.setBorder(new TitledBorder(null, "Script Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			scriptPanel.setBounds(10, 540, 700, 240);
			scriptPanel.setLayout(null);
			scriptPanel.add(getLblScriptName());
			scriptPanel.add(getLblScriptId());
			scriptPanel.add(getEndMailPanel());
			scriptPanel.add(getTxtScriptId());
			scriptPanel.add(getTxtScriptName());
			scriptPanel.add(getErrorMailPanel());
		}
		return scriptPanel;
	}
	private JPanel getEndMailPanel() {
		if (endMailPanel == null) {
			endMailPanel = new JPanel();
			endMailPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "End Mail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			endMailPanel.setBounds(5, 60, 675, 80);
			endMailPanel.setLayout(null);
			endMailPanel.add(getLblMailId());
			endMailPanel.add(getTxtEndMailId());
			endMailPanel.add(getLblEndMailName());
			endMailPanel.add(getTxtEndMailName());
			endMailPanel.add(getBtnEndMailSet());
			endMailPanel.add(getBtnEndMailDetail());
			endMailPanel.add(getBtnEndmailClear());
		}
		return endMailPanel;
	}
	private JTextField getTxtScriptId() {
		if (txtScriptId == null) {
			txtScriptId = new JTextField();
			txtScriptId.setEditable(false);
			txtScriptId.setBounds(115, 30, 100, 22);
			txtScriptId.setColumns(10);
		}
		return txtScriptId;
	}
	private JTextField getTxtScriptName() {
		if (txtScriptName == null) {
			txtScriptName = new JTextField();
			txtScriptName.setEditable(false);
			txtScriptName.setColumns(10);
			txtScriptName.setBounds(340, 30, 355, 22);
		}
		return txtScriptName;
	}
	private JLabel getLblMailId() {
		if (lblMailId == null) {
			lblMailId = new JLabel();
			lblMailId.setText("Mail ID");
			lblMailId.setBounds(10, 21, 80, 22);
		}
		return lblMailId;
	}
	private JTextField getTxtEndMailId() {
		if (txtEndMailId == null) {
			txtEndMailId = new JTextField();
			txtEndMailId.setEditable(false);
			txtEndMailId.setColumns(10);
			txtEndMailId.setBounds(95, 21, 90, 22);
		}
		return txtEndMailId;
	}
	private JLabel getLblEndMailName() {
		if (lblEndMailName == null) {
			lblEndMailName = new JLabel();
			lblEndMailName.setText("Mail Name");
			lblEndMailName.setBounds(200, 21, 100, 22);
		}
		return lblEndMailName;
	}
	private JTextField getTxtEndMailName() {
		if (txtEndMailName == null) {
			txtEndMailName = new JTextField();
			txtEndMailName.setEditable(false);
			txtEndMailName.setColumns(10);
			txtEndMailName.setBounds(295, 21, 375, 22);
		}
		return txtEndMailName;
	}
	private JButton getBtnEndMailSet() {
		if (btnEndMailSet == null) {
			btnEndMailSet = new JButton("Set");
			btnEndMailSet.addActionListener(new ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(ActionEvent e) {
					int iRow = -1;
					int iCount = 0;
					
					List compList = grid.getComponentList();
					
					for (int i = 0; i < compList.size(); i++) {
						JCheckBox jcb = (JCheckBox)((List)compList.get(i)).get(2);
						if (jcb.isSelected()) {
							iRow = i;
							iCount ++;
						}
					}
					
					// check the script
					if (iCount == 0) {
						JOptionPane.showMessageDialog(MailListDialog.this, "Please select one on the list to set!");
						return;
					} else if (iCount > 1) {
						JOptionPane.showMessageDialog(MailListDialog.this, "You just can select one at onetime!");
						return;
					}
					
					// get mail id and name
					String strMailId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					String strMailName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					
					txtEndMailId.setText(strMailId);
					txtEndMailName.setText(strMailName);
					
				}
			});
			btnEndMailSet.setBounds(10, 49, 70, 22);
		}
		return btnEndMailSet;
	}
	private JButton getBtnEndMailDetail() {
		if (btnEndMailDetail == null) {
			btnEndMailDetail = new JButton("Detail");
			btnEndMailDetail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (Utils.isEmpty(txtEndMailId.getText().trim())) {
							JOptionPane.showMessageDialog(MailListDialog.this, "No mail has been set!");
							return;
						}

						String strMailId = txtEndMailId.getText();
						String strMailName = txtEndMailName.getText();
						MailDialog dialog = new MailDialog(strMailId, strMailName, CommonConstant.MODE_UPDATE);
						dialog.setModal(true);
						dialog.setVisible(true);
						searchDetailList();

						MailListDao mailListDao = new MailListDao();
						txtEndMailName.setText(mailListDao.getMailName(strMailId));
					} catch (Exception e1) {
						logger.exception(e1);
					}

				}
			});
			btnEndMailDetail.setBounds(90, 49, 100, 22);
		}
		return btnEndMailDetail;
	}
	private JButton getBtnEndmailClear() {
		if (btnEndmailClear == null) {
			btnEndmailClear = new JButton("Clear");
			btnEndmailClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(MailListDialog.this, "Do you want to clear the select mail?");
					
					if (result == JOptionPane.OK_OPTION) {
						txtEndMailId.setText("");
						txtEndMailName.setText("");
					}
				}
			});
			btnEndmailClear.setBounds(200, 49, 100, 22);
		}
		return btnEndmailClear;
	}
	private JPanel getErrorMailPanel() {
		if (errorMailPanel == null) {
			errorMailPanel = new JPanel();
			errorMailPanel.setLayout(null);
			errorMailPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Error Mail", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			errorMailPanel.setBounds(5, 140, 675, 80);
			errorMailPanel.add(getLabel());
			errorMailPanel.add(getTxtErrorMailId());
			errorMailPanel.add(getLabel_2());
			errorMailPanel.add(getTxtErrorMailName());
			errorMailPanel.add(getBtnErrorMailSet());
			errorMailPanel.add(getBtnErrorMailDetail());
			errorMailPanel.add(getBtnErrorMailClear());
		}
		return errorMailPanel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("Mail ID");
			label.setBounds(10, 21, 80, 22);
		}
		return label;
	}
	private JTextField getTxtErrorMailId() {
		if (txtErrorMailId == null) {
			txtErrorMailId = new JTextField();
			txtErrorMailId.setEditable(false);
			txtErrorMailId.setColumns(10);
			txtErrorMailId.setBounds(95, 21, 90, 22);
		}
		return txtErrorMailId;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("Script Name");
			label_2.setBounds(200, 21, 100, 22);
		}
		return label_2;
	}
	private JTextField getTxtErrorMailName() {
		if (txtErrorMailName == null) {
			txtErrorMailName = new JTextField();
			txtErrorMailName.setEditable(false);
			txtErrorMailName.setColumns(10);
			txtErrorMailName.setBounds(295, 21, 375, 22);
		}
		return txtErrorMailName;
	}
	private JButton getBtnErrorMailSet() {
		if (btnErrorMailSet == null) {
			btnErrorMailSet = new JButton("Set");
			btnErrorMailSet.addActionListener(new ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(ActionEvent e) {
					int iRow = -1;
					int iCount = 0;
					
					List compList = grid.getComponentList();
					
					for (int i = 0; i < compList.size(); i++) {
						JCheckBox jcb = (JCheckBox)((List)compList.get(i)).get(2);
						if (jcb.isSelected()) {
							iRow = i;
							iCount ++;
						}
					}
					
					// check the script
					if (iCount == 0) {
						JOptionPane.showMessageDialog(MailListDialog.this, "Please select one on the list to set!");
						return;
					} else if (iCount > 1) {
						JOptionPane.showMessageDialog(MailListDialog.this, "You just can select one at onetime!");
						return;
					}
					
					// get mail id and name
					String strMailId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					String strMailName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					
					txtErrorMailId.setText(strMailId);
					txtErrorMailName.setText(strMailName);
					
				}
			});
			btnErrorMailSet.setBounds(10, 49, 70, 22);
		}
		return btnErrorMailSet;
	}
	private JButton getBtnErrorMailDetail() {
		if (btnErrorMailDetail == null) {
			btnErrorMailDetail = new JButton("Detail");
			btnErrorMailDetail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (Utils.isEmpty(txtErrorMailId.getText().trim())) {
							JOptionPane.showMessageDialog(MailListDialog.this, "No mail has been set!");
							return;
						}

						String strMailId = txtErrorMailId.getText();
						String strMailName = txtErrorMailName.getText();
						MailDialog dialog = new MailDialog(strMailId, strMailName, CommonConstant.MODE_UPDATE);
						dialog.setModal(true);
						dialog.setVisible(true);
						searchDetailList();

						MailListDao mailListDao = new MailListDao();
						txtErrorMailName.setText(mailListDao.getMailName(strMailId));
					} catch (Exception e1) {
						logger.exception(e1);
					}

				}
			});
			btnErrorMailDetail.setBounds(90, 49, 100, 22);
		}
		return btnErrorMailDetail;
	}
	private JButton getBtnErrorMailClear() {
		if (btnErrorMailClear == null) {
			btnErrorMailClear = new JButton("Clear");
			btnErrorMailClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(MailListDialog.this, "Do you want to clear the select mail?");
					
					if (result == JOptionPane.OK_OPTION) {
						txtErrorMailId.setText("");
						txtErrorMailName.setText("");
					}
				}
			});
			btnErrorMailClear.setBounds(200, 49, 100, 22);
		}
		return btnErrorMailClear;
	}
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton();
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ScriptListDao dao = new ScriptListDao();
					try {
						dao.updateScriptMail(strScriptId, txtEndMailId.getText(), txtErrorMailId.getText());
						setVisible(false);
					} catch (IOException e1) {
						logger.exception(e1);
						e1.printStackTrace();
					}
				}
			});
			btnUpdate.setText("Update");
			btnUpdate.setSize(new Dimension(90, 30));
			btnUpdate.setLocation(new Point(690, 491));
			btnUpdate.setBounds(710, 745, 130, 30);
		}
		return btnUpdate;
	}
	
}  //  @jve:decl-index=0:visual-constraint="6,6"
