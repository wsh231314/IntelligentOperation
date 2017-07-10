package org.app.co.jp.ap;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

public class DateTimeChooserDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabelMerberIDTitle = null;
	private JButton jButtonSure = null;
	private JButton jButtonChooser = null;
	private String inputValue = "";

	public DateTimeChooserDialog(JDialog owner,String s,boolean b) {
		super(owner,s,b);
		initialize();
	}

	private void initialize() {
		this.setSize(332, 198);
		this.setContentPane(getJContentPane());
		this.setLocationRelativeTo(this);
		this.setVisible(true);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelMerberIDTitle = new JLabel();
			jLabelMerberIDTitle.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
			jLabelMerberIDTitle.setText("日付タイム選択：");
			jLabelMerberIDTitle.setBounds(new Rectangle(35, 37, 114, 33));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabelMerberIDTitle, null);
			jContentPane.add(getJButtonChooser(), null);
			jContentPane.add(getJButtonSure(), null);
		}
		return jContentPane;
	}

	private JButton createBtn(String text) {
		JButton btn = new DateTimeChooserJButton(text);
		btn.setUI(new BasicButtonUI());
		btn.setPreferredSize(new Dimension(80, 27));
		btn.setContentAreaFilled(false);
		btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		btn.setMargin(new Insets(0, 0, 0, 0));
		return btn;
	}

	private JButton getJButtonChooser() {
		if (jButtonChooser == null) {
			jButtonChooser = createBtn("");
			jButtonChooser.setBounds(new Rectangle(132, 37, 160, 33));
			jButtonChooser.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return jButtonChooser;
	}

	private JButton getJButtonSure() {
		if (jButtonSure == null) {
			jButtonSure = new JButton();
			jButtonSure.setBounds(new Rectangle(132, 98, 100, 33));
			jButtonSure.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
			jButtonSure.setText("確認");
			jButtonSure.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String text = jButtonChooser.getText();
					setInputValue(text);
					setVisible(false);
				}
			});
		}
		return jButtonSure;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public static void main(String[] args) {
		new DateTimeChooserDialog(null, "日付タイム選択", true);
	}
}
