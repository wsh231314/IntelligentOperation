package org.app.co.jp.ap;

import org.app.co.jp.dao.ScriptListDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.GridUtils;
import org.app.co.jp.util.RunHelp;
import org.app.co.jp.util.ScriptUtils;
import org.app.co.jp.util.Utils;
import org.sikuli.ide.SikuliIDE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScriptListDialog extends JDialog {

	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel excelSheet = null;
	private JButton btnExecute = null;
	private JButton jButton = null;
	
	private File initExcelFile;
	private JButton preButton = null;
	private JButton afterButton = null;
	private JLabel scriptIdInit;
	private JCheckBox scriptCheckInit = null;
	private JLabel lblScriptSelect;
	private JButton scriptSelect = null;
	private JLabel scriptNameInit;
	GridUtils grid = null;
	private JLabel titleScriptId;
	private JLabel titleScriptName;
	private JLabel titleSelect = null;
	private JLabel pageInfoLbl = null;
	
	BasicLogger logger = BasicLogger.getLogger();
	private JTextField searchScriptName = null;
	private JButton jButton1 = null;
	private JLabel titleDeal = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JLabel titleScriptFile;
	private JLabel scriptFileInit;
	private JButton jButton4 = null;
	public List<String> _console;
	private JLabel titleMailSetting;
	private JButton btnMailSettingInit;
	
	/**
	 * This method initializes 
	 * 
	 */
	public ScriptListDialog() {
		super();
		try {
			initialize();
			
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					hide();
					SikuliIDE.showMain();
				}
			});
			
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
        this.setSize(new Dimension(1000, 600));
        this.setContentPane(getJPanel());

        List<String> title = new ArrayList<String>();
        title.add("Pattern ID");
        title.add("Pattern Name");
        title.add("Pattern Type");
        title.add("Mail Setting");
        title.add("Used");
        title.add("Detail");
        title.add("Copy");
		title.add("Del");

        List<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(scriptIdInit);
        componentList.add(scriptNameInit);
        componentList.add(scriptFileInit);
        componentList.add(btnMailSettingInit);
        componentList.add(scriptCheckInit);
        componentList.add(jButton2);
        componentList.add(jButton3);
        componentList.add(jButton4);
        String []arrColumn = {"SCRIPT_ID", "SCRIPT_NAME", "SCRIPT_FILE", "MAIL_SET", "SELECT", "DEAL_1", "DEAL_2", "DEAL_3"};
        String []arrTitle = {"SCRIPT_ID", "SCRIPT_NAME", "SCRIPT_FILE", "MAIL_SET", "SELECT", "DEAL_1"};
        grid = new GridUtils(excelSheet, title, componentList, arrColumn, preButton, afterButton, 12, arrTitle);
        grid.setPageInfo(pageInfoLbl);
        
        searchDetailList();
        
        setTitle("Script List Page");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			titleScriptFile = new JLabel();
			titleScriptFile.setBounds(new Rectangle(380, 73, 120, 20));
			titleScriptFile.setHorizontalAlignment(SwingConstants.CENTER);
			titleScriptFile.setText("Script File");
			titleScriptFile.setBackground(new Color(255, 204, 204));
			titleDeal = new JLabel();
			titleDeal.setBounds(new Rectangle(727, 73, 210, 20));
			titleDeal.setHorizontalAlignment(SwingConstants.CENTER);
			titleDeal.setText("Operation");
			titleDeal.setBackground(new Color(255, 204, 204));
			pageInfoLbl = new JLabel();
			pageInfoLbl.setBounds(new Rectangle(274, 402, 315, 30));
			pageInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
			pageInfoLbl.setText("JLabel");
			titleSelect = new JLabel();
			titleSelect.setBounds(new Rectangle(650, 73, 70, 20));
			titleSelect.setHorizontalAlignment(SwingConstants.CENTER);
			titleSelect.setBackground(new Color(255,204,204));
			titleSelect.setText("Select");
			titleScriptName = new JLabel();
			titleScriptName.setBounds(new Rectangle(110, 73, 270, 20));
			titleScriptName.setHorizontalAlignment(SwingConstants.CENTER);
			titleScriptName.setBackground(new Color(255,204,204));
			titleScriptName.setText("Script Name");
			titleScriptId = new JLabel();
			titleScriptId.setBounds(new Rectangle(11, 73, 100, 22));
			titleScriptId.setHorizontalAlignment(SwingConstants.CENTER);
			titleScriptId.setText("Script ID");
			lblScriptSelect = new JLabel();
			lblScriptSelect.setBounds(new Rectangle(10, 40, 130, 20));
			lblScriptSelect.setText("Script Name");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 10, 800, 24));
			jLabel.setText("Select the Script which you want to execute.One time just one script can be execute.");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getBtnExecute(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getExcelSheet(), null);
			jPanel.add(getPreButton(), null);
			jPanel.add(getAfterButton(), null);
			jPanel.add(lblScriptSelect, null);
			jPanel.add(getScriptSelect(), null);
			jPanel.add(titleScriptId, null);
			jPanel.add(titleScriptName, null);
			jPanel.add(titleSelect, null);
			jPanel.add(pageInfoLbl, null);
			jPanel.add(getSearchScriptName(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(titleDeal, null);
			jPanel.add(titleScriptFile, null);
			
			JButton btnTimer = new JButton();
			btnTimer.addActionListener(new ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(ActionEvent e) {
					try {
						// add datetime dialog by chunhui.li is tart
				        DateTimeChooserDialog dDialog = new DateTimeChooserDialog(ScriptListDialog.this, "日付タイム選択", true);
				        String strDate = dDialog.getInputValue();
						// add datetime dialog by chunhui.li is tart
						
						DateTimeChooserDialog dDialog = new DateTimeChooserDialog(ScriptListDialog.this, "日付タイム選択", true);
						String strDate = dDialog.getInputValue();
						
						
						if (Utils.isEmpty(strDate)) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "Please input the date!");
							return;
						}
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						
						Date strExecuteDate = null;
						
						try {
							strExecuteDate = sdf.parse(strDate);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "The Time format is [yyyy/MM/dd HH:mm:ss]!");
							return;
						}
						
						Date nowDate = new Date();
						nowDate.setMinutes(nowDate.getMinutes() + 5);
						
						if (strExecuteDate.before(nowDate)) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "Please input a future time!");
							return;
						}
						
						int iRow = -1;
						int iCount = 0;
						
						List compList = grid.getComponentList();
						
						for (int i = 0; i < compList.size(); i++) {
							JCheckBox jcb = (JCheckBox)((List)compList.get(i)).get(4);
							if (jcb.isSelected()) {
								iRow = i;
								iCount ++;
							}
						}
						
						// check the script
						if (iCount == 0) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "Please select one to execute!");
							return;
						} else if (iCount > 1) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "You just can select on script!");
							return;
						}
						
						// get script path and execute
						String strScriptId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
						String strScriptName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
						ScriptListDao dao = new ScriptListDao();
						String strScriptPath = dao.getScriptPath(strScriptId);
						
						String strEvidencePath = ".\\excelApl\\evidences\\scripts\\";
						strEvidencePath = strEvidencePath.concat(strScriptId);
						
						int intDataRow = grid.getDataRow(iRow);
						Map valueMap = (Map) grid.getValueList().get(intDataRow);
						
						String strEndMail = (String)valueMap.get("END_MAIL");
						String strErrorMail = (String)valueMap.get("ERROR_MAIL");
						
						Utils.addJob(strScriptId, strScriptName, strScriptPath, strEvidencePath, strEndMail, strErrorMail, strDate);
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(ScriptListDialog.this, "Create failed!");
						e1.printStackTrace();
						logger.exception(e1);
					}
				}
			});
			btnTimer.setText("Timer");
			btnTimer.setSize(new Dimension(150, 30));
			btnTimer.setLocation(new Point(10, 507));
