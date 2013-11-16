package com.df.blobstore.image.http;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.blobstore.image.ImageAttributes;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageKeyResolver;

public class DefaultImageLinkCreator implements ImageLinkCreator {

	@Autowired
	private ServletContext servletContext;

	private String imageRequestPrefix;

	private ImageKeyResolver keyResolver;

	public DefaultImageLinkCreator() {
	}

	public void setImageRequestPrefix(String imageRequestPrefix) {
		this.imageRequestPrefix = imageRequestPrefix;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void setKeyResolver(ImageKeyResolver keyResolver) {
		this.keyResolver = keyResolver;
	}

	@Override
	public String createImageLink(String imageKey) {
		ImageAttributes attributes = keyResolver.resolveImageAttributes(new ImageKey(imageKey));
		String suffix = attributes.getFormat().name().toLowerCase();
		if (imageRequestPrefix != null) {
			return imageRequestPrefix + "/" + imageKey + "." + suffix;
		} else {
			return "/" + imageKey + "." + suffix;
		}
	}

	@Override
	public String createImageLink(ImageAttributes attributes) {
		ImageKey key = keyResolver.hash(attributes);
		String suffix = attributes.getFormat().getFileSuffix();
		if (imageRequestPrefix != null) {
			return imageRequestPrefix + "/" + key.toString() + suffix;
		} else {
			return "/" + key.toString() + suffix;
		}
	}
}
