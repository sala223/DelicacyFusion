package com.df.core.provision;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

public class JsonFileEntitySources extends JsonStreamEntitySources {

	private List<JsonFile> files = new ArrayList<JsonFile>();

	private Iterator<JsonFile> iterator;

	public JsonFileEntitySources() {
	}

	public JsonFileEntitySources(List<JsonFile> files) {
		this.files = files;
	}

	public void setJsonFiles(List<JsonFile> files) {
		if (iterator != null) {
			throw new IllegalStateException("This object is open to be read, call close() first before initialization");
		}
		Assert.notNull(files);
		this.files = files;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		for (JsonFile file : files) {
			buffer.append(file.toString() + ";");
		}
		buffer.append("}");
		return buffer.toString();
	}

	public static class JsonFile {

		private String fileName;

		private String entityClassName;

		JsonFile() {
		}

		public JsonFile(String fileName, String entityClassName) {
			this.fileName = fileName;
			this.entityClassName = entityClassName;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getEntityClassName() {
			return entityClassName;
		}

		public void setEntityClassName(String entityClassName) {
			this.entityClassName = entityClassName;
		}

		@Override
		public String toString() {
			return "JsonFile [fileName=" + fileName + ", entityClassName=" + entityClassName + "]";
		}
	}

	@Override
	public void open() {
		iterator = files.iterator();
	}

	@Override
	public void close() {
		iterator = null;
	}

	@Override
	protected JsonStream nextJsonStream() {
		JsonFile next = iterator.next();
		try {
			return new JsonStream(new FileInputStream(next.getFileName()), next.getEntityClassName());
		} catch (FileNotFoundException ex) {
			throw new DataProvisioningException(ex);
		}
	}
}
