package rps.dev.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LatencyServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        int latency = 0;
        try {
            latency = Integer.parseInt(request.getHeader("x-latency"));
        } catch (NumberFormatException e) {
        	latency = 0;
        }

        try {
            if (latency > 0) {
                Thread.sleep(latency);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher(request.getRequestURI().substring((request.getContextPath() + request.getServletPath()).length())).forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
