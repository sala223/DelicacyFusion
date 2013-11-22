<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>DelicacyFusion Web</title>
    <link rel="stylesheet" type="text/css" href="css/main.css" />
    
    <style>
    #panel div {
        text-align:center;
        font-size:2.5em;
        color:#eee;
    }
    </style>
  </head>
  <body>
    <div id="page">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div>Under Construction</div>
      </div>
    </div>

    <dev:includeJS src="js/jquery-2.0.3.min.js" />
    <dev:includeJS src="js/common.js" />
    <dev:includeJS src="js/jquery.event.drag-2.2.js" />
    <dev:includeJS src="js/i19.js" />
    <script type="text/javascript">

    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>
