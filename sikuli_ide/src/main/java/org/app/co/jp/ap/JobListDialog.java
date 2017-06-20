package org.app.co.jp.ap;

import org.app.co.jp.com.ScheduleCommon;
import org.app.co.jp.dao.JobListDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.GridUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JobListDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel detailPanel = null;
	private JButton jButton = null;
	
	private File initExcelFile;
	private JButton preButton = null;
	private JButton afterButton = null;
	private JLabel lblJobSelect;
	private JButton btnJobSelect = null;
	GridUtils grid = null;
	private JLabel titleScriptId;
	private JLabel titleScriptName;
	private JLabel pageInfoLbl = null;
	
	BasicLogger logger = BasicLogger.getLogger();
	private JTextField searchJobName = null;
	private JLabel titleDeal = null;
	
	private JLabel scriptIdInit;
	private JLabel scriptNameInit;
	private JButton btnCancel = null;
	private JLabel titleJobTime;
	private JLabel timeJobStatus;
	private JLabel timeInit;
	private JLabel statusInit;
	
	/**
	 * This method initializes 
	 * 
	 */
	public JobListDialog() {
		super();
		
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
        this.setSize(new Dimension(800,600));
        this.setContentPane(getJPanel());

        List<String> title = new ArrayList<String>();
        title.add("Script ID");
        title.add("Script Name");
        title.add("Time");
        title.add("Status");
		title.add("Operation");

        List<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(scriptIdInit);
        componentList.add(scriptNameInit);
        componentList.add(timeInit);
        componentList.add(statusInit);
        componentList.add(btnCancel);
        String []arrColumn = {"SCRIPT_ID", "SCRIPT_NAME", "SCRIPT_DATE", "STATUS_TEXT", "DEAL_1"};
        String []arrTitle = {"SCRIPT_ID", "SCRIPT_NAME", "SCRIPT_DATE", "STATUS_TEXT", "DEAL_1"};
        grid = new GridUtils(detailPanel, title, componentList, arrColumn, preButton, afterButton, 15, arrTitle);
        grid.setPageInfo(pageInfoLbl);
        
        searchDetailList();
        
        setTitle("Job List Page");
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			titleDeal = new JLabel();
			titleDeal.setBounds(new Rectangle(690, 75, 85, 20));
			titleDeal.setHorizontalAlignment(SwingConstants.CENTER);
			titleDeal.setText("Operation");
			titleDeal.setBackground(new Color(255, 204, 204));
			pageInfoLbl = new JLabel();
			pageInfoLbl.setBounds(new java.awt.Rectangle(224,402,315,30));
			pageInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
			pageInfoLbl.setText("JLabel");
			titleScriptName = new JLabel();
			titleScriptName.setBounds(new Rectangle(110, 75, 260, 20));
			titleScriptName.setHorizontalAlignment(SwingConstants.CENTER);
			titleScriptName.setBackground(new Color(255,204,204));
			titleScriptName.setText("Script Name");
			titleScriptId = new JLabel();
			titleScriptId.setBounds(new java.awt.Rectangle(11,75,100,22));
			titleScriptId.setHorizontalAlignment(SwingConstants.CENTER);
			titleScriptId.setText("Script Id");
			lblJobSelect = new JLabel();
			lblJobSelect.setBounds(new Rectangle(10, 40, 100, 20));
			lblJobSelect.setText("Job Name");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 10, 500, 24));
			jLabel.setText("watch the job in schedule");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getDetailPanel(), null);
			jPanel.add(getPreButton(), null);
			jPanel.add(getAfterButton(), null);
			jPanel.add(lblJobSelect, null);
			jPanel.add(getBtnJobSelect(), null);
			jPanel.add(titleScriptId, null);
			jPanel.add(titleScriptName, null);
			jPanel.add(pageInfoLbl, null);
			jPanel.add(getSearchJobName(), null);
			jPanel.add(titleDeal, null);
			jPanel.add(getTitleJobTime());
			jPanel.add(getTimeJobStatus());
		}
		return jPanel;
	}

	/**
	 * This method initializes excelSheet	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getDetailPanel() {
		if (detailPanel == null) {
			scriptNameInit = new JLabel();
			scriptNameInit.setBounds(new Rectangle(100, 0, 260, 20));
			scriptNameInit.setText("JLabel");
			scriptIdInit = new JLabel();
			scriptIdInit.setHorizontalAlignment(SwingConstants.CENTER);
			scriptIdInit.setBounds(new java.awt.Rectangle(3,0,100,20));
			scriptIdInit.setText("JLabel");
			detailPanel = new JPanel();
			detailPanel.setBounds(new java.awt.Rectangle(10,95,770,300));
			detailPanel.setLayout(null);
			detailPanel.add(scriptIdInit, null);
			detailPanel.add(scriptNameInit, null);
			detailPanel.add(getBtnCancel(), null);
			detailPanel.add(getTimeInit());
			detailPanel.add(getStatusInit());
		}
		return detailPanel;
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
			jButton.setLocation(new java.awt.Point(690,500));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
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
			preButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setControl();
				}
			});
			preButton.setText("Prev Page");
			preButton.setSize(new Dimension(90,30));
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
			afterButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setControl();
				}
			});
			afterButton.setLocation(new java.awt.Point(690,403));
			afterButton.setText("Next Page");
			afterButton.setSize(new Dimension(90,30));
		}
		return afterButton;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnJobSelect() {
		if (btnJobSelect == null) {
			btnJobSelect = new JButton();
			btnJobSelect.setText("Search");
			btnJobSelect.setLocation(new Point(260, 40));
			btnJobSelect.setSize(new Dimension(110,20));
			btnJobSelect.setPreferredSize(new Dimension(70, 30));
			btnJobSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						searchDetailList();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(JobListDialog.this, "Search Failed!");
						logger.exception(e1);
					}
				}
			});
		}
		return btnJobSelect;
	}

	/**
	 * This method initializes searchPatternName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchJobName() {
		if (searchJobName == null) {
			searchJobName = new JTextField();
			searchJobName.setBounds(new Rectangle(120, 40, 130, 20));
		}
		return searchJobName;
	}
	
	/**
	 *
	 */
	public void searchDetailList() {
		JobListDao dao = new JobListDao();
		String strSearchTxt = searchJobName.getText().trim();
		List<Map<String, String>> list = dao.searchList(strSearchTxt);
		//
		grid.setData(list);

		setControl();
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(687, 0, 80, 20));
			btnCancel.setPreferredSize(new Dimension(70, 30));
			btnCancel.setText("Cancel");
			btnCancel.setFont(new Font("Dialog", Font.BOLD, 10));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				@SuppressWarnings("rawtypes")
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int iResult = JOptionPane.showConfirmDialog(JobListDialog.this, "Do you want to Cancel?");
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
					
					int iDataRow = grid.getDataRow(iRow);
					
					List record = grid.getValueList();
					Map dataRecord = (Map)record.get(iDataRow);
					
					String strJobId = (String)dataRecord.get("ID");
					
					try {
						ScheduleCommon.removeJobById(strJobId, "2");
					} catch (Exception e1) {
						logger.exception(e1);
					}
					
					int nowPage = grid.getPageNo();
			        searchDetailList();
			        grid.setPageNo(nowPage);
			        
			        setControl();
				}
			});
		}
		return btnCancel;
	}
	private JLabel getTitleJobTime() {
		if (titleJobTime == null) {
			titleJobTime = new JLabel();
			titleJobTime.setText("Time");
			titleJobTime.setHorizontalAlignment(SwingConstants.CENTER);
			titleJobTime.setBounds(new Rectangle(11, 75, 100, 22));
			titleJobTime.setBounds(370, 75, 180, 22);
		}
		return titleJobTime;
	}
	private JLabel getTimeJobStatus() {
		if (timeJobStatus == null) {
			timeJobStatus = new JLabel();
			timeJobStatus.setText("Status");
			timeJobStatus.setHorizontalAlignment(SwingConstants.CENTER);
			timeJobStatus.setBounds(new Rectangle(11, 75, 100, 22));
			timeJobStatus.setBounds(550, 75, 140, 22);
		}
		return timeJobStatus;
	}
	private JLabel getTimeInit() {
		if (timeInit == null) {
			timeInit = new JLabel();
			timeInit.setText("JLabel");
			timeInit.setBounds(new Rectangle(100, 0, 260, 20));
			timeInit.setBounds(360, 0, 180, 20);
		}
		return timeInit;
	}
	private JLabel getStatusInit() {
		if (statusInit == null) {
			statusInit = new JLabel();
			statusInit.setText("JLabel");
			statusInit.setBounds(new Rectangle(100, 0, 260, 20));
			statusInit.setBounds(540, 0, 140, 20);
		}
		return statusInit;
	}
	
	@SuppressWarnings("rawtypes")
	private void setControl() {
		
		List valuelist = grid.getValueList();
		
		//
		List component = grid.getComponentList();
		for (int i = 0; i <= component.size() - 1; i++) {
			List componentRecord = (List)component.get(i);
			
			int iDataRow = grid.getDataRow(i);
			Map map = (Map)valuelist.get(iDataRow);
			
			// status 
			String strStatus = (String)map.get("STATUS");
			
			if (!strStatus.equals("0")) {
				JComponent jcp = (JComponent)componentRecord.get(4);
				jcp.setEnabled(false);
			}
		}
	}
}  //  @jve:decl-index=0:visual-constraint="6,6"
