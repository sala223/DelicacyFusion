package com.df.blobstore.bundle;

import java.io.IOException;
import java.io.InputStream;

public interface Blob {

    BundleKey getBundleKey();

    BundleValue getBundleValue();

    void readBundleValue(InputStream input) throws IOException;

    void readBundleKey(String key) throws IOException;
}
