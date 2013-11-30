package com.df.core.common.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Assert;

;
public abstract class SecurityUtils {

	public static final String KEY_STORE_TYPE_JKS = "jks";

	public static final String SECRET_ALGORITHM_AES = "AES";

	public static final String KEY_PAIR_ALGORITHM_RSA = "RSA";

	public static final String KEY_PAIR_ALGORITHM_DSA = "DSA";

	public static SecretKey createSecretKey(String algorithm, int bit) throws NoSuchAlgorithmException {
		Assert.notNull(algorithm);
		KeyGenerator kgen = KeyGenerator.getInstance(algorithm);
		kgen.init(bit);
		return kgen.generateKey();
	}

	public static SecretKey createAESSecretKey() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance(SECRET_ALGORITHM_AES);
		kgen.init(128);
		return kgen.generateKey();
	}

	public static KeyPair createKeyPair(String algorithm, AlgorithmParameterSpec params) {
		Assert.notNull(algorithm);
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance(algorithm);
			if (params != null) {
				generator.initialize(params);
			}
			return generator.genKeyPair();
		} catch (Throwable ex) {
			throw new SecurityException(ex, "Failed create key pair with algorithm parameters %s", params.toString());
		}
	}

	public static byte[] encrypt(SecretKey key, byte[] content) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			return cipher.doFinal(content);
		} catch (Throwable e) {
			throw new SecurityException(e);
		}
	}

	public static byte[] decrypt(String algorithm, byte[] key, byte[] source) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return cipher.doFinal(source);
		} catch (Exception e) {
			throw new SecurityException(e);
		}
	}

	public static char[] getRandomPassword(int length) {
		Assert.isTrue(length > 0 && length < 500, "password length is restricted from 1 to 128");
		char[] pwd = new char[length];
		for (int pos = 0; pos < length; pos++) {
			char nextChar = getRandomChar();
			pwd[pos] = nextChar;
		}
		return pwd;
	}

	public static char getRandomChar() {
		Random random = new Random();
		char newChar;
		int next = random.nextInt();
		int type = random.nextInt() % 3;
		int d;
		switch (type) {
		case 0:
			d = next % 10;
			if (d < 0) {
				d = d * (-1);
			}
			newChar = (char) (d + 48);
			break;
		case 1:
			d = next % 26;
			if (d < 0) {
				d = d * (-1);
			}
			newChar = (char) (d + 97);
			break;
		default:
			d = (next % 26);
			if (d < 0) {
				d = d * (-1);
			}
			newChar = (char) (d + 65);
		}
		return newChar;
	}
}
