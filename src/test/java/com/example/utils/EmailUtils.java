package com.example.utils;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtils {

	private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	public String getEmailContent(String username, String password, String host, String folderName) {
		try {
			Properties props = new Properties();
			
			props.setProperty("mail.store.protocol", "imaps");
			props.setProperty("mail.imap.connectiontimeout", "5000");
			props.setProperty("mail.imap.timeout", "5000");

			Session session = Session.getInstance(props);
			Store store = session.getStore();
			store.connect(host, username, password);

			Folder emailFolder = store.getFolder(folderName);
			emailFolder.open(Folder.READ_ONLY);

			int messageCount = emailFolder.getMessageCount();
			if (messageCount == 0) {
				emailFolder.close(false);
				store.close();
				return null;
			}

			Message message = emailFolder.getMessage(messageCount);

			if (message instanceof MimeMessage) {
				MimeMessage mimeMessage = (MimeMessage) message;
				String content = (String) mimeMessage.getContent().toString();
				emailFolder.close(false);
				store.close();
				return content;
			}

			emailFolder.close(false);
			store.close();
			return null;

		} catch (MessagingException e) {
			logger.error("Error retrieving email content: {}", e.getMessage());
			return null;
		} catch (Exception e) {
			logger.error("An unexpected error occurred: {}", e.getMessage());
			return null;
		}
	}
}