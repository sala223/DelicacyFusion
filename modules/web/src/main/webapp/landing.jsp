<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Landing - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page" class="showmenu" >
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div>
          <div>
            <div class="head"></div>
            <div class="actionbar">
              <div id="action-sm" data-i19="def">STORE MANAGEMENT</div>
              <div id="action-dm" data-i19="def">DISH MANAGEMENT</div>
              <div id="action-md" data-i19="def">MENU DESIGN</div>
            </div>
            <div class="actionbar">
              <div></div>
              <div></div>
            </div>
            <div class="foot"></div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>