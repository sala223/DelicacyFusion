<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>DelicacyFusion Web</title>
    <link rel="stylesheet" type="text/css" href="css/logon.css" />
  </head>
  <body>
    <div id="page">
      <div>
        <div id="logon">
          <div class="fieldbar">
            <label for="fusername" data-i19="def">Username</label>
            <input type="text" id="fusername" />
          </div>
          <div class="fieldbar">
            <label for="fpassword" data-i19="def">Password</label>
            <input type="password" id="fpassword" />
          </div>
          <div class="buttonbar">
            <button onclick="javascript:alert(123)" data-i19="def">Logon</button>
          </div>
        </div>
      </div>
    </div>

    <dev:includeJS src="js/jquery-2.0.3.min.js" />
    <dev:includeJS src="js/common.js" />
    <dev:includeJS src="js/i19.js" />
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>