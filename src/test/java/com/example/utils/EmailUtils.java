//package com.example.utils;
//
////import org.apache.commons.mail.util.MimeMessageParser;
////import org.apache.commons.mail.util.MimeMessageUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import jakarta.mail.Folder;
//import jakarta.mail.Message;
//import jakarta.mail.Session;
//import jakarta.mail.Store;
//import java.util.Properties;
//
//public class EmailUtils {
//
//	private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);
//
//	public String getEmailContent(String username, String password, String host, String folderName) {
//		Store store = null;
//		Folder emailFolder = null;
//
//		try {
//			Properties props = new Properties();
//			props.put("mail.store.protocol", "imaps");
//			props.put("mail.imap.host", host);
//			props.put("mail.imap.port", "993");
//			props.put("mail.imap.ssl.enable", "true");
//			props.put("mail.imap.auth", "true");
//
//			Session session = Session.getInstance(props);
//			store = session.getStore("imaps");
//			store.connect(host, username, password);
//
//			emailFolder = store.getFolder(folderName);
//			emailFolder.open(Folder.READ_ONLY);
//
//			int messageCount = emailFolder.getMessageCount();
//			if (messageCount == 0) {
//				return null;
//			}
//
//			Message message = emailFolder.getMessage(messageCount);
//			MimeMessageParser parser = new MimeMessageParser(MimeMessageUtils.getMimeMessage(message));
//			parser.parse();
//
//			String content = parser.getHtmlContent();
//			if (content == null || content.isEmpty()) {
//				content = parser.getPlainContent();
//			}
//
//			return content;
//
//		} catch (Exception e) {
//			logger.error("Error retrieving email content: {}", e.getMessage());
//			return null;
//		} finally {
//			try {
//				if (emailFolder != null && emailFolder.isOpen()) {
//					emailFolder.close(false);
//				}
//				if (store != null) {
//					store.close();
//				}
//			} catch (Exception e) {
//				logger.error("Error closing email resources: {}", e.getMessage());
//			}
//		}
//	}
//}
