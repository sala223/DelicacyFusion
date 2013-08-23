package com.df.blobstore.bundle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	    OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
	    InputStream input = blob.getBundleValue().getDataInBundle();
	    try {
		byte[] buffer = new byte[1024];
		int size = 0;
		while ((size = input.read(buffer)) > 0) {
		    out.write(buffer, 0, size - 1);
		}
	    } finally {
		out.flush();
		out.close();
		input.close();
	    }
	} catch (IOException ex) {
	    String msg = "Add blob %s error";
	    throw new BlobStoreException(ex, msg, fileName);
	}
    }

    @Override
    public boolean fetchBlob(Blob blob, BundleKey key) {
	String fileName = key.getKeyInBundle();
	InputStream in = null;
	try {
	    in = new BufferedInputStream(new FileInputStream(new File(directory, fileName)));
	    blob.readBundleValue(in);
	    return true;
	} catch (FileNotFoundException ex) {
	    return false;
	} catch (IOException ex) {
	    String msg = "get blob %s error";
	    throw new BlobStoreException(ex, msg, fileName);
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}
	    } catch (IOException ex) {
	    }
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
