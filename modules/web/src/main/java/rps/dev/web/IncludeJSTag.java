package rps.dev.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class IncludeJSTag extends TagSupport {

    private String src;

    @Override
    public int doStartTag() {
        // String contextPath =
        // this.pageContext.getServletContext().getContextPath();
        JspWriter jw = this.pageContext.getOut();

        try {
            jw.write("<script type=\"text/javascript\" src=\"");

            if (cdn.containsKey(this.src)) {
                jw.write(cdn.get(this.src));
            } else {
                jw.write(this.src);
            }
            jw.write("\"></script>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (SKIP_BODY);
    }

    public void setSrc(String src) {
        this.src = src;
    }

    private static Map<String, String> cdn = new HashMap<String, String>(10);
    static {
        // cdn.put("js/jquery-2.0.3.min.js",
        // "http://libs.baidu.com/jquery/2.0.3/jquery.min.js");
    }
}
