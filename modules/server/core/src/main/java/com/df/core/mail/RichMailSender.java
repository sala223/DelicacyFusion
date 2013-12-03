package com.df.core.mail;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public interface RichMailSender {

	void send(Object templateId, SimpleMailMessage message, Map<String, Object> variables);

}
