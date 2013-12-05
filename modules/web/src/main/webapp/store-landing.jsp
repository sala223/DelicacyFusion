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
    <div id="page" >
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div class="titlebar" id="titlebar">
        Store Landing
        </div>
        <div class="tilecontainer">
          <div>
            <div>
              <div class="actionbar">
                <a id="action-ca" data-i19="def" href="cashier.html">CASHIER</a>
                <a id="action-em" data-i19="def">STAFF MANAGEMENT</a>
                <a id="action-tm" data-i19="def" href="tables.html">TABLE MANAGEMENT</a>
              </div>

              <div class="actionbar">
                <a id="action-info" data-i19="def">STORE INFO</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){
      window.parent.setTitle($('#titlebar').html());
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>