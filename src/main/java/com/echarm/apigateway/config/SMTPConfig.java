package com.echarm.apigateway.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configurable
public class SMTPConfig {
	public static final String ADDRESS = "echarmcorp@gmail.com";
	public static final String PASSWORD = "OSZuv1whK6VahNMV";

	public SMTPConfig() {
		// TODO Auto-generated constructor stub
	}
	
	@Bean
	public JavaMailSenderImpl javaMailSenderImpl(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		//Set gmail email id
		mailSender.setUsername(ADDRESS);
		//Set gmail email password
		mailSender.setPassword(PASSWORD);
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return mailSender;
	}

}
