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
      <jsp:include page="WEB-INF/jsptiles/store-nav.jsp" />
      <div id="panel">
        <div class="titlebar" id="titlebar">
          <div class="ltr">
            <div class="home"></div>
            <span class="logo-text">DelicacyFusion</span>
          </div>
          <div class="rtl">
            <a href="logon.html" data-i19="def">Logout</a>
          </div>
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
        <jsp:include page="WEB-INF/jsptiles/store-editor.jsp" />
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){
      var editmask;

      window.storeEditor.ajaxEvent = function(){
        editmask.dismiss();
        //TODO Update Current Store Name
      };

      $('#action-info').click(function(){
        editmask = $('#panel').mask({loadingText:transStr('loading')});

    		$.ajax('{prefix}/tenant/{tenant}/store/{store}')
    		.done(function(data){
      		editmask.clearIndicator();
      	  storeEditor.loadStoreDetail(data);
      	  showEditor(editmask);
    		});
        
      });
      
      window.parent.setTitle($('#titlebar').html());
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>