//			btnTimer.setFont(new Font("Dialog", Font.BOLD, 10));
			btnTimer.setBounds(170, 507, 150, 30);
			jPanel.add(btnTimer);
			
			JButton btnTestExec = new JButton();
			btnTestExec.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						
						int iRow = -1;
						int iCount = 0;
						
						List compList = grid.getComponentList();
						
						for (int i = 0; i < compList.size(); i++) {
							JCheckBox jcb = (JCheckBox)((List)compList.get(i)).get(4);
							if (jcb.isSelected()) {
								iRow = i;
								iCount ++;
							}
						}
						
						// check the script
						if (iCount == 0) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "Please select one to execute!");
							return;
						} else if (iCount > 1) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "You just can select on script!");
							return;
						}
						
						// get script path and execute
						String strScriptId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
						ScriptListDao dao = new ScriptListDao();
						String strScriptPath = dao.getScriptPath(strScriptId);
						
						String strEvidencePath = ".\\excelApl\\evidences\\scripts\\";
						strEvidencePath = strEvidencePath.concat(strScriptId);
						
						int intDataRow = grid.getDataRow(iRow);
						Map valueMap = (Map) grid.getValueList().get(intDataRow);
						
						String strEndMail = (String)valueMap.get("END_MAIL");
						String strErrorMail = (String)valueMap.get("ERROR_MAIL");
						
						RunHelp help = new RunHelp();
						help.runCurrentScript(strScriptPath, strEvidencePath, true, strEndMail, strErrorMail);
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(ScriptListDialog.this, "Create failed!");
						e1.printStackTrace();
						logger.exception(e1);
					}
				}
			});
			btnTestExec.setText("Test Execute");
			btnTestExec.setSize(new Dimension(150, 30));
			btnTestExec.setLocation(new Point(10, 507));
