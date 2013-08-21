package com.df.blobstore.image;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.BinaryComparable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class ImageHeader implements Writable, RawComparator<BinaryComparable> {

    public static enum ImageFormat {

	UNSUPPORTED(0x0), JPEG(0x1), PNG(0x2), PPM(0x3);

	private int val;

	private ImageFormat(int val) {
	    this.val = val;
	}

	public static ImageFormat fromValue(int value) {
	    for (ImageFormat type : values()) {
		if (type.val == value) {
		    return type;
		}
	    }
	    return getDefault();
	}

	public int toValue() {
	    return val;
	}

	public static ImageFormat getDefault() {
	    return UNSUPPORTED;
	}
    }

    private int width;

    private int height;

    private int bitDepth;

    private Map<String, String> exifInformation = new HashMap<String, String>();

    private ImageFormat format;

    /**
     * Adds an EXIF field to this header object. The information consists of a
     * key-value pair where the key is the field name as it appears in the EXIF
     * 2.2 specification and the value is the corresponding information for that
     * field.
     * 
     * @param key
     *            the field name of the EXIF information
     * @param value
     *            the EXIF information
     */
    public void addEXIFInformation(String key, String value) {
	exifInformation.put(key, value);
    }

    /**
     * Get an EXIF value designated by the key. The key should correspond to the
     * 'Field Name' in the EXIF 2.2 specification.
     * 
     * @param key
     *            the field name of the EXIF information desired
     * @return either the value corresponding to the key or the empty string if
     *         the key was not found
     */
    public String getEXIFInformation(String key) {
	String value = exifInformation.get(key);

	if (value == null) {
	    return "";
	} else {
	    return value;
	}
    }

    public ImageHeader(ImageFormat format) {
	this.format = format;
    }

    public ImageHeader() {
	this.format = ImageFormat.getDefault();
    }

    public ImageFormat getFormat() {
	return format;
    }

    public int compare(BinaryComparable o1, BinaryComparable o2) {
	return 0;
    }

    public int compare(byte[] arg0, int arg1, int arg2, byte[] arg3, int arg4, int arg5) {
	return 0;
    }

    public void readFields(DataInput in) throws IOException {
	bitDepth = in.readInt();
	height = in.readInt();
	width = in.readInt();
	int size = in.readInt();
	for (int i = 0; i < size; i++) {
	    String key = Text.readString(in);
	    String value = Text.readString(in);
	    exifInformation.put(key, value);
	}
    }

    public void write(DataOutput out) throws IOException {
	out.writeInt(bitDepth);
	out.writeInt(height);
	out.writeInt(width);
	out.writeInt(exifInformation.size());
	Iterator<Entry<String, String>> it = exifInformation.entrySet().iterator();
	while (it.hasNext()) {
	    Entry<String, String> entry = it.next();
	    Text.writeString(out, entry.getKey());
	    Text.writeString(out, entry.getValue());
	}
    }
}
