package org.app.co.jp.ap;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Utils;
import org.app.co.jp.util.bean.FileSelect;
import org.sikuli.ide.SikuliIDE;
import org.sikuli.script.Sikulix;

public class MainApplet extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel = null;
	private JButton btnClose = null;
	private JLabel lblMessageLabel1 = null;
	private JButton btnExcelSelect = null;
	private JLabel labelExcelSelect = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JButton btnTableInfoSet = null;
	private JLabel labelTableInfoSet = null;
	private JLabel jLabel3 = null;
	
    BasicLogger logger = BasicLogger.getLogger();
	private JButton btnPatternAdd = null;
	private JLabel labelPatternAdd = null;
	private JLabel jLabel2 = null;
    private JButton btnParamSet = null;
	private JLabel labelParamSet = null;
    private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;

	private JButton btnPageList = null;
	private JButton btnScenarioList;
	private JButton btnMailList;
	private JButton btnJobList;
	private JLabel labelPageList = null;
	private JLabel labelScenarioList = null;
	private JLabel labelMailList = null;
	private JLabel labelJobList = null;
	
	private JLabel labelScriptList = null;
	private JLabel labelOperationList  = null;
	/**
	 * This method initializes 
	 * 
	 */
	public MainApplet() {
		super();
		initFont (new javax.swing.plaf.FontUIResource(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
		initialize();
	}
	
	public void initFont(javax.swing.plaf.FontUIResource f) {
		UIManager.put("Button.font", f);
		UIManager.put("ToggleButton.font", f);
		UIManager.put("RadioButton.font", f);
		UIManager.put("CheckBox.font", f);
		UIManager.put("ColorChooser.font", f);
		UIManager.put("ComboBox.font", f);
		UIManager.put("Label.font", f);
		UIManager.put("List.font", f);
		UIManager.put("MenuBar.font", f);
		UIManager.put("MenuItem.font", f);
		UIManager.put("RadioButtonMenuItem.font", f);
		UIManager.put("CheckBoxMenuItem.font", f);
		UIManager.put("Menu.font", f);
		UIManager.put("PopupMenu.font", f);
		UIManager.put("OptionPane.font", f);
		UIManager.put("Panel.font", f);
		UIManager.put("ProgressBar.font", f);
		UIManager.put("ScrollPane.font", f);
		UIManager.put("Viewport.font", f);
		UIManager.put("TabbedPane.font", f);
		UIManager.put("Table.font", f);
		UIManager.put("TableHeader.font", f);
		UIManager.put("TextField.font", f);
		UIManager.put("PasswordField.font", f);
		UIManager.put("TextArea.font", f);
		UIManager.put("TextPane.font", f);
		UIManager.put("EditorPane.font", f);
		UIManager.put("TitledBorder.font", f);
		UIManager.put("ToolBar.font", f);
		UIManager.put("ToolTip.font", f);
		UIManager.put("Tree.font", f);
	}

	/**
	 * This method initializes this
	 * 
	 */
	public void initialize() {
		this.setBounds(0, 0, 600, 600);
        this.setContentPane(getMainPanel());

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Sikulix.cleanUp(0);
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes mainPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(228, 517, 346, 33));
			jLabel5.setVerticalAlignment(SwingConstants.TOP);
			jLabel5.setVerticalTextPosition(SwingConstants.TOP);
			jLabel5.setText("shawn.shaohua.wang@accenture.com");
//			jLabel4 = new JLabel();
//			jLabel4.setBounds(new Rectangle(165, 190, 587, 20));
//			jLabel4.setText("Common column that will be covered in scripts.");
//			jLabel2 = new JLabel();
//			jLabel2.setBounds(new Rectangle(165, 140, 587, 20));
//			jLabel2.setText("Patten which can create data automatically");
//			jLabel3 = new JLabel();
//			jLabel3.setBounds(new Rectangle(165, 540, 396, 16));
//			jLabel3.setText("Import table's definitions");
//			jLabel1 = new JLabel();
//			jLabel1.setBounds(new java.awt.Rectangle(165,90,587,20));
//			jLabel1.setVerticalAlignment(SwingConstants.TOP);
//			jLabel1.setVerticalTextPosition(SwingConstants.TOP);
//			jLabel1.setText("Create scripts(create or update) with excel sheets in the test document.");
//			jLabel = new JLabel();
//			jLabel.setVerticalAlignment(SwingConstants.TOP);
//			jLabel.setVerticalTextPosition(SwingConstants.TOP);
//			jLabel.setLocation(new Point(37, 600));
//			jLabel.setSize(new java.awt.Dimension(430,20));
//			jLabel.setText("If you have any advice, please contract me with:");
			lblMessageLabel1 = new JLabel();
			lblMessageLabel1.setVerticalAlignment(SwingConstants.TOP);
			lblMessageLabel1.setVerticalTextPosition(SwingConstants.TOP);
			lblMessageLabel1.setLocation(new java.awt.Point(39,25));
			lblMessageLabel1.setSize(new java.awt.Dimension(430,20));
			lblMessageLabel1.setText("Version 1.0.0.0");
			mainPanel = new JPanel();
			mainPanel.setLayout(null);
			mainPanel.setSize(800, 600);
//			mainPanel.add(getBtnClose(), null);
			mainPanel.add(lblMessageLabel1, null);
			mainPanel.add(getBtnExcelSelect(), null);
			// add chunhui.li
			mainPanel.add(getLabelExcelSelect(), null);
			// add chunhui.li
//			mainPanel.add(jLabel, null);
//			mainPanel.add(jLabel1, null);
			mainPanel.add(getBtnTableInfoSet(), null);
			mainPanel.add(getLabelTableInfoSet(), null);
//			mainPanel.add(jLabel3, null);
			mainPanel.add(getJButton(), null);
			// add chunhui.li
			mainPanel.add(getLabelPatternAdd(), null);
			// add chunhui.li
//			mainPanel.add(jLabel2, null);
			mainPanel.add(getJButton2(), null);
			// add chunhui.li
			mainPanel.add(getLabelParamSet(), null);
			// add chunhui.li
//			mainPanel.add(jLabel4, null);
			mainPanel.add(jLabel5, null);

			mainPanel.add(getJButton3(), null);
			// add chunhui.li
			mainPanel.add(getLabelPageList(), null);
			// add chunhui.li
			
			JButton btnScriptList = createBtn("Script List", "/icons/sl.png");
			btnScriptList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ScriptListDialog scriptDialog = new ScriptListDialog();
					scriptDialog.setModal(true);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					scriptDialog.setLocationRelativeTo(null);
					scriptDialog.show();
				}
			});
			btnScriptList.setText("Script List");
			btnScriptList.setBounds(new Rectangle(40, 200, 100, 110));
			btnScriptList.setBounds(40, 200, 100, 110);
			mainPanel.add(btnScriptList);
			mainPanel.add(getLabelScriptList());
			mainPanel.add(getBtnScenarioList());
			mainPanel.add(getLabelScenarioList());
			
			JButton btnOperationList = createBtn("Operation List", "/icons/ol.png");//new JButton();
			btnOperationList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Utils.addWindow(SikuliIDE.getMain());
					OperationListDialog operationListDialog = new OperationListDialog();
					Utils.addWindow(operationListDialog);
					SikuliIDE.getMain().setVisible(false);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					operationListDialog.setLocationRelativeTo(null);
					operationListDialog.setVisible(true);
				}
			});
			btnOperationList.setText("Operation List");
