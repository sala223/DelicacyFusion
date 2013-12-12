<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="../WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <title>DelicacyFusion Web</title>
    <jsp:include page="jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page">
      <div id="panel">
        <div class="titlebar" id="titlebar">
          <div class="ltr">
            <span class="logo-text">DelicacyFusion</span>
          </div>
          <div class="rtl">
            <a href="tenant-application.html" class="btn-primary" data-i19="def">Trial Application</a>
            <a href="logon.html" data-i19="def">Logon</a>
          </div>
        </div>
        <div class="main">
        </div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){
      window.parent.setTitle($('#titlebar').html());
    });
    </script>
    <jsp:include page="jsptiles/jsmain.jsp" />
  </body>
</html>