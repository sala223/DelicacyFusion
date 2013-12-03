<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ page import="rps.dev.web.DevUtil" %>

<%
String pagename = DevUtil.extJsp2Html(request.getServletPath());
pagename = pagename.substring(1,pagename.length() - ".html".length());
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
<meta name="viewport" content="width=device-width" />

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-extension.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/<%=pagename%>.css" />

<script type="text/javascript">
  window.main=[];
  window.memoryStorage={};
</script>
