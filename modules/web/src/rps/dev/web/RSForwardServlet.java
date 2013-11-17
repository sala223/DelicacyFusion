package rps.dev.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class RESTFulForwardingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String HOST_APP = "/m-console", HOST_APP_RS_PATH = "/rs";

	@Override
	protected void service(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		this.getServletContext()
		.getContext(HOST_APP)
		.getRequestDispatcher(HOST_APP_RS_PATH)
		.forward(new HttpServletRequestWrapper(request){
			@Override
			public String getRequestURI(){

				String originURI = request.getRequestURI().substring((RESTFulForwardingServlet.this.getServletContext().getContextPath() + request.getServletPath()).length());
				System.out.println(originURI);

				return HOST_APP + HOST_APP_RS_PATH + originURI;
			}

			@Override
		    public StringBuffer getRequestURL() {

		        StringBuffer url = new StringBuffer();
		        String scheme = getScheme();
		        int port = getServerPort();
		        if (port < 0) {
		            port = 80;
		        }

		        url.append(scheme);
		        url.append("://");
		        url.append(getServerName());
		        if ((scheme.equals("http") && (port != 80))
		            || (scheme.equals("https") && (port != 443))) {
		            url.append(':');
		            url.append(port);
		        }
		        url.append(getRequestURI());

		        return url;
		    }
		}, response);
	}

}
