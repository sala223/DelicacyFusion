package com.df.blobstore.image.http;

import com.df.blobstore.image.ImageAttributes;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageKeyResolver;
import com.df.blobstore.image.http.ImageLinkCreator;

public class DefaultImageReferenceFactory implements ImageReferenceFactory {

	private ImageLinkCreator imageLinkCreator;

	private ImageKeyResolver imageKeyResolver;

	public DefaultImageReferenceFactory(ImageLinkCreator imageLinkCreator, ImageKeyResolver imageKeyResolver) {
		this.imageLinkCreator = imageLinkCreator;
		this.imageKeyResolver = imageKeyResolver;
	}

	@Override
	public ImageReference createImageReference(ImageKey imageKey) {
		ImageAttributes attributes = imageKeyResolver.resolveImageAttributes(imageKey);
		ImageReference ref = new ImageReference();
		ref.setImageId(imageKey.getKey());
		ref.setWidth(attributes.getWidth());
		ref.setHeigth(attributes.getHeigth());
		ref.setFormat(attributes.getFormat().name());
		ref.setImageLink(imageLinkCreator.createImageLink(attributes));
		return ref;
	}

}
