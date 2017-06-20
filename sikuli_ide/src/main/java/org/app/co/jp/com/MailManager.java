package org.app.co.jp.com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.app.co.jp.dao.MailDao;
import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.ComPropertyUtil;
import org.app.co.jp.util.Utils;

public class MailManager {
	
	private static Map<String, String> mailParameterMap = new HashMap<String, String>();
	
	static BasicLogger logger = BasicLogger.getLogger();
	
	public static void sendMailById(String strMailId, String strAttachmentFile) {
		
		MailDao mailDao = new MailDao();
		Map<String, String> mailMap = mailDao.searchById(strMailId);
		mailParameterMap.putAll(mailMap);
		
		// send mail
		send(strAttachmentFile);
	}
	
	
	public static void send(String strAttachmentFile) {
		
		boolean sendFlg = Boolean.valueOf(ComPropertyUtil.getProperty("mail_send_flg"));
        String host = ComPropertyUtil.getProperty("mail_send_server");
        String user = ComPropertyUtil.getProperty("mail_login_user");
        String pwd = ComPropertyUtil.getProperty("mail_login_password");
        String sendFromUser = ComPropertyUtil.getProperty("mail_send_user_id");

        Properties props = new Properties();

        // server
        props.put("mail.smtp.host", host);
        // login
        props.put("mail.smtp.auth", "true");

        // 
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        // 
        MimeMessage message = new MimeMessage(session);
        try {
            // from user
            message.setFrom(new InternetAddress(sendFromUser));
            // to user
            String strTo = mailParameterMap.get("TO");
            List<String> toList = Arrays.asList(strTo.split(";"));
            for (String mail : toList) {
            	if (!Utils.isEmpty(mail)) {
            		message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            	}
            }
            
            // CC user
            String strCc = mailParameterMap.get("CC");
            List<String> ccList = Arrays.asList(strCc.split(";"));
            for (String mail : ccList) {
            	if (!Utils.isEmpty(mail)) {
            		message.addRecipient(Message.RecipientType.CC, new InternetAddress(mail));
            	}
            }
            
            // BCC user
            String strBcc = mailParameterMap.get("BCC");
            List<String> bccList = Arrays.asList(strBcc.split(";"));
            for (String mail : bccList) {
            	if (!Utils.isEmpty(mail)) {
            		message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail));
            	}
            }
            
            // subject
            message.setSubject(mailParameterMap.get("SUBJECT"));

            // attachment
            Multipart multipart = new MimeMultipart();

            // content
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(mailParameterMap.get("CONTENT"));
            multipart.addBodyPart(contentPart);
            // Attachment
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(strAttachmentFile);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("OperationResult.xls");
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            message.saveChanges();
            
            if (sendFlg) {
            	Transport transport = session.getTransport("smtp");
            	transport.connect(host, user, pwd);
            	transport.sendMessage(message, message.getAllRecipients());
            	transport.close();
            	logger.debug("MAIL SEND OK!");
            } else {
            	logger.debug("TO:" + strTo);
            	logger.debug("CC:" + strCc);
            	logger.debug("BCC:" + strBcc);
            	logger.debug("SUBJECT:" + mailParameterMap.get("SUBJECT"));
            	logger.debug("CONTENT:" + mailParameterMap.get("CONTENT"));
            	logger.debug("Attachment:" + strAttachmentFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.exception(e);
        }
    }
}
