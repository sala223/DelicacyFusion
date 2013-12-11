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

      window.storeEditor.ajaxEvent = function(data){
        console.log(data);
        memoryStorage['stores'].push(data);
        $(getTile(memoryStorage['stores'].length - 1,data)).insertBefore($('#stores .tile').last());
      };

      $('#stores').delegate('.tile > div','click',function(ev){
          var dataIdx = parseInt($(ev.currentTarget).attr('data-idx'),10);
          if(isNaN(dataIdx)){

    		    var lastCode = "";
    		    $.each(memoryStorage['stores'],function(i,store){
    		        lastCode = lastCode < store.code ? store.code : lastCode;
    		    });
    		    if(lastCode===''){
    		        lastCode = "S00000";
    		    }

            window.storeEditor.newStoreDetail({
              "code":nextValue(lastCode),
              "createdTime":"2013-01-02 09:22:22 +0800",
              "changedTime":"2013-12-10 21:18:29 +0800",
              "createdBy":0,
              "name": "",
              "description": "",
              "address": "",
              "image": null,
              "imageId": null,
              "telephone1": "",
              "telephone2": "",
              "businessHourFrom": 600,
              "businessHourTo": 1380,
              "trafficInfo": null,
              "enabled": true
            });

            showEditor();
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

      var getTile = function(i,s){
        var sb = ['<div class="tile"><div data-idx="'+i+'" style="background-image:url(/m-console'+(s.image||{}).imageLink+')">'];
        sb.push('<div>'+s.name+'</div>');
        sb.push('</div></div>');
        return sb.join('');
      };

      var loadmask = $('#page').mask({loadingText:'loading'});
      $.ajax('{prefix}/tenant/{tenant}/store')
      .done(function(data){
          memoryStorage['stores'] = data;

          $('#stores>div').empty().append(memoryStorage['stores'].map(function(s,i){
            return getTile(i,s);
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