<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Cashier - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page" class="showmenu">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div id="tables" class="tilecontainer"></div>
        <div id="edit" class="above-loadmask"></div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

    	$('#tables').delegate('.tile>div','click',function(ev){
    		var je=$(ev.target);

    		var mask = $('#panel').mask({loadingText:transStr('loading_order')});
    		$.ajax('{prefix}/tenant/{tenant}/store/{store}/order/table/'+je.attr('data-id'))
    		.done(function(data){

    			$('#edit').append('<table>'+data.lines.map(function(e,i){
    				var orderline=['<tr data-index="'+i+'" data-code="'+e.itemCode+'">'];
    				orderline.push('<td class="ln">'+e.lineNumber+'</td>');
    				orderline.push('<td class="itemname"><span>'+e.itemName+'</span></td>');
    				orderline.push('<td class="quantity">x<span>'+e.quantity+'</span><span>'+transStr('unit_'+(e.itemUnit||'DISK').toLowerCase())+'</span></td>');
    				orderline.push('<td class="totalBef">'+e.totalPayment.toFixed(2)+'</td>');
    				orderline.push('<td class="totalDis">'+(e.totalPaymentAfterDiscount - e.totalPayment).toFixed(2)+'</td>');
    				orderline.push('</tr>');
    				return orderline.join('');
    			}).join('')+'</table>');

    			mask.clearIndicator();
    			$('#edit').addClass('transform0');	
    		})
    		.fail(function( jqXHR, textStatus, errorThrown ){
    			var errorData = jqXHR.responseJSON;
    			if(errorData.errorCode===100003){
    				mask.complete({text:'no_order_on_table',iconClass:'exclamation-sign'});
    			}else{
    				mask.complete({text:'unknown_error',iconClass:'remove'});
    			}
    		});
    	});

    	// Loading tables
    	var loadmask = $('#panel').mask({loadingText:transStr('loading')});
    	$.when($.ajax('{prefix}/tenant/{tenant}/store/{store}/table'))
    	.then(function(tableData){
    		memoryStorage['tables']=tableData;

    		$('#tables').empty().append(memoryStorage['tables'].map(function(t,i){
    			var str=['<div class="tile"><div data-id="'+t.code+'">'];
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