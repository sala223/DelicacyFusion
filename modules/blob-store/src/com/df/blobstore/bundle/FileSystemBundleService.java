package com.df.blobstore.bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileSystemBundleService implements BundleService {

    private File directory;

    public FileSystemBundleService(File directory) {
	if (directory == null) {
	    throw new BlobStoreException("Directory must not be null");
	}
	if (!directory.isDirectory()) {
	    throw new BlobStoreException("File %s is not a directory", directory.getAbsolutePath());
	}
	this.directory = directory;
    }

    @Override
    public void addBlob(Blob blob) {
	String fileName = blob.getBundleKey().getKeyInBundle();
	File file = new File(directory, fileName);
	try {
	    FileOutputStream stream = new FileOutputStream(file);
	    try {
		stream.write(blob.getBundleValue().getDataInBundle());
	    } finally {
		stream.flush();
		stream.close();
	    }
	} catch (IOException ex) {
	    String msg = "Add blob %s error";
	    throw new BlobStoreException(ex, msg, fileName);
	}
    }

    @Override
    public byte[] fetchBlob(BundleKey key) {
	String fileName = key.getKeyInBundle();
	File file = new File(directory, fileName);
	if (!file.exists()) {
	    return new byte[0];
	}
	try {
	    return Files.readAllBytes(file.toPath());
	} catch (IOException ex) {
	    String msg = "get blob %s error";
	    throw new BlobStoreException(ex, msg, fileName);
	}
    }

    @Override
    public void updateBlob(Blob blob) {
	addBlob(blob);
    }

    @Override
    public void deleteBlob(BundleKey key) {
	String fileName = key.getKeyInBundle();
	File file = new File(directory, fileName);
	file.deleteOnExit();
    }

    @Override
    public boolean hasBlob(BundleKey key) {
	String fileName = key.getKeyInBundle();
	File file = new File(directory, fileName);
	return file.exists();
    }

}
