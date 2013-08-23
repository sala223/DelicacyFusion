package com.df.blobstore.bundle;

public interface BundleService {

    void addBlob(Blob blob);

    boolean fetchBlob(Blob blob, BundleKey key);

    void updateBlob(Blob blob);

    void deleteBlob(BundleKey key);

    boolean hasBlob(BundleKey key);

}
