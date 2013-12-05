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
          <div class="ltr">
            <span class="logo-text">DelicacyFusion</span>
          </div>
          <div class="rtl">
            <a href="tenant-application.html" class="btn-primary" data-i19="def">Trial Application</a>
          </div>
        </div>
        <div class="main">
          <div>
            <div class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-12 form-title" data-i19="def">System Sign-In</label>
              </div>
              <div class="form-group">
                <div class="col-sm-12">
                  <input type="text" class="form-control" id="input-username" placeholder="EMAIL/CP" data-i19="def" />
                </div>
              </div>

              <div class="form-group">
                <div class="col-sm-12">
                  <input type="password" class="form-control" id="input-password" placeholder="Sign-In Password" data-i19="def" />
                </div>
              </div>

              <div class="form-group">
                <div class="col-sm-7">
                  <input type="checkbox" id="chk-autosignin" />
                  <label for="chk-autosignin" data-i19="def" class="slim-label">Auto Sign-In Next Time</label>
                </div>
                <div class="col-sm-5">
                  <a href="#" data-i19="def">Forgot Password?</a>
                </div>
              </div>

              <div class="form-group">
                <div class="col-sm-12">
                  <button type="button" class="btn btn-primary" data-i19="def">Logon</button>
                </div>
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