//			btnOperationList.setBounds(new Rectangle(40, 340, 110, 30));
			btnOperationList.setBounds(40, 350, 100, 110);
			mainPanel.add(btnOperationList);
			mainPanel.add(getLabelOperationList());
			mainPanel.add(getBtnMailList());
			mainPanel.add(getLabelMailList());
			mainPanel.add(getBtnJobList());
			mainPanel.add(getLabelJobList());

	        // --------------------------------------------------------
	        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/29
	        // -------------------------------------------------------
	        setTitle("Menu");
		}
		return mainPanel;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLabelScriptList() {
		if (labelScriptList == null) {
			labelScriptList = new JLabel();
//			labelScriptList.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelScriptList.setBounds(new Rectangle(40, 265, 130, 110));
			labelScriptList.setText("Script List");
		}
		return labelScriptList;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLabelOperationList() {
		if (labelOperationList == null) {
			labelOperationList = new JLabel();
//			labelOperationList.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelOperationList.setBounds(new Rectangle(20, 415, 130, 110));
			labelOperationList.setText("Operation List");
		}
		return labelOperationList;
	}


	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setPreferredSize(new java.awt.Dimension(70,30));
			btnClose.setLocation(new Point(660, 600));
			btnClose.setSize(new java.awt.Dimension(110,30));
			btnClose.setText("Close");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExcelSelect() {
		if (btnExcelSelect == null) {
			btnExcelSelect = createBtn("Create Scripts", "/icons/cs.png");
			btnExcelSelect.setPreferredSize(new java.awt.Dimension(70,30));
			btnExcelSelect.setLocation(new java.awt.Point(40, 50));
			btnExcelSelect.setSize(new java.awt.Dimension( 100, 110));
			btnExcelSelect.setText("Create Scripts");
		    
			btnExcelSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					actionExcelSelectPerformed();
				}
			});
		}
		return btnExcelSelect;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLabelExcelSelect() {
		if (labelExcelSelect == null) {
			labelExcelSelect = new JLabel();
//			labelExcelSelect.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelExcelSelect.setBounds(new Rectangle(20, 115, 130, 110));
			labelExcelSelect.setText("Create Scripts");
		}
		return labelExcelSelect;
	}

	/**
	 *
	 */
	private void actionExcelSelectPerformed() {
		try {
			FileSelect jfc = new FileSelect();
			jfc.setVisible(true);
			int returnVal = jfc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (jfc.getSelectedFile().getName().indexOf(".xls") < 0) {
					JOptionPane.showMessageDialog(this, "Please select EXCEL files!");
					return;
				}				
				PrimaryExcelDialog excelDialog = new PrimaryExcelDialog(new File(jfc.getSelectedFile().getAbsolutePath()));
				excelDialog.setModal(true);
				excelDialog.show();
			}
		} catch (Exception e) {
			//
			logger.exception(e);
		}
	}

	/**
	 * This method initializes btnTableInfoSet	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnTableInfoSet() {
		if (btnTableInfoSet == null) {
			btnTableInfoSet = createBtn("tables info", "/icons/ti.png");
			btnTableInfoSet.setBounds(new Rectangle(460, 200, 100, 110));
			btnTableInfoSet.setText("tables info");
			btnTableInfoSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						ConnectionSetDialog excelDialog = new ConnectionSetDialog();
						excelDialog.setModal(true);
				        // --------------------------------------------------------
				        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
				        // -------------------------------------------------------
						excelDialog.setLocationRelativeTo(null);
						excelDialog.show();
					} catch (Exception e1) {
						//
						logger.exception(e1);
					}
				}
			});
		}
		return btnTableInfoSet;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLabelTableInfoSet() {
		if (labelTableInfoSet == null) {
			labelTableInfoSet = new JLabel();
//			labelTableInfoSet.SETFONT(NEW FONT(COMMONCONSTANT.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelTableInfoSet.setBounds(new Rectangle(460, 265, 130, 110));
			labelTableInfoSet.setText("tables info");
		}
		return labelTableInfoSet;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (btnPatternAdd == null) {
			btnPatternAdd = createBtn("Batch Pattern", "/icons/bp.png");
			btnPatternAdd.setBounds(new Rectangle(180, 50, 100, 110));
			btnPatternAdd.setText("Batch Pattern");
			btnPatternAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PatternListDialog patternDialog = new PatternListDialog();
					patternDialog.setModal(true);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					patternDialog.setLocationRelativeTo(null);
					patternDialog.show();
				}
			});
		}
		return btnPatternAdd;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLabelPatternAdd() {
		if (labelPatternAdd == null) {
			labelPatternAdd = new JLabel();
//			labelPatternAdd.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelPatternAdd.setBounds(new Rectangle(160, 115, 130, 110));
			labelPatternAdd.setText("Batch Pattern");
		}
		return labelPatternAdd;
	}

    /**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (btnParamSet == null) {
			btnParamSet = createBtn("Common cols", "/icons/ccs.png");
			btnParamSet.setBounds(new Rectangle(320, 50, 100, 110));
			btnParamSet.setText("Common cols");
			btnParamSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ParameterDialog parameterDialog = new ParameterDialog();
					parameterDialog.setModal(true);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					parameterDialog.setLocationRelativeTo(null);
					parameterDialog.show();				
				}
			});
		}
		return btnParamSet;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLabelParamSet() {
		if (labelParamSet == null) {
			labelParamSet = new JLabel();
//			labelParamSet.SETFONT(NEW FONT(COMMONCONSTANT.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelParamSet.setBounds(new Rectangle(300, 115, 130, 110));
			labelParamSet.setText("Common cols");
		}
		return labelParamSet;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnPageList == null) {
			btnPageList = createBtn("Page List", "/icons/pl.png");
			btnPageList.setBounds(new Rectangle(180, 200, 100, 110));
			btnPageList.setText("Page List");
			btnPageList.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Utils.addWindow(SikuliIDE.getMain());
					PageListDialog pageListDialog = new PageListDialog();
					Utils.addWindow(pageListDialog);
					SikuliIDE.getMain().setVisible(false);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					pageListDialog.setLocationRelativeTo(null);
					pageListDialog.show();
					}
			});
		}
		return btnPageList;
	}

	/**
	 * This method initializes btnExcelSelect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JLabel getLabelPageList() {
		if (labelPageList == null) {
			labelPageList = new JLabel();
//			labelPageList.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelPageList.setBounds(new Rectangle(180, 265,  100, 110));
			labelPageList.setText("Page List");
		}
		return labelPageList;
	}
	
	private JButton getBtnScenarioList() {
		if (btnScenarioList == null) {
			btnScenarioList = createBtn("Scenario List", "/icons/snl.png");
			btnScenarioList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Utils.addWindow(SikuliIDE.getMain());
					ScenarioListDialog scenarioListDialog = new ScenarioListDialog();
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					scenarioListDialog.setLocationRelativeTo(null);
					Utils.addWindow(scenarioListDialog);
					SikuliIDE.getMain().setVisible(false);
					scenarioListDialog.show();
				}
			});
			btnScenarioList.setText("Scenario List");
			btnScenarioList.setBounds(new Rectangle(320, 200, 100, 110));
		}
		return btnScenarioList;
	}
	private JLabel getLabelScenarioList() {
		if (labelScenarioList == null) {
			labelScenarioList = new JLabel();
//			labelScenarioList.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelScenarioList.setBounds(new Rectangle(300, 265, 130, 110));
			labelScenarioList.setText("Scenario List");
		}
		return labelScenarioList;
	}
	
	
	private JButton getBtnMailList() {
		if (btnMailList == null) {
			btnMailList = createBtn("Scenario List", "/icons/m.png");
			btnMailList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MailListDialog dialog = new MailListDialog("", "", "", "", false);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				}
			});
			btnMailList.setText("Mail List");
			btnMailList.setBounds(new Rectangle(180, 350, 100, 110));
//			btnMailList.setBounds(40, 440, 110, 30);
		}
		return btnMailList;
	}
	private JLabel getLabelMailList() {
		if (labelMailList == null) {
			labelMailList = new JLabel();
//			labelMailList.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelMailList.setBounds(new Rectangle(180, 415,  100, 110));
			labelMailList.setText("Mail List");
		}
		return labelMailList;
	}
	private JButton getBtnJobList() {
		if (btnJobList == null) {
			btnJobList = createBtn("Job List", "/icons/jb.png");
			btnJobList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JobListDialog dialog = new JobListDialog();
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					dialog.setModal(true);
					dialog.setVisible(true);
				}
			});
			btnJobList.setText("Job List");
			btnJobList.setBounds(new Rectangle(460, 50, 100, 110));
		}
		return btnJobList;
	}
	private JLabel getLabelJobList() {
		if (labelJobList == null) {
			labelJobList = new JLabel();
//			labelJobList.setFont(new Font(CommonConstant.FONT, CommonConstant.FONT_TYPE, CommonConstant.FONT_SIZE));
			labelJobList.setBounds(new Rectangle(460, 115,  100, 110));
			labelJobList.setText("Job List");
		}
		return labelJobList;
	}

	private JButton createBtn(String text, String icon) {  
        URL imageURL = SikuliIDE.class.getResource(icon);
		ImageIcon imaIcon = new ImageIcon(imageURL);
		imaIcon.setImage(imaIcon.getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT));
        JButton btn = new JButton(imaIcon);  
        btn.setUI(new BasicButtonUI());// 恢复基本视觉效果  
        btn.setContentAreaFilled(false);// 设置按钮透明  
        btn.setMargin(new Insets(0, 0, 0, 0));// 按钮内容与边框距离  
        return btn;  
    }  
}  //  @jve:decl-index=0:visual-constraint="10,10"
