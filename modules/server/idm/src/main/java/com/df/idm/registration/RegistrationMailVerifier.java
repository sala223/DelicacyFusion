package com.df.idm.registration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

import com.df.core.mail.RichMailSender;
import com.df.idm.entity.User;

public class RegistrationMailVerifier implements UserRegistrationVerfier {

	private RichMailSender sender;

	private RandomTokenRepository tokenRepository;

	public RegistrationMailVerifier(RichMailSender sender, RandomTokenRepository tokenRepository) {
		this.sender = sender;
		this.tokenRepository = tokenRepository;
	}

	public void setMailSender(RichMailSender sender) {
		this.sender = sender;
	}

	public void setTokenRepository(RandomTokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	@Override
	public RandomToken sentRegistrationMesage(User newUser) {
		RandomToken randomToken = tokenRepository.generateAndSaveToken(newUser);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("delicacy_fusion@163.com");
		message.setTo(newUser.getEmail());
		message.setSubject("New User Email verification");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("user", newUser);
		variables.put("link", "http://");
		sender.send("freemarker/registration_verify.ftl", message, variables);
		return randomToken;
	}

	@Override
	public boolean verifyRegistration(String token) {
		return tokenRepository.isValidToken(token);
	}

}
