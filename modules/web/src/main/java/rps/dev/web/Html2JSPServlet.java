package rps.dev.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Html2JSPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Html2JSPServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String htmlPath = request.getServletPath();
        request.getRequestDispatcher(DevUtil.extHtml2Jsp(htmlPath)).forward(request, response);
    }
}
