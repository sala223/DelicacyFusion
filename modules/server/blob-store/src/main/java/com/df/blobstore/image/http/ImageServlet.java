package com.df.blobstore.image.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.df.blobstore.image.Image;
import com.df.blobstore.image.ImageFormat;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageService;

public abstract class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(ImageServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
	initImageService(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	ImageKey imageKey = this.getImageKeyFromRequest(req);
	if (imageKey == null) {
	    resp.setStatus(404);
	} else {
	    InputStream in = null;
	    OutputStream out = null;
	    try {
		Image image = imageService.fetchImage(imageKey);
		ImageFormat format = image.getImageAttributes().getFormat();
		resp.setContentType("image/" + format.name().toLowerCase());
		in = image.getBundleValue().getDataInBundle();
		resp.setContentLength(image.getBundleValue().getSize());
		out = resp.getOutputStream();
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
		    out.write(buf, 0, count);
		}
	    } catch (Throwable ex) {
		String msg = "Cannot get image from key " + imageKey.toString();
		logger.error(msg, ex);
		resp.setStatus(404);
	    } finally {
		if (in != null) {
		    try {
			in.close();
		    } catch (Throwable ex) {
		    }
		}
		if (out != null) {
		    try {
			out.close();
		    } catch (Throwable ex) {
		    }
		}
	    }
	}
    }

    protected abstract ImageKey getImageKeyFromRequest(HttpServletRequest request);

    protected abstract void initImageService(ServletConfig config);

    protected void setImageService(ImageService imageService) {
	this.imageService = imageService;
    }

}
