<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Store - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
    <style>
    </style>
  </head>
  <body>
    <div id="page">
      <jsp:include page="WEB-INF/jsptiles/tenant-nav.jsp" />
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
        <div id="stores" class="tilecontainer"><div class="clearboth"></div></div>
        <jsp:include page="WEB-INF/jsptiles/store-editor.jsp" />
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

      $('#stores').delegate('.tile > div','click',function(ev){
          var dataIdx = parseInt($(ev.currentTarget).attr('data-idx'),10);
          if(isNaN(dataIdx)){
              showStoreDetail({
                name:'xxxxxx'
              });
          }else{
              window.location = "store-landing.html"+'?'+
		          ['tenant='+queryParams.tenant,'store='+memoryStorage['stores'][dataIdx].code].join('&');
          }
      });

      <%--
      $('#btnCancel').click(function(){
          hideStoreDetail();
      });
      --%>

      var loadmask = $('#page').mask({loadingText:'loading'});
      $.ajax('{prefix}/tenant/{tenant}/store')
      .done(function(data){
          memoryStorage['stores'] = data;

          $('#stores>div').empty().append(memoryStorage['stores'].map(function(s,i){
              var sb = ['<div class="tile"><div data-idx="'+i+'">'];
              sb.push(s.name);
              sb.push('</div></div>');
              return sb.join('');
          }).join('') +
          '<div class="tile"><div data-idx="NaN"></div></div>');

          loadmask.dismiss();
      })
      .fail(function(){
          loadmask.complete({text:'failure',iconClass:'remove'});
      });

      window.parent.setTitle($('#titlebar').html());
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>