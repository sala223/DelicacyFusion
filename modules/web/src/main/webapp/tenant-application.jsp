<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <title>Tenant Application - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page" class="standalone">
      <div id="panel" class="step1">
        <div class="titlebar" id="titlebar">
          <div class="ltr">
            <span class="logo-text">DelicacyFusion</span>
          </div>
          <div class="rtl">
            <a href="logon.html" data-i19="def">Logon</a>
          </div>
        </div>
        <div class="main" id="main1">
          <div>
            <div class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-12 form-title" data-i19="def">30-Days Free Trial Application</label>
              </div>
              <div class="form-group">
                <label for="input-company" class="col-sm-2 control-label" data-i19="def">TENANT NAME</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-company" placeholder="Mandatory" data-i19="def" data-channel="val(this.tenantName)"/>
                </div>
              </div>
              <div class="form-group">
                <label for="input-adminname" class="col-sm-2 control-label" data-i19="def">ADMIN NAME</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-adminname" placeholder="Mandatory" data-i19="def" data-channel="val(this.adminName)"/>
                </div>
              </div>
              <div class="form-group">
                <label for="input-admin-emailcp" class="col-sm-2 control-label" data-i19="def">ADMIN EMAIL/CP</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-admin-emailcp"
                    placeholder="Mandatory,used for sign-in and receiving initial password" data-i19="def"
                    data-channel="val(this.adminId)" />
                </div>
              </div>

              <div class="form-group">
                <label for="input-address" class="col-sm-2 control-label" data-i19="def">TENANT ADDRESS</label>
                <div class="col-sm-10">
                  <div class="multi-input-group">
                    <input type="text" class="form-control col-sm-4" id="input-address1" placeholder="Province,AR,MU" data-i19="def" data-channel="val(this.address1)" />
                    <input type="text" class="form-control col-sm-4" id="input-address2" placeholder="city" data-i19="def" data-channel="val(this.address2)" />
                    <input type="text" class="form-control col-sm-4" id="input-address3" placeholder="district" data-i19="def" data-channel="val(this.address3)" />
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label for="input-street-addr" class="col-sm-2 control-label" data-i19="def"></label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="input-street-addr" placeholder="street address" data-i19="def"data-channel="val(this.streetAddr)" />
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
                  <button type="button" id="btn-submit" class="btn btn-primary" data-i19="def">submit</button>
                </div>
              </div>
            </div>
          </div>
        </div>


        <div class="main" id="main2">
          <div>
            <div class="form-horizontal">

              <div class="form-group">
                <label id="label-status" class="col-sm-12 form-title"></label>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label" data-i19="def">Progress</label>
                <div class="col-sm-10">
                  <div class="progress progress-striped">
                    <div class="progress-bar" id="progressbar"></div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label" data-i19="def">TENANT ADDRESS</label>
                <div class="col-sm-10">
                  <div class="form-control" data-channel="text([this.address1,this.address2,this.address3,this.streetAddr].join(' '))"></div>
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label" data-i19="def">ADMIN NAME</label>
                <div class="col-sm-10">
                  <div class="form-control" data-channel="text(this.adminName)"></div>
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label" data-i19="def">ADMIN EMAIL/CP</label>
                <div class="col-sm-10">
                  <div class="form-control" data-channel="text(this.adminId)"></div>
                </div>
              </div>

              <div class="form-group">
                <label class="col-sm-2 control-label" data-i19="def">Password</label>
                <div class="col-sm-10">
                  <div class="form-control" id="label-password-msg" data-i19="def"></div>
                </div>
              </div>

            </div>
          </div>
        </div>
        
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

      $('#main1').toDataView({
        tenantName:'望湘园上海餐饮有限公司 ',
        adminName:'张三',
        adminId:'san.zhang@gmail.com',
        address1:'上海市',
        address2:'',
        address3:'浦东新区',
        streetAddr:'世纪大道88号54楼'
      });


      $('#btn-submit').click(function(){
        var data = $('#main1').toViewData();
        $('#main2').toDataView(data);
        $('#label-status').iText('Creating',data.tenantName);

        $('#panel').addClass('step2');

        var progress=0;
        function runProgress(){
          setTimeout(function(){
            progress = Math.min(100,progress+10);

            $('#progressbar').css({width:progress+'%'});
            if(progress<100){
              runProgress();
            }else{
              runComplete();
            }
          },500);
        };
        function runComplete(){
          $('#label-status').iText('Created',data.tenantName);
          $('#label-password-msg').iText('Sent Intial Password');
        };

        runProgress();
      });

      window.parent.setTitle($('#titlebar').html());
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>