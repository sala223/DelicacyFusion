package rps.dev.web;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.sommeri.less4j.Less4jException;
import com.github.sommeri.less4j.LessCompiler;
import com.github.sommeri.less4j.LessCompiler.CompilationResult;
import com.github.sommeri.less4j.LessCompiler.Problem;
import com.github.sommeri.less4j.core.ThreadUnsafeLessCompiler;
import com.yahoo.platform.yui.compressor.CssCompressor;

public class Css2LessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Css2LessServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final boolean mincss = "y".equals(request.getParameter("min"));

        LessCompiler compiler = new ThreadUnsafeLessCompiler();
        CompilationResult cr;
        try {

            Reader cssReader = null;

            File lessFile = new File(this.getServletContext().getRealPath(DevUtil.extCss2Less(request.getServletPath())));
            if (lessFile.exists()) {
                cr = compiler.compile(lessFile);

                for (Problem warning : cr.getWarnings()) {
                    System.err.println("WARNING " + warning.getLine() + ":" + warning.getCharacter() + " " + warning.getMessage());
                }

                cssReader = new StringReader(cr.getCss());
            } else {
                cssReader = new FileReader(this.getServletContext().getRealPath(request.getServletPath()));
            }

            response.setContentType("text/css");
            PrintWriter pw = response.getWriter();
            if (mincss) {
                new CssCompressor(cssReader).compress(pw, 0);
            } else {
                new ReaderWriterChannel(cssReader).write(pw);
            }

            pw.flush();

            cssReader.close();
        } catch (Less4jException lessException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain");
            PrintWriter pw = response.getWriter();
            for (Problem err : lessException.getErrors()) {
                pw.println("Error " + "l" + err.getLine() + ":c" + err.getCharacter() + " - " + err.getMessage());
            }
            pw.flush();
        }

    }
}
