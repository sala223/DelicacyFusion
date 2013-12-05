<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <title>DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page">
      <div id="panel">
        <div class="titlebar" id="titlebar">

        </div>
        <div class="main">
          <iframe id="frame-a"></iframe>
        </div>
      </div>
    </div>

    <dev:includeJS src="js/jquery-2.0.3.min.js" />
    <dev:includeJS src="js/bootstrap.min.js" />
    <dev:includeJS src="js/jquery-extension.js" />
    <dev:includeJS src="js/i19.js" />
    <script type="text/javascript">
    $(document).ready(function(){
      $('#frame-a').attr('src',getQueryParams()['url']||'index.html');
    });

    function setTitle(html){
      $('#titlebar').empty().append(html);
    }

    function go(url){
      $('#frame-a').attr('src',url);
    }
    </script>
  </body>
</html>