//			btnTestExec.setFont(new Font("Dialog", Font.BOLD, 10));
			btnTestExec.setBounds(330, 507, 150, 30);
			jPanel.add(btnTestExec);
			jPanel.add(getTitleMailSetting());
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
			scriptFileInit = new JLabel();
			scriptFileInit.setBounds(new java.awt.Rectangle(367,0,120,20));
			scriptFileInit.setText("script file");
			scriptFileInit.setBackground(new Color(255, 204, 204));
			scriptNameInit = new JLabel();
			scriptNameInit.setBounds(new java.awt.Rectangle(100,0,267,20));
			scriptNameInit.setText("JLabel");
			scriptIdInit = new JLabel();
			scriptIdInit.setBounds(new java.awt.Rectangle(3,0,100,20));
			scriptIdInit.setText("JLabel");
			excelSheet = new JPanel();
			excelSheet.setBounds(new Rectangle(10, 95, 965, 300));
			excelSheet.setLayout(null);
			excelSheet.add(scriptIdInit, null);
			excelSheet.add(getExcelCheckInit(), null);
			excelSheet.add(scriptNameInit, null);
			excelSheet.add(getJButton2(), null);
			excelSheet.add(getJButton3(), null);
			excelSheet.add(scriptFileInit, null);
			excelSheet.add(getJButton4(), null);
			excelSheet.add(getBtnMailSettingInit());
		}
		return excelSheet;
	}

	/**
	 * This method initializes btnExcelCreate	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExecute() {
		if (btnExecute == null) {
			btnExecute = new JButton();
			btnExecute.setText("Execute");
			btnExecute.setSize(new Dimension(150,30));
//			btnExecute.setFont(new Font("Dialog", Font.BOLD, 10));
			btnExecute.setLocation(new java.awt.Point(10,507));
			btnExecute.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						
						int iRow = -1;
						int iCount = 0;
						
						List compList = grid.getComponentList();
						
						for (int i = 0; i < compList.size(); i++) {
							JCheckBox jcb = (JCheckBox)((List)compList.get(i)).get(4);
							if (jcb.isSelected()) {
								iRow = i;
								iCount ++;
							}
						}
						
						// check the script
						if (iCount == 0) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "Please select one to execute!");
							return;
						} else if (iCount > 1) {
							JOptionPane.showMessageDialog(ScriptListDialog.this, "You just can select on script!");
							return;
						}
						
						// get script path and execute
						String strScriptId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
						ScriptListDao dao = new ScriptListDao();
						String strScriptPath = dao.getScriptPath(strScriptId);
						
						String strEvidencePath = ".\\excelApl\\evidences\\scripts\\";
						strEvidencePath = strEvidencePath.concat(strScriptId);
						
						int intDataRow = grid.getDataRow(iRow);
						Map valueMap = (Map) grid.getValueList().get(intDataRow);
						
						String strEndMail = (String)valueMap.get("END_MAIL");
						String strErrorMail = (String)valueMap.get("ERROR_MAIL");
						
						RunHelp help = new RunHelp();
						help.runCurrentScript(strScriptPath, strEvidencePath, false, strEndMail, strErrorMail);
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(ScriptListDialog.this, "Create failed!");
						e1.printStackTrace();
						logger.exception(e1);
					}
				}
			});
		}
		return btnExecute;
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
			jButton.setLocation(new Point(840, 500));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					hide();
					SikuliIDE.showMain();
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
	 */
	private List getSelectedList(List valueList) {
		List result = new ArrayList();
		for (int i = 0; i < valueList.size(); i++) {
			Map map = (Map)valueList.get(i); 
			//
			if (map.get("SELECT").equals("true")) {
				Map newMap = new HashMap();
				newMap.put("PATTERN_ID", map.get("PATTERN_ID"));
				newMap.put("PATTERN_NAME", map.get("PATTERN_NAME"));
				newMap.put("PATTERN_TYPE", map.get("PATTERN_TYPE"));
				result.add(newMap);
			}
		}
		return result;
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
			preButton.setLocation(new java.awt.Point(10,399));
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
			afterButton.setLocation(new Point(840, 403));
			afterButton.setText("Next Page");
			afterButton.setSize(new Dimension(130, 30));
		}
		return afterButton;
	}

	/**
	 * This method initializes excelCheckInit	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getExcelCheckInit() {
		if (scriptCheckInit == null) {
			scriptCheckInit = new JCheckBox();
			scriptCheckInit.setBounds(new Rectangle(640, 0, 70, 20));
			scriptCheckInit.setHorizontalAlignment(SwingConstants.CENTER);
			scriptCheckInit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				}
			});
		}
		return scriptCheckInit;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getScriptSelect() {
		if (scriptSelect == null) {
			scriptSelect = new JButton();
			scriptSelect.setText("Search");
			scriptSelect.setLocation(new Point(286, 40));
			scriptSelect.setSize(new Dimension(110,20));
			scriptSelect.setPreferredSize(new Dimension(70, 30));
			scriptSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						searchDetailList();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(ScriptListDialog.this, "Search Failed!");
						logger.exception(e1);
					}
				}
			});
		}
		return scriptSelect;
	}

	/**
	 * This method initializes searchPatternName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchScriptName() {
		if (searchScriptName == null) {
			searchScriptName = new JTextField();
			searchScriptName.setBounds(new Rectangle(150, 40, 130, 20));
		}
		return searchScriptName;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(820, 40, 150, 20));
			jButton1.setText("Add Script");
			jButton1.setPreferredSize(new Dimension(70, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// hide self
					setVisible(false);
					Container parent = jPanel.getParent().getParent().getParent();
					// hide main
					if (SikuliIDE.getMain().isVisible()) {
						SikuliIDE.hideMain();
					}
					SikuliIDE.setParentWindow(parent, "", "", "");
					
					// show the IDE
					SikuliIDE.showIDE();
					
					searchDetailList();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(717, 0, 90, 20));
			jButton2.setText("Detail");
//			jButton2.setFont(new Font("Dialog", Font.BOLD, 10));
			jButton2.setPreferredSize(new Dimension(70, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int iRow = -1;
					
					List compList = grid.getComponentList();
					
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(5))) {
							iRow = i;
							break;
						}
					}
					
					// get script path and execute
					String strScriptId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					ScriptListDao dao = new ScriptListDao();
					String strScriptPath = dao.getScriptPath(strScriptId);
					
					// description
					String strScriptDescription = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					
					// hide self
					setVisible(false);
					Container parent = jPanel.getParent().getParent().getParent();
					// hide main
					SikuliIDE.hideMain();
					SikuliIDE.setParentWindow(parent, strScriptPath, strScriptDescription, strScriptId);
					
					// show the IDE
					SikuliIDE.showIDE();
					
					// search again
					searchDetailList();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(807, 0, 85, 20));
			jButton3.setText("Copy");
//			jButton3.setFont(new Font("Dialog", Font.BOLD, 10));
			jButton3.setPreferredSize(new Dimension(70, 30));
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iRow = -1;
					
					List compList = grid.getComponentList();
					
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(6))) {
							iRow = i;
							break;
						}
					}
					
					// get script path and execute
					String strScriptId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					ScriptListDao dao = new ScriptListDao();
					
					// description
					String strScriptDescription = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					
					int intDataRow = grid.getDataRow(iRow);
					Map valueMap = (Map) grid.getValueList().get(intDataRow);
					
					String strEndMail = (String)valueMap.get("END_MAIL");
					String strErrorMail = (String)valueMap.get("ERROR_MAIL");
					
					try {
						ScriptUtils.copyById(strScriptId, strScriptDescription, strEndMail, strErrorMail);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(ScriptListDialog.this, "Error!!");
						logger.exception(e1);
					}
					
					// search again
					searchDetailList();
				}
			});
		}
		return jButton3;
	}
	
	/**
	 *
	 */
	public void searchDetailList() {
		ScriptListDao dao = new ScriptListDao();
		String strSearchTxt = searchScriptName.getText().trim();
		List<Map<String, String>> list = dao.searchList(strSearchTxt);
		//
		grid.setData(list);
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(892, 0, 70, 20));
			jButton4.setPreferredSize(new Dimension(70, 30));
			jButton4.setText("Del");
