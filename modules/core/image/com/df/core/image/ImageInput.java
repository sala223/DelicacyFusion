package com.df.core.image;

import java.io.InputStream;

public interface ImageInput {

    String realm();

    InputStream getImageStream();
}
