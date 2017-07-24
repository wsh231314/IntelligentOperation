package org.app.co.jp.ap;

import org.app.co.jp.cmp.CustomerImageButton;
import org.app.co.jp.com.ComDao;
import org.app.co.jp.com.CommonConstant;
import org.app.co.jp.dao.MailDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;

public class MailDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JButton btnConfirm = null;
	private JButton jButton = null;
	
	private File initExcelFile;
	private JLabel lblSubject;
	private JLabel pageInfoLbl = null;
	CustomerImageButton imageButton  = null;
	
	BasicLogger logger = BasicLogger.getLogger();
	private JTextField txtSubject = null;
	
	private String strMailId = "";
	private String strMode = "";
	private String strMailName = "";
	
	private JLabel lblTo;
	private JLabel lblCc;
	private JLabel lblBcc;
	private JLabel lblContent;
	private JTextField txtTo;
	private JTextField txtCC;
	private JTextField txtBCC;
	private JTextArea txtContent;
	private JLabel lblAttachment;
	private JCheckBox chkAttachment;
	private JLabel lblMailId;
	private JLabel lblMailName;
	private JTextField txtMailId;
	private JTextField txtMailName;
	
	/**
	 * This method initializes 
	 * 
	 */
	public MailDialog(String strMailId, String strMailName, String strMode) {
		super();
		try {
			this.strMailId = strMailId;
			this.strMailName = strMailName;
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
        this.setSize(new Dimension(800, 700));
        this.setContentPane(getJPanel());

        searchDetail();
        
        setTitle("Mail create page");
        
		if (CommonConstant.MODE_NEW.equals(strMode) || CommonConstant.MODE_COPY.equals(strMode)) {
			// confirm button
			btnConfirm.setText("Create");
		} else {
			btnConfirm.setText("Update");
		}
		
		if (CommonConstant.MODE_UPDATE.equals(strMode)) {
			txtMailId.setText(strMailId);
		}
		txtMailName.setText(strMailName);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			pageInfoLbl = new JLabel();
			pageInfoLbl.setBounds(new java.awt.Rectangle(224,480,315,30));
			pageInfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
			pageInfoLbl.setText("");
			lblSubject = new JLabel();
			lblSubject.setBounds(new Rectangle(10, 140, 80, 20));
			lblSubject.setText("Subject");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 10, 500, 20));
			jLabel.setText("Define or view the mail option.");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getBtnConfirm(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(lblSubject, null);
			jPanel.add(pageInfoLbl, null);
			jPanel.add(getSearchPatternName(), null);
			jPanel.add(getLblTo());
			jPanel.add(getLblCc());
			jPanel.add(getLblBcc());
			jPanel.add(getLblContent());
			jPanel.add(getTxtTo());
			jPanel.add(getTxtCC());
			jPanel.add(getTxtBCC());
			jPanel.add(getTxtContent());
			jPanel.add(getLblAttachment());
			jPanel.add(getChkAttachment());
			jPanel.add(getLblMailId());
			jPanel.add(getLblMailName());
			jPanel.add(getTxtMailId());
			jPanel.add(getTxtMailName());
		}
		return jPanel;
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
			btnConfirm.setSize(new Dimension(110, 30));
			btnConfirm.setLocation(new Point(10, 620));
			btnConfirm.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						
						String strMessage = checkBeforeConfirm();
						
						if (!Utils.isEmpty(strMessage)) {
							JOptionPane.showMessageDialog(MailDialog.this, strMessage);
							return;
						}
						
						String strMailSeq = "";
						
						if (CommonConstant.MODE_NEW.equals(strMode) || CommonConstant.MODE_COPY.equals(strMode)) {
							ComDao comDao = new ComDao();
							strMailSeq = "MAIL_".concat(comDao.getMailSeq());
						} else {
							strMailSeq = strMailId;
						}
						
						// set property
						Map<String, String> paramMap = new HashMap<String, String>();
						
						paramMap.put("ID", strMailSeq);
						paramMap.put("SUBJECT", txtSubject.getText());
						paramMap.put("TO", txtTo.getText());
						paramMap.put("CC", txtCC.getText());
						paramMap.put("BCC", txtBCC.getText());
						paramMap.put("ATTACHMENT", String.valueOf(chkAttachment.isSelected()));
						paramMap.put("CONTENT", txtContent.getText());
						
						MailDao mailDao = new MailDao();
						mailDao.dealMail(paramMap, strMailSeq, txtMailName.getText());
						
						JOptionPane.showMessageDialog(MailDialog.this, "Completed!");
						setVisible(false);
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(MailDialog.this, "Failed!");
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
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Close");
			jButton.setSize(new Dimension(90,30));
			jButton.setLocation(new Point(690, 620));
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
	 * This method initializes searchPatternName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSearchPatternName() {
		if (txtSubject == null) {
			txtSubject = new JTextField();
			txtSubject.setBounds(new Rectangle(110, 140, 550, 20));
		}
		return txtSubject;
	}
	
	/**
	 *
	 */
	private void searchDetail() {
		if (strMailId != null && !strMailId.equals("")) {
			MailDao dao = new MailDao();
			
			Map<String, String> map = dao.searchById(strMailId);
			txtSubject.setText(map.get("SUBJECT"));
			txtTo.setText(map.get("TO"));
			txtCC.setText(map.get("CC"));
			txtBCC.setText(map.get("BCC"));
			chkAttachment.setSelected(Boolean.valueOf(map.get("ATTACHMENT")));
			txtContent.setText(map.get("CONTENT"));
		}
	}
	
	private String checkBeforeConfirm() {
		String strMessage = "";
		
		if (Utils.isEmpty(txtSubject.getText())) {
			strMessage = "You must input the Subject!";
			return strMessage;
		}
		
		if (Utils.isEmpty(txtTo.getText())) {
			strMessage = "You must input the To mail address!";
			return strMessage;
		}
		
		if (Utils.isEmpty(txtContent.getText())) {
			strMessage = "You must input the mail content!";
		}
		
		return strMessage;
	}
	
	private JLabel getLblTo() {
		if (lblTo == null) {
			lblTo = new JLabel();
			lblTo.setText("To:");
			lblTo.setBounds(new Rectangle(10, 40, 80, 20));
			lblTo.setBounds(10, 170, 80, 20);
		}
		return lblTo;
	}
	private JLabel getLblCc() {
		if (lblCc == null) {
			lblCc = new JLabel();
			lblCc.setText("CC:");
			lblCc.setBounds(new Rectangle(10, 40, 80, 20));
			lblCc.setBounds(10, 200, 80, 20);
		}
		return lblCc;
	}
	private JLabel getLblBcc() {
		if (lblBcc == null) {
			lblBcc = new JLabel();
			lblBcc.setText("BCC:");
			lblBcc.setBounds(new Rectangle(10, 40, 80, 20));
			lblBcc.setBounds(10, 230, 80, 20);
		}
		return lblBcc;
	}
	private JLabel getLblContent() {
		if (lblContent == null) {
			lblContent = new JLabel();
			lblContent.setText("Content:");
			lblContent.setBounds(new Rectangle(10, 40, 80, 20));
			lblContent.setBounds(10, 300, 80, 20);
		}
		return lblContent;
	}
	private JTextField getTxtTo() {
		if (txtTo == null) {
			txtTo = new JTextField();
			txtTo.setBounds(new Rectangle(110, 40, 550, 20));
			txtTo.setBounds(110, 170, 550, 20);
		}
		return txtTo;
	}
	private JTextField getTxtCC() {
		if (txtCC == null) {
			txtCC = new JTextField();
			txtCC.setBounds(new Rectangle(110, 40, 550, 20));
			txtCC.setBounds(110, 200, 550, 20);
		}
		return txtCC;
	}
	private JTextField getTxtBCC() {
		if (txtBCC == null) {
			txtBCC = new JTextField();
			txtBCC.setBounds(new Rectangle(110, 40, 550, 20));
			txtBCC.setBounds(110, 230, 550, 20);
		}
		return txtBCC;
	}
	private JTextArea getTxtContent() {
		if (txtContent == null) {
			txtContent = new JTextArea();
			txtContent.setBounds(110, 300, 550, 300);
		}
		return txtContent;
	}
	private JLabel getLblAttachment() {
		if (lblAttachment == null) {
			lblAttachment = new JLabel();
			lblAttachment.setText("Attachment:");
			lblAttachment.setBounds(new Rectangle(10, 40, 80, 20));
			lblAttachment.setBounds(10, 260, 105, 20);
		}
		return lblAttachment;
	}
	private JCheckBox getChkAttachment() {
		if (chkAttachment == null) {
			chkAttachment = new JCheckBox("if need to add the execute evidence in the mail, check it");
			chkAttachment.setBounds(110, 260, 500, 23);
		}
		return chkAttachment;
	}
	private JLabel getLblMailId() {
		if (lblMailId == null) {
			lblMailId = new JLabel();
			lblMailId.setText("Mail ID");
			lblMailId.setBounds(new Rectangle(10, 140, 80, 20));
			lblMailId.setBounds(10, 50, 80, 20);
		}
		return lblMailId;
	}
	private JLabel getLblMailName() {
		if (lblMailName == null) {
			lblMailName = new JLabel();
			lblMailName.setText("Mail Name");
			lblMailName.setBounds(new Rectangle(10, 140, 80, 20));
			lblMailName.setBounds(10, 80, 100, 20);
		}
		return lblMailName;
	}
	private JTextField getTxtMailId() {
		if (txtMailId == null) {
			txtMailId = new JTextField();
			txtMailId.setEditable(false);
			txtMailId.setBounds(new Rectangle(110, 140, 550, 20));
			txtMailId.setBounds(110, 50, 100, 20);
		}
		return txtMailId;
	}
	private JTextField getTxtMailName() {
		if (txtMailName == null) {
			txtMailName = new JTextField();
			txtMailName.setBounds(new Rectangle(110, 140, 550, 20));
			txtMailName.setBounds(110, 80, 550, 20);
		}
		return txtMailName;
	}
}  //  @jve:decl-index=0:visual-constraint="6,6"
