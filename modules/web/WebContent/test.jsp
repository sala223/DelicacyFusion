<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Test - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
    <style>
    </style>
  </head>
  <body>
    <div id="page">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel"></div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

    	var mask = $('#page').mask();
    	
    	setTimeout(function(){
    		mask.complete({
    			iconClass:'remove'
    		});
    	},1000);
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>