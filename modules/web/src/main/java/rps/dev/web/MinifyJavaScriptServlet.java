package rps.dev.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class MinifyJavaScriptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final boolean minjs = "y".equals(request.getParameter("min"));
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/javascript");

		Writer w = response.getWriter();
		Reader r = new InputStreamReader(new FileInputStream(this.getServletContext().getRealPath(request.getServletPath())),"UTF-8");
		if(minjs){
			new JavaScriptCompressor(
				r,
				new ErrorReporter(){

					@Override
					public void error(String arg0, String arg1, int arg2,
							String arg3, int arg4) {
					}

					@Override
					public EvaluatorException runtimeError(String arg0,
							String arg1, int arg2, String arg3, int arg4) {
						return new EvaluatorException(arg0);
					}

					@Override
					public void warning(String arg0, String arg1, int arg2,
							String arg3, int arg4) {
					}
				}
			).compress(w, 2000, false, false, false, false);

		}else{
			new ReaderWriterChannel(r).write(w);
		}

		w.flush();
	}

}
