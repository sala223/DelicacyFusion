<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ page import="rps.dev.web.DevUtil" %>

<%
final String[] pages = new String[]{
        "restaurant",
        "items",
        "menu",
        "promotion"};

final String currHtml = DevUtil.extJsp2Html(request.getServletPath());
%>
<div id="navi">
  <div>
<%for(String p : pages){%>
    <div class="menu <%=currHtml.endsWith(p+".html")?"curr":""%>"><a href="<%=p+".html"%>" data-i19="def"><%="page_"+p%></a></div>
<%}%>
  </div>
  <a href="logon.html" style="display:none;"></a>
</div>
