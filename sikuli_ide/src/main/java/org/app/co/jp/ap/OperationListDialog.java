package org.app.co.jp.ap;

import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.dao.OperationListDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.GridUtils;
import org.app.co.jp.util.Utils;
import org.sikuli.ide.SikuliIDE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationListDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel excelSheet = null;
	private JButton jButton = null;
	
	private File initExcelFile;
	private JButton preButton = null;
	private JButton afterButton = null;
	private JLabel lblOperationSelect;
	private JButton operationSelect = null;
	GridUtils grid = null;
	private JLabel titleOperation;
	private JLabel titleOperationName;
	private JLabel pageInfoLbl = null;
	
	BasicLogger logger = BasicLogger.getLogger();
	private JTextField searchOperationName = null;
	private JButton jButton1 = null;
	private JLabel titleDeal = null;
	
	private JLabel operationIdInit;
	private JLabel operationNameInit;
	private JButton btnDetailInit = null;
	private JButton btnCopyInit = null;
	private JButton btnDelInit = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public OperationListDialog() {
		super();
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				Utils.clearWindow();
				SikuliIDE.getMain().setVisible(true);
			}
		});
		
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
        this.setSize(new Dimension(850, 600));
        this.setContentPane(getJPanel());

        List<String> title = new ArrayList<String>();
        title.add("Pattern ID");
        title.add("Pattern Name");
        title.add("Detail");
        title.add("Copy");
		title.add("Del");

        List<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(operationIdInit);
        componentList.add(operationNameInit);
        componentList.add(btnDetailInit);
        componentList.add(btnCopyInit);
        componentList.add(btnDelInit);
        String []arrColumn = {"OPERATION_ID", "OPERATION_NAME", "DEAL_1", "DEAL_2", "DEAL_3"};
        String []arrTitle = {"OPERATION_ID", "OPERATION_NAME", "DEAL_1"};
        grid = new GridUtils(excelSheet, title, componentList, arrColumn, preButton, afterButton, 15, arrTitle);
        grid.setPageInfo(pageInfoLbl);
        
        searchDetailList();
        
        setTitle("Operation List Page");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			titleDeal = new JLabel();
			titleDeal.setBounds(new Rectangle(570, 73, 210, 20));
			titleDeal.setHorizontalAlignment(SwingConstants.CENTER);
			titleDeal.setText("Operation");
			titleDeal.setBackground(new Color(255, 204, 204));
			pageInfoLbl = new JLabel();
			pageInfoLbl.setBounds(new Rectangle(224, 450, 315, 30));
			pageInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
			pageInfoLbl.setText("JLabel");
			titleOperationName = new JLabel();
			titleOperationName.setBounds(new Rectangle(130, 73, 440, 20));
			titleOperationName.setHorizontalAlignment(SwingConstants.CENTER);
			titleOperationName.setBackground(new Color(255,204,204));
			titleOperationName.setText("Operation Name");
			titleOperation = new JLabel();
			titleOperation.setBounds(new Rectangle(11, 73, 120, 22));
			titleOperation.setHorizontalAlignment(SwingConstants.CENTER);
			titleOperation.setText("Operation ID");
			lblOperationSelect = new JLabel();
			lblOperationSelect.setBounds(new Rectangle(10, 40, 150, 20));
			lblOperationSelect.setText("Operation Name");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(10,10,205,24));
			jLabel.setText("View the defined Operation ");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getExcelSheet(), null);
			jPanel.add(getPreButton(), null);
			jPanel.add(getAfterButton(), null);
			jPanel.add(lblOperationSelect, null);
			jPanel.add(getOperationSelect(), null);
			jPanel.add(titleOperation, null);
			jPanel.add(titleOperationName, null);
			jPanel.add(pageInfoLbl, null);
			jPanel.add(getSearchOperationName(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(titleDeal, null);
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
			operationNameInit = new JLabel();
			operationNameInit.setBounds(new Rectangle(120, 0, 437, 20));
			operationNameInit.setText("JLabel");
			operationIdInit = new JLabel();
			operationIdInit.setHorizontalAlignment(SwingConstants.CENTER);
			operationIdInit.setBounds(new Rectangle(3, 0, 120, 20));
			operationIdInit.setText("JLabel");
			excelSheet = new JPanel();
			excelSheet.setBounds(new Rectangle(10, 95, 820, 350));
			excelSheet.setLayout(null);
			excelSheet.add(operationIdInit, null);
			excelSheet.add(operationNameInit, null);
			excelSheet.add(getBtnDetailInit(), null);
			excelSheet.add(getBtnCopyInit(), null);
			excelSheet.add(getBtnDelInit(), null);
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
			jButton.setLocation(new Point(710, 500));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					Utils.clearWindow();
					SikuliIDE.getMain().setVisible(true);
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
			preButton.setSize(new Dimension(120, 30));
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
			afterButton.setSize(new Dimension(120, 30));
		}
		return afterButton;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOperationSelect() {
		if (operationSelect == null) {
			operationSelect = new JButton();
			operationSelect.setText("Search");
			operationSelect.setLocation(new Point(296, 40));
			operationSelect.setSize(new Dimension(110,20));
			operationSelect.setPreferredSize(new Dimension(70, 30));
			operationSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						searchDetailList();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(OperationListDialog.this, "Search Failed!");
						logger.exception(e1);
					}
				}
			});
		}
		return operationSelect;
	}

	/**
	 * This method initializes searchPatternName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchOperationName() {
		if (searchOperationName == null) {
			searchOperationName = new JTextField();
			searchOperationName.setBounds(new Rectangle(160, 40, 130, 20));
		}
		return searchOperationName;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(660, 40, 170, 20));
			jButton1.setText("Add Operation");
			jButton1.setPreferredSize(new Dimension(70, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OperationDialog dialog = new OperationDialog(OperationListDialog.this, "", "", CommonConstant.MODE_NEW);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					setVisible(false);
					Utils.addWindow(dialog);
					dialog.setVisible(true);;
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
						if (e.getSource().equals(((List)compList.get(i)).get(2))) {
							iRow = i;
						}
					}
					String strOperationId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					String strOperationName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					OperationDialog dialog = new OperationDialog(OperationListDialog.this, strOperationId, strOperationName, CommonConstant.MODE_UPDATE);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
					setVisible(false);
					Utils.addWindow(dialog);
					dialog.setVisible(true);
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
			btnCopyInit.setBounds(new Rectangle(655, 0, 90, 20));
			btnCopyInit.setText("Copy");
//			btnCopyInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnCopyInit.setPreferredSize(new Dimension(70, 30));
			btnCopyInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iRow = 0;
					List compList = grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(3))) {
							iRow = i;
						}
					}
					String strOperationId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					String strOperationName = ((JLabel)((List)compList.get(iRow)).get(1)).getText();
					OperationDialog dialog = new OperationDialog(OperationListDialog.this, strOperationId, strOperationName, CommonConstant.MODE_COPY);
			        // --------------------------------------------------------
			        // 弹出窗口居中表示追加 add by chunhui.li 2017/06/27
			        // -------------------------------------------------------
					dialog.setLocationRelativeTo(null);
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
	public void searchDetailList() {
		OperationListDao dao = new OperationListDao();
		String strSearchTxt = searchOperationName.getText().trim();
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
			btnDelInit.setBounds(new Rectangle(747, 0, 70, 20));
			btnDelInit.setPreferredSize(new Dimension(70, 30));
			btnDelInit.setText("Del");
//			btnDelInit.setFont(new Font("Dialog", Font.BOLD, 10));
			btnDelInit.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iResult = JOptionPane.showConfirmDialog(OperationListDialog.this, "Do you want to delete?");
					if (iResult != JOptionPane.YES_OPTION) {
						return;
					}
					int iRow = 0;
					List compList = grid.getComponentList();
					for (int i = 0; i < compList.size(); i++) {
						if (e.getSource().equals(((List)compList.get(i)).get(4))) {
							iRow = i;
						}
					}
					String strOperationId = ((JLabel)((List)compList.get(iRow)).get(0)).getText();
					OperationListDao dao = new OperationListDao();
					dao.deleteByList(strOperationId);
			        searchDetailList();
				}
			});
		}
		return btnDelInit;
	}
}  //  @jve:decl-index=0:visual-constraint="6,6"
