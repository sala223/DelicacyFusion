package com.df.blobstore.bundle;

public interface BundleService {

    void addBlob(Blob blob);

    BundleValue fetchBlob(BundleKey key);

    void updateBlob(Blob blob);

    void deleteBlob(BundleKey key);

}
