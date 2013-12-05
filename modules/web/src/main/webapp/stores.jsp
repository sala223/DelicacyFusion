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
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div class="titlebar" id="titlebar">
        Stores
        </div>
        <div id="stores" class="tilecontainer"><div class="clearboth"></div></div>
        <div id="edit" class="above-loadmask">
          <div>
            <div class="form-group">
              <input id="val-name" type="text" class="form-control" placeholder="Enter Name" data-channel="val(this.name)" tabindex="1" />
              <label for="val-name" class="control-label" data-i19="def">store_name</label>
            </div>
            
            <div class="form-group">
              <input id="val-code" type="text" class="form-control" placeholder="Enter Code" data-channel="val(this.code)" tabindex="2" />
              <label for="val-code" class="control-label" data-i19="def">store_code</label>
            </div>

            <div class="form-group">
              <input type="text" id="val-desc" class="form-control" placeholder="Enter Description" data-channel="val(this.description)" tabindex="3"/>
              <label for="val-desc" class="control-label" data-i19="def">store_desc</label>
            </div>

            <div class="form-group">
              <input type="text" id="val-addr" class="form-control" placeholder="Enter Address" data-channel="val(this.address)" tabindex="3"/>
              <label for="val-addr" class="control-label" data-i19="def">store_address</label>
            </div>

            <div class="form-group">
              <input type="text" id="val-tel1" class="form-control" placeholder="Enter Tel 1" data-channel="val(this.telephone1)" tabindex="4"/>
              <label for="val-tel1" class="control-label" data-i19="def">telephone_1</label>
            </div>

            <div class="form-group">
              <input type="text" id="val-tel2" class="form-control" placeholder="Enter Tel 2" data-channel="val(this.telephone2)" tabindex="5"/>
              <label for="val-tel2" class="control-label" data-i19="def">telephone_2</label>
            </div>

            <div class="form-group">
              <input type="text" id="val-time" class="form-control" placeholder="Enter Business Time" data-channel="val(this.businessHourFrom+' - '+this.businessHourTo)" tabindex="5"/>
              <label for="val-time" class="control-label" data-i19="def">business_hours</label>
            </div>

            <div class="form-group">
              <div class="btn-group">
                <button type="button" class="btn btn-primary" data-i19="def" id="btnOK">save</button>
                <%--
                <button type="button" class="btn btn-default" data-i19="def" id="btnCancel">cancel</button>
                --%>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

      var editmask = undefined;
      var showStoreDetail = function(idx,data){
          $('#edit')
          .data('editingIdx',idx)
          .toDataView(data)
          .addClass('transform0');

          editmask = $('#panel').mask({noIndicator:true}); 
          editmask.element.click(function(){
              hideStoreDetail();
          });
      };

      var hideStoreDetail = function(){
          $('#edit')
          .removeData('editingIdx')
          .removeClass('transform0');
          
          editmask.dismiss();
      };

      $('#stores').delegate('.tile > div','click',function(ev){
          var dataIdx = parseInt($(ev.currentTarget).attr('data-idx'),10);
          if(isNaN(dataIdx)){
              showStoreDetail(dataIdx,memoryStorage['stores'][dataIdx]);
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