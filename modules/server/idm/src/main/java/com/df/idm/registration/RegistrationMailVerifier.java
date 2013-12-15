package com.df.idm.registration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.mail.SimpleMailMessage;

import com.df.core.mail.EmailContextProvider;
import com.df.core.mail.RichMailSender;
import com.df.idm.entity.User;

public class RegistrationMailVerifier implements UserRegistrationVerfier {

	private RichMailSender sender;

	private EmailRegistrationTokenRepository tokenRepository;

	private AsyncTaskExecutor taskExecutor;

	private EmailContextProvider emailContextProvider;

	private String tokenParameter = DEFAULT_TOKEN_PARAMETER;

	private String verifyUrlPrefix = DEFAULT_VERIFY_URL_PREFIX;

	private static final Logger logger = LoggerFactory.getLogger(RegistrationMailVerifier.class);

	public RegistrationMailVerifier(AsyncTaskExecutor taskExecutor, RichMailSender sender,
	        EmailRegistrationTokenRepository tokenRepository) {
		this.taskExecutor = taskExecutor;
		this.sender = sender;
		this.tokenRepository = tokenRepository;
	}

	public void setMailSender(RichMailSender sender) {
		this.sender = sender;
	}

	public void setTaskExecutor(AsyncTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setEmailContextProvider(EmailContextProvider emailContextProvider) {
		this.emailContextProvider = emailContextProvider;
	}

	public void setTokenRepository(EmailRegistrationTokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	@Override
	public void sentRegistrationMesage(final User newUser, final Map<String, Object> attributes) {
		taskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					RegistrationMailVerifier.this.sentEmail(newUser, attributes);
				} catch (Throwable ex) {
					logger.error("Failed to sent verification email for user " + newUser.getEmail(), ex);
				}
			}

		});
	}

	public RandomToken sentEmail(User newUser, Map<String, Object> attributes) {
		RandomToken token = tokenRepository.generateAndSaveToken(newUser);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(newUser.getEmail());
		message.setFrom(emailContextProvider.getSentFrom());
		message.setSubject("New User Email verification");
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(tokenParameter, token.getToken());
		String link = emailContextProvider.getHyperLink(false, verifyUrlPrefix, parameters);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("user", newUser);
		variables.put("link", link);
		sender.send("registration_verify.ftl", message, variables);
		return token;
	}

	@Override
	public String verifyRegistration(String token) {
		return tokenRepository.isValidToken(token);
	}

	public void setTokenParameter(String tokenParameter) {
		this.tokenParameter = tokenParameter;
	}

	public void setVerifyUrlPrefix(String verifyUrlPrefix) {
		this.verifyUrlPrefix = verifyUrlPrefix;
	}
}
