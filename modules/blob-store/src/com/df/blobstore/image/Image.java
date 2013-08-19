package com.df.blobstore.image;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.BinaryComparable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Writable;

import com.df.core.common.utils.ByteUtils;

public class Image implements Writable, RawComparator<BinaryComparable> {
    public static final int RGB2GRAY = 0x01;
    private int width;
    private int height;
    private int bands;
    private float[] pels;

    public Image() {
    }

    public Image(int width, int height, int bands, float[] pels) {
	this.width = width;
	this.height = height;
	this.bands = bands;
	this.pels = pels;
    }

    public Image(int width, int height, int bands) {
	this(width, height, bands, new float[width * height * bands]);
    }

    /**
     * Crops a float image according the the x,y location and the width, height
     * passed in.
     */
    public Image crop(int x, int y, int width, int height) {
	float[] pels = new float[width * height * bands];
	for (int i = y; i < y + height; i++)
	    for (int j = x * bands; j < (x + width) * bands; j++)
		pels[(i - y) * width * bands + j - x * bands] = pels[i * width * bands + j];
	return new Image(width, height, bands, pels);
    }

    /**
     * Convert between color types (black and white, gray scale, etc.).
     * Currently only RGB2GRAY
     */
    public Image convert(int type) {
	switch (type) {
	case RGB2GRAY:
	    float[] pels = new float[width * height];
	    for (int i = 0; i < width * height; i++)
		pels[i] = pels[i * bands] * 0.30f + pels[i * bands + 1] * 0.59f + pels[i * bands + 2] * 0.11f;
	    return new Image(width, height, 1, pels);
	}
	return null;
    }

    /**
     * Adds a {@link Image} to the current image
     */
    public void add(Image image) {
	float[] pels = image.getData();
	for (int i = 0; i < width * height * bands; i++)
	    pels[i] += pels[i];
    }

    /**
     * Adds a scalar to every pixel in the FloatImage
     * 
     * @param number
     */
    public void add(float number) {
	for (int i = 0; i < width * height * bands; i++)
	    pels[i] += number;
    }

    /**
     * 
     * @param image
     *            Each value is scaled by the corresponding value in image
     */
    public void scale(Image image) {
	float[] pels = image.getData();
	for (int i = 0; i < width * height * bands; i++)
	    pels[i] *= pels[i];
    }

    public void scale(float number) {
	for (int i = 0; i < width * height * bands; i++)
	    pels[i] *= number;
    }

    public float getPixel(int x, int y, int c) {
	return pels[c + (x + y * width) * bands];
    }

    public void setPixel(int x, int y, int c, float val) {
	pels[c + (x + y * width) * bands] = val;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getBands() {
	return bands;
    }

    public float[] getData() {
	return pels;
    }

    @Override
    public String toString() {
	StringBuilder result = new StringBuilder();
	result.append(width + " " + height + " " + bands + "\n");
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width * bands; j++) {
		result.append(pels[i * width * bands + j]);
		if (j < width * bands - 1)
		    result.append(" ");
	    }
	    result.append("\n");
	}
	return result.toString();
    }

}