package com.FYP.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailServices {
	public boolean sendEmail(String subject, String message,String to) {
		boolean flag = false;
		
		String from = "reddevilsggmufc10@gmail.com";
		//variables for host
		String host = "smtp.gmail.com";
		
		//getting the system properties
		Properties properties = System.getProperties();
		System.out.println("system properties" + properties);
		
		
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.prot", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		//step 1: getting session object.
		Session session = Session.getInstance(properties,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("reddevilsggmufc10@gmail.com","gxkfllqsbjykszyp");
			}
		});
		session.setDebug(true);
		
		//step 2: composing message
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			//from email
			mimeMessage.setFrom(from);
			//adding recipient 
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//adding subject
			mimeMessage.setSubject(subject);
			
			//adding text to message
			/* mimeMessage.setText(message); */
			mimeMessage.setContent(message,"text/html");
			
			
			//step 3: send message using transport class
			Transport.send(mimeMessage);
			System.out.println("mail sent successfully");
			flag = true;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
		
	}


}
