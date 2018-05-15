package com.java.ccsolution.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.java.ccsolution.model.Address;

@Component
public class EmailService {

	@Autowired
	public JavaMailSender emailSender;
	
	@Value("${base.url}")
	private String base_url;
	
	@Value("${subject}")
	private String subject;

	public void sendSimpleMessage(Address addressInDB, long unique) {
		try {
			//String subject="Regarding the CREDIT CARD Transaction";
			String genuine_url=base_url+unique+"&&genuine=YES";
			String fraud_url=base_url+unique+"&&genuine=NO";
			String text = "Hello "+addressInDB.getFullName()+",\n\nWe Came across a transaction with $4000. This email is to validate this transaction."
					+ "\n\nIf your self making this transaction, Please click the below URL.\n"+genuine_url
					+"\n\nIf you feel this is an fraud transaction, please click on below URL\n"+fraud_url
					+"\n\n\nThanks & Regards,\nCredit Card Team.";
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(addressInDB.getEmail());
			message.setSubject(subject);
			message.setText(text);
			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

}
