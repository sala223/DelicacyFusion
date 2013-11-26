package rps.dev.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JavaScript2JSPServlet
 */
public class JavaScript2JSPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File jsFile = new File(this.getServletContext().getRealPath(request.getServletPath()));

		if(jsFile.exists()){
			response.setContentType("text/javascript; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			Writer w = response.getWriter();
			new ReaderWriterChannel(new InputStreamReader(new FileInputStream(jsFile),"UTF-8")).write(w);
			w.flush();
		} else {
			this.getServletContext()
			.getRequestDispatcher(DevUtil.changeExt(request.getServletPath(), "js", "jsp"))
			.forward(request, response);
		}
	}
}
