package com.echarm.apigateway.accountsystem.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.echarm.apigateway.accountsystem.model.Account;
import com.echarm.apigateway.config.MailAccount;
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
		MailAccount mailAccount = ctx.getBean(MailAccount.class);
		if (mailSender == null || mailAccount == null) {
		    System.out.println("---Get mail sender/account failed---");
		    ctx.close();
		    return;
		}

		MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
	    mailMsg.setFrom(mailAccount.getAddress());
	    mailMsg.setTo(mailAccount.getAddress());
	    mailMsg.setSubject("[Doctor Registration] Doctor Name: " + acc.getUserName());
	    ObjectMapper mapper = new ObjectMapper();

	    mailText = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(acc);

	    mailMsg.setText(mailText);
		mailSender.send(mimeMessage);
		System.out.println("---Done---");
		ctx.close();
	}

	public static void sendPasswordChangedEmail(String username, String newPassword, String email) throws MessagingException {

	    if (email == null || username == null || newPassword == null) {
	        throw new MessagingException("Null email address or username or password!");
	    }

	    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SMTPConfig.class);
        ctx.refresh();
        JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
        MailAccount mailAccount = ctx.getBean(MailAccount.class);
        if (mailSender == null || mailAccount == null) {
            System.out.println("---Get mail sender/account failed---");
            ctx.close();
            return;
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        mailMsg.setFrom(mailAccount.getAddress());
        mailMsg.setTo(email);
        mailMsg.setSubject("[ECharm] Password Reset Successfully!");

        StringBuilder builder = new StringBuilder();
        builder
            .append(String.format("Username: %s\n", username))
            .append(String.format("New Password: %s\n", newPassword));
        String mailText = builder.toString();

        mailMsg.setText(mailText);
        mailSender.send(mimeMessage);
        System.out.println("---Done---");

        ctx.close();
	}
}
