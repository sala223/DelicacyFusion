<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Tenant Application - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page">
      <div id="panel">
        <div class="titlebar"></div>
        <div class="main">
          <div>
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-12 form-title" data-i19="def">30-Days Free Trial Application</label>
              </div>
              <div class="form-group">
                <label for="input-company" class="col-sm-2 control-label" data-i19="def">TENANT NAME</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-company" placeholder="Mandatory" data-i19="def" />
                </div>
              </div>
              <div class="form-group">
                <label for="input-adminname" class="col-sm-2 control-label" data-i19="def">ADMIN NAME</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-adminname" placeholder="Mandatory" data-i19="def" />
                </div>
              </div>
              <div class="form-group">
                <label for="input-admin-emailcp" class="col-sm-2 control-label" data-i19="def">ADMIN EMAIL/CP</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-admin-emailcp" placeholder="Mandatory,used for sign-in and receiving initial password" data-i19="def" />
                </div>
              </div>

              <div class="form-group">
                <label for="input-address" class="col-sm-2 control-label" data-i19="def">TENANT ADDRESS</label>
                <div class="col-sm-10">
                  <div class="multi-input-group">
                    <input type="text" class="form-control col-sm-4" id="input-address1" placeholder="Province,AR,MU" data-i19="def" />
                    <input type="text" class="form-control col-sm-4" id="input-address2" placeholder="city" data-i19="def" />
                    <input type="text" class="form-control col-sm-4" id="input-address3" placeholder="district" data-i19="def" />
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label for="input-street-addr" class="col-sm-2 control-label" data-i19="def"></label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-street-addr" placeholder="street address" data-i19="def" />
                </div>
              </div>

              <div class="form-group">
                <label for="input-CAPTCHA" class="col-sm-2 control-label" data-i19="def">CAPTCHA</label>
                <div class="col-sm-10">
                  <div class="input-group">
                    <input type="text" class="form-control" id="input-CAPTCHA" placeholder="Please enter the CAPTCHA" data-i19="def" />
                    <span class="input-group-addon">@</span>
                  </div>
                </div>
              </div>

              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-primary" data-i19="def">submit</button>
                </div>
              </div>
            </form>
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