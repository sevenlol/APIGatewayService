package com.echarm.apigateway.accountsystem.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.config.SMTPConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmailSender {

	public EmailSender() {
		// TODO Auto-generated constructor stub
	}
	
	public static void sendDoctorRegistrationEmail(Account acc) throws MessagingException, JsonProcessingException {
		
		String mailText = "";
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(SMTPConfig.class);
		ctx.refresh();
		JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
	    mailMsg.setFrom(SMTPConfig.ADDRESS);
	    mailMsg.setTo(SMTPConfig.ADDRESS);
	    mailMsg.setSubject("[Doctor Registration] Doctor Name: " + acc.getUserName());
	    ObjectMapper mapper = new ObjectMapper();
	    
	    mailText = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(acc);
	    
	    mailMsg.setText(mailText);
		mailSender.send(mimeMessage);
		System.out.println("---Done---");
		ctx.close();
	}

}
