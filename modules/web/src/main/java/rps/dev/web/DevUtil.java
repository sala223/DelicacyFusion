package rps.dev.web;

public final class DevUtil {

    private static final String jspExt = "jsp", htmlExt = "html";
    private static final String lessExt = "less", cssExt = "css";

    public static String extJsp2Html(String jspPath) {
        return changeExt(jspPath, jspExt, htmlExt);
    }

    public static String extHtml2Jsp(String htmlPath) {
        return changeExt(htmlPath, htmlExt, jspExt);
    }

    public static String extCss2Less(String cssPath) {
        return changeExt(cssPath, cssExt, lessExt);
    }

    public static String changeExt(String url, String oldExt, String newExt) {
        String path = url;

        if (path.endsWith("." + oldExt)) {
            path = path.substring(0, path.length() - oldExt.length() - 1);
        }

        return path + "." + newExt;
    }
}
