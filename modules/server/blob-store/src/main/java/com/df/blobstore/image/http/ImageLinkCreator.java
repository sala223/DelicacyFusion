package com.df.blobstore.image.http;

import com.df.blobstore.image.ImageAttributes;

public interface ImageLinkCreator {

    String createImageLink(String imageKey);
    
    String createImageLink(ImageAttributes attributes);
}
