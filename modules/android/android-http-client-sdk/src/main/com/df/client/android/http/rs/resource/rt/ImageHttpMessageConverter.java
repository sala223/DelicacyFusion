package com.df.client.android.http.rs.resource.rt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

public class ImageHttpMessageConverter extends AbstractHttpMessageConverter<byte[]> {

	public ImageHttpMessageConverter() {
		super(new MediaType[] { new MediaType("image", "jpeg"), new MediaType("image", "gif"),
		        new MediaType("image", "png") });
	}

	public boolean supports(Class<?> clazz) {
		return byte[].class.equals(clazz);
	}

	public byte[] readInternal(Class<? extends byte[]> clazz, HttpInputMessage inputMessage) throws IOException {
		long contentLength = inputMessage.getHeaders().getContentLength();
		if (contentLength >= 0L) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream((int) contentLength);
			FileCopyUtils.copy(inputMessage.getBody(), bos);
			return bos.toByteArray();
		}

		return FileCopyUtils.copyToByteArray(inputMessage.getBody());
	}

	protected Long getContentLength(byte[] bytes, MediaType contentType) {
		return Long.valueOf(bytes.length);
	}

	protected void writeInternal(byte[] bytes, HttpOutputMessage outputMessage) throws IOException {
		FileCopyUtils.copy(bytes, outputMessage.getBody());
	}
}
