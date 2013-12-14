package com.df.idm.registration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.df.core.common.utils.DataMarshaller;
import com.df.idm.entity.User;
import com.df.idm.exception.UserRegistrationException;

public class SecuredEmailRegistrationTokenRepository implements EmailRegistrationTokenRepository {

	private DataMarshaller dataMarshaller;

	private static final Charset utf8 = Charset.forName("utf-8");

	private int validDuration;

	public SecuredEmailRegistrationTokenRepository(DataMarshaller dataMarshaller) {
		this.dataMarshaller = dataMarshaller;
	}

	public void setDataMarshaller(DataMarshaller dataMarshaller) {
		this.dataMarshaller = dataMarshaller;
	}

	@Override
	public RandomToken generateAndSaveToken(User newUser) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			byte[] email = newUser.getEmail().getBytes(utf8);
			Calendar calendar = Calendar.getInstance();
			Date validFrom = new Date();
			calendar.setTime(validFrom);
			calendar.add(Calendar.MINUTE, validDuration);
			long validUntil = calendar.getTime().getTime();
			dos.writeLong(validUntil);
			dos.write(email);
			byte[] securedData = dataMarshaller.seal(bos.toByteArray());
			String token = Base64.encodeBase64URLSafeString(securedData);
			RandomToken randomToken = new RandomToken();
			randomToken.setToken(token);
			randomToken.setValidFrom(validFrom);
			randomToken.setValidTo(calendar.getTime());
			return randomToken;
		} catch (Throwable ex) {
			throw new UserRegistrationException(ex);
		}
	}

	@Override
	public String isValidToken(String token) {
		try {
			byte[] securedData = Base64.decodeBase64(token);
			byte[] rawData = dataMarshaller.disclose(securedData);
			ByteArrayInputStream arrayInput = new ByteArrayInputStream(rawData);
			DataInputStream inputStream = new DataInputStream(arrayInput);
			long validUntil = inputStream.readLong();
			if (new Date().getTime() <= validUntil) {
				return new String(rawData, 8, rawData.length - 8, utf8);
			} else {
				return null;
			}
		} catch (Throwable ex) {
			return null;
		}
	}

	@Override
	public void setValidDuration(int minutes) {
		this.validDuration = minutes;
	}

}