//			jButton4.setFont(new Font("Dialog", Font.BOLD, 10));
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iResult = JOptionPane.showConfirmDialog(ScriptListDialog.this, "Do you want to delete?");
					if (iResult != JOptionPane.YES_OPTION) {
						return;
					}
					int iRow = 0;
					List compList = grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(7))) {
							iRow = i;
						}
					}
					String strScriptId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					
					ScriptListDao dao = new ScriptListDao();
					dao.deleteByList(strScriptId);
			        searchDetailList();
				}
			});
		}
		return jButton4;
	}
	private JLabel getTitleMailSetting() {
		if (titleMailSetting == null) {
			titleMailSetting = new JLabel();
			titleMailSetting.setText("Mail Setting");
			titleMailSetting.setHorizontalAlignment(SwingConstants.CENTER);
			titleMailSetting.setBounds(new Rectangle(380, 75, 120, 20));
			titleMailSetting.setBackground(new Color(255, 204, 204));
			titleMailSetting.setBounds(500, 73, 150, 20);
		}
		return titleMailSetting;
	}
	private JButton getBtnMailSettingInit() {
		if (btnMailSettingInit == null) {
			btnMailSettingInit = new JButton();
			btnMailSettingInit.addActionListener(new ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(ActionEvent e) {
					
					int iRow = -1;
					
					List compList = grid.getComponentList();
					
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(3))) {
							iRow = i;
							break;
						}
					}
					
					String strScriptId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					String strScriptName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					
					int intDataRow = grid.getDataRow(iRow);
					Map valueMap = (Map) grid.getValueList().get(intDataRow);
					
					String strEndMail = (String)valueMap.get("END_MAIL");
					String strErrorMail = (String)valueMap.get("ERROR_MAIL");
					
					MailListDialog dialog = new MailListDialog(strEndMail, strErrorMail, strScriptId, strScriptName, true);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
					
					int iPage = grid.getPageNo();
					
					searchDetailList();
					
					grid.setPageNo(iPage);
				}
			});
			btnMailSettingInit.setText("Mail Set");
			btnMailSettingInit.setPreferredSize(new Dimension(70, 30));
//			btnMailSettingInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnMailSettingInit.setBounds(new Rectangle(657, 0, 70, 20));
			btnMailSettingInit.setBounds(503, 0, 140, 20);
		}
		return btnMailSettingInit;
	}
}  //  @jve:decl-index=0:visual-constraint="6,6"
