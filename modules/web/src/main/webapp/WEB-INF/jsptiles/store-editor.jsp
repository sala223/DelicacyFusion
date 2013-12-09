<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>

<div id="edit" class="above-loadmask">
  <div>
    <div class="row-layout clearboth">
      <div>
        <div class="form-group">
          <input id="val-name" type="text" class="form-control" placeholder="Enter Name" data-channel="val(this.name)" tabindex="1" />
          <label for="val-name" class="control-label" data-i19="def">store_name</label>
        </div>

        <div class="form-group">
          <input id="val-code" type="text" class="form-control" placeholder="Enter Code" data-channel="val(this.code)" />
          <label for="val-code" class="control-label" data-i19="def">store_code</label>
        </div>

        <div class="form-group">
          <input type="text" id="val-desc" class="form-control" placeholder="Enter Description" data-channel="val(this.description)" tabindex="2"/>
          <label for="val-desc" class="control-label" data-i19="def">store_desc</label>
        </div>
      </div>
      <div class="storeimage">
        <div></div>
      </div>
    </div>

    <div class="form-group clearboth">
      <div class="city-input clearboth">
        <input type="text" class="form-control col-sm-4" id="input-address1" placeholder="Province,AR,MU" data-i19="def" tabindex="3" />
        <input type="text" class="form-control col-sm-4" id="input-address2" placeholder="city" data-i19="def" tabindex="4" />
        <input type="text" class="form-control col-sm-4" id="input-address3" placeholder="district" data-i19="def" tabindex="5" />
      </div>
      <input type="text" class="form-control" id="input-address4" placeholder="street address" data-i19="def" tabindex="6" />
      <label for="input-address" class="control-label" data-i19="def">store_address</label>
    </div>

    <div class="form-group telephones clearboth">
      <div class="clearboth">
        <input type="text" class="form-control col-sm-6" placeholder="Enter Tel 1" data-i19="def" tabindex="7" />
        <input type="text" class="form-control col-sm-6" placeholder="Enter Tel 2" data-i19="def" tabindex="8" />
      </div>
      <label for="val-tel1" class="control-label" data-i19="def">telephone_1</label>
    </div>

    <div class="form-group">
      <div id="daytime-selector">
        <div></div>
      </div>
      <label for="val-time" class="control-label"><span data-i19="def">business_hours</span><span id="daytime-label"></span></label>
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

<script type="text/javascript">

window.main.push(function(){

  var editmask = undefined;
  window.showStoreDetail = function(data){
      $('#edit')
      .toDataView(data)
      .addClass('transform0');

      editmask = $('#panel').mask({noIndicator:true}); 
      editmask.element.click(function(){
          hideStoreDetail();
      });
  };

  var hideStoreDetail = function(){
      $('#edit')
      .removeClass('transform0');
      
      editmask.dismiss();
  };

  var formatTime=function(i){
    var hh = Math.floor(i*30/60),
        mm = i*30 - hh*60;
    return (hh<10?'0':'')+hh+':'+(mm<10?'0':'')+mm;
  };
  $('#daytime-label').text( formatTime(0) + '-'+formatTime(48) );
  $('#daytime-selector>div').numberDragger({
    markers:[0,48],
    unitCount:49,
    unitText:{"0":'00:00',"24":'12:00',"48":'24:00'},
    changeEvent:function(start,end){
      $('#daytime-label').text( formatTime(start) + '-'+formatTime(end) );
    }
  });

});
</script>