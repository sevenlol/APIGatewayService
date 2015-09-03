package com.echarm.apigateway.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
//@PropertySource(value = "file:apigateway.config", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class SMTPConfig {
	public static final String DEFAULT_ADDRESS = "echarmcorp@gmail.com";
	public static final String DEFAULT_PASSWORD = "mailpassword";
	public static final String DEFAULT_SMTP_HOST = "smtp.gmail.com";
	public static final int DEFAULT_SMTP_PORT = 587;

	public static final String MAIL_ADDRESS_KEY = "apigateway.mail.address";
	public static final String MAIL_PASSWORD_KEY = "apigateway.mail.password";
	public static final String SMTP_HOST_KEY = "apigateway.smtp.host";
	public static final String SMTP_PORT_KEY = "apigateway.smtp.port";

	@Autowired
    private Environment env;

	public SMTPConfig() {
		// TODO Auto-generated constructor stub
	}

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl(){

	    String address = DEFAULT_ADDRESS;
	    String password = DEFAULT_PASSWORD;
	    String smtpHost = DEFAULT_SMTP_HOST;
	    int smtpPort = DEFAULT_SMTP_PORT;

	    if (env != null) {
	        if (env.getProperty(MAIL_ADDRESS_KEY) != null) {
	            address = env.getProperty(MAIL_ADDRESS_KEY);
	        }
	        if (env.getProperty(MAIL_PASSWORD_KEY) != null) {
	            password = env.getProperty(MAIL_PASSWORD_KEY);
	        }
	        if (env.getProperty(SMTP_HOST_KEY) != null) {
	            smtpHost = env.getProperty(SMTP_HOST_KEY);
	        }
	        if (env.getProperty(SMTP_PORT_KEY) != null) {
	            try {
	                smtpPort = Integer.valueOf(env.getProperty(SMTP_PORT_KEY));
	            } catch (NumberFormatException e) {
	                smtpPort = DEFAULT_SMTP_PORT;
	            }
	        }
	    }

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(smtpHost);
		mailSender.setPort(smtpPort);
		//Set gmail email id
		mailSender.setUsername(address);
		//Set gmail email password
		mailSender.setPassword(password);
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return mailSender;
	}

	@Bean
	public MailAccount getMailAccount() {
	    String address = DEFAULT_ADDRESS;
        String password = DEFAULT_PASSWORD;
        if (env != null) {
            if (env.getProperty(MAIL_ADDRESS_KEY) != null) {
                address = env.getProperty(MAIL_ADDRESS_KEY);
            }
            if (env.getProperty(MAIL_PASSWORD_KEY) != null) {
                password = env.getProperty(MAIL_PASSWORD_KEY);
            }
        }
        return new MailAccount().setAddress(address).setPassword(password);
	}
}
