package com.halal.halal.services;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	 private VelocityEngine velocityEngine;

	//@Autowired 
	//private SpringTemplateEngine templateEngine; 
	  
	@Async
	public void send2faAuthCode(String message,String email,String subject){
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
			msg.setContent(message, "text/html");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setFrom("demo@i-cmms.com");
			System.err.println("Sending code for 2FA");
			mailSender.send(msg);
			System.err.println("2FA code sent");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	@Async
	public void sendEmailToAddress(String message,String email,String subject){
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
			msg.setContent(message, "text/html");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setFrom("demo@i-cmms.com"); 
			System.err.println("message Error sent");
			mailSender.send(msg);
			System.err.println("Message Sent");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}



