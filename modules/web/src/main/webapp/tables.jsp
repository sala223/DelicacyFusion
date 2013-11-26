<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Tables - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page" class="showmenu">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div class="tiletoolbar">
          <div class="tool"><span class="glyphicon glyphicon-plus"></span><span data-i19="def">new_table</span></div>
          <div class="tool" data-i19="def">new_table 22</div>
        </div>
        <div id="tables" class="tilecontainer"></div>
        <div id="edit" class="above-loadmask">
          <div>

            <div class="form-group">
              <input type="text" id="val-code" class="form-control" placeholder="Enter Code" data-channel="val(this.code)" tabindex="1"/>
              <label for="val-code" class="control-label" data-i19="def">table_code</label>
            </div>

            <div class="form-group">
              <input type="text" id="val-barcode" class="form-control" placeholder="Enter Bar Code" data-channel="val(this.barcode)" tabindex="2"/>
              <label for="val-barcode" class="control-label" data-i19="def">table_barcode</label>
            </div>

            <div class="form-group">
              <input type="text" id="val-cap" class="form-control"
                placeholder="Enter Capacity"
                data-channel="val(this.capacity)"
                data-constraint="integer"
                tabindex="3"/>
              <label for="val-cap" class="control-label" data-i19="def">table_capacity</label>
            </div>


            <div class="form-group">
              <div class="btn-group">
                <button type="button" class="btn btn-primary" data-i19="def" id="btnOK">save</button>
                <button type="button" class="btn btn-default" data-i19="def" id="btnCancel">cancel</button>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

        $('#btnCancel').click(function(){
            $('#edit').removeClass('transform0');
        });

    	$('#tables').delegate('.tile>div','click',function(ev){
    		var je=$(ev.target);

    	    $('#edit')
    	    .toDataView(memoryStorage['tables'][parseInt(je.attr('data-idx'),10)])
    	    .addClass('transform0');
    	});

    	// Loading tables
    	var loadmask = $('#panel').mask({loadingText:transStr('loading')});
    	$.when($.ajax('{prefix}/tenant/{tenant}/store/{store}/table'))
    	.then(function(tableData){
    		memoryStorage['tables']=tableData;

    		$('#tables').empty().append(memoryStorage['tables'].map(function(t,i){
    			var str=['<div class="tile"><div data-idx="'+i+'">'];
    			str.push('<div class="code">'+t.code+'</div>');
    			str.push('<div class="cap"><span class="glyphicon glyphicon-user"></span><span>'+t.capacity+'</span></div>');
    			str.push('</div></div>');
                return str.join('');
            }).join(''));

    		loadmask.dismiss();
    	},function(){
    		loadmask.complete({text:transStr('failure'),iconClass:'remove'});
    	});
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>