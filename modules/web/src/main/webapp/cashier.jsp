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
    <div id="page">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div class="titlebar">
        </div>
        <div id="tables" class="tilecontainer"></div>
        <div id="edit" class="above-loadmask">
          <div>
		    <table>
		      <thead>
		        <tr class="titleline">
		          <td data-i19="def">store</td>
		          <td data-channel="text(this.storeCode)"></td>
		        </tr>
                <tr class="titleline">
                  <td data-i19="def">table_code</td>
                  <td></td>
                  <td data-i19="def" colspan="2">dinner_person_count</td>
                  <td></td>
                </tr>
                <tr><td colspan="5" class="rowspacing"></td></tr>
		        <tr class="line">
		          <td class="ln" data-i19="def">number</td>
		          <td class="itemname" data-i19="def">item_name</td>
		          <td class="quantity" data-i19="def">quantity</td>
		          <td class="totalBef" data-i19="def">total_amount</td>
		          <td class="totalDis" data-i19="def">total_discount</td>
		        </tr>
		      </thead>
		      <tbody></tbody>
		      <tfoot>
		        <tr><td colspan="5" class="rowspacing"></td></tr>
		        <tr class="totalline">
		          <td colspan="4" data-i19="def">order_total_amount</td>
		          <td data-channel="money_text(this.totalPayment)"></td>
		        </tr>
		        <tr class="totalline">
		          <td colspan="4" data-i19="def">order_total_discount</td>
		          <td data-channel="money_text(data.totalPaymentAfterDiscount-data.totalPayment)"></td>
		        </tr>
		        <tr class="totalline">
		          <td colspan="4" data-i19="def">order_total_payment</td>
		          <td data-channel="money_text(data.totalPaymentAfterDiscount)"></td>
		        </tr>
		      </tfoot>
		    </table>

            <div class="btn-group">
              <button type="button" class="btn btn-primary" data-i19="def" id="btnCash">pay_by_cash</button>
              <button type="button" class="btn btn-default" data-i19="def" id="btnCard">pay_by_card</button>
              <button type="button" class="btn btn-default" data-i19="def" id="btnCancel">cancel</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

        var receiptMask = undefined,
            hideReceiptPanel = function(){
	            $('#edit')
                .removeData('orderId')
	            .removeClass('transform0');

	            (receiptMask||{dismiss:emptyFn}).dismiss();
	            receiptMask = undefined;
	        };

        $('#btnCancel').click(function(){
            hideReceiptPanel();
        });

    	$('#tables').delegate('.tile>div','click',function(ev){
    		var je=$(ev.currentTarget);

    		receiptMask = $('#panel').mask({loadingText:transStr('loading_order')});
    		$.ajax('{prefix}/tenant/{tenant}/store/{store}/order/table/'+je.attr('data-id'))
    		.done(function(data){

    			$('#edit')
    			.data('orderId',data.transactionId)
    			.toDataView(data);

    			$('#edit tbody').empty()
    			.append(data.lines.map(function(e,i){
    				var orderline=[
		                '<tr data-index="'+i+'" data-code="'+e.itemCode+'">'
	    			   ,'<td class="ln">'+e.lineNumber+'</td>'
	    			   ,'<td class="itemname"><span>'+e.itemName+'</span></td>'
	    			   ,'<td class="quantity">x<span>'+e.quantity+transStr('unit_'+(e.itemUnit||'DISK').toLowerCase())+'</span></td>'
	    			   ,'<td class="totalBef">'+e.totalPayment.toFixed(2)+'</td>'
	    			   ,'<td class="totalDis">'+(e.totalPaymentAfterDiscount - e.totalPayment).toFixed(2)+'</td>'
	    			   ,'</tr>'];
    				return orderline.join('');
   			    }).join(''));

    			receiptMask.clearIndicator();
    			setTimeout(function(){
    			    receiptMask.element.click(function(){
	    			    hideReceiptPanel();
	    			});
    			},400);

    			$('#edit').addClass('transform0');	
    		})
    		.fail(function( jqXHR, textStatus, errorThrown ){
    			var errorData = jqXHR.responseJSON;
    			if(errorData.errorCode===100003){
    			    receiptMask.complete({text:'no_order_on_table',iconClass:'exclamation-sign'});
    			}else{
    			    receiptMask.complete({text:'unknown_error',iconClass:'remove'});
    			}
    		});
    	});

    	// Payments
        $('#btnCash').click(function(){
            $.ajax('{prefix}/tenant/{tenant}/store/{store}/payment/bill/cash/'+$('#edit').data('orderId'),{
                type:"POST"
            });
        });

        // Occupation
        var fetchOccupation=function(){
            setTimeout(function(){
                $.ajax('{prefix}/tenant/{tenant}/store/{store}/table/occupation')
                .done(function(data){
                    memoryStorage['occupation'] = data;
                    refreshOccupation();
                })
                .always(function(){
                    fetchOccupation();
                });
            },10000);
        };

        var refreshOccupation = function(){
            $('#panel .tile').removeClass('occupied');
            $.each(memoryStorage['occupation'],function(i,o){
                $.each(o.tables,function(i,t){
                    $('#'+memoryStorage['tablesIndex'][t]).addClass('occupied');
                });
            });
        };

    	// Loading tables
    	var loadmask = $('#panel').mask({loadingText:transStr('loading')});
    	$.when($.ajax('{prefix}/tenant/{tenant}/store/{store}/table'),$.ajax('{prefix}/tenant/{tenant}/store/{store}/table/occupation'))
    	.then(function(tableData,occupData){
    		memoryStorage['tables']=tableData[0];
    		memoryStorage['occupation'] = occupData[0];
    		memoryStorage['tablesIndex']={};

    		$('#tables').empty().append(memoryStorage['tables'].map(function(t,i){
    		    var tileId = 'tile-'+i;
    		    memoryStorage['tablesIndex'][t.code] = tileId;

    			var str=['<div class="tile" id="'+tileId+'"><div data-id="'+t.code+'">'];
    			str.push('<div class="code">'+t.code+'</div>');
    			str.push('<div class="cap"><span class="glyphicon glyphicon-user"></span><span>'+t.capacity+'</span></div>');
    			str.push('</div></div>');
                return str.join('');
            }).join(''));    		

    		loadmask.dismiss();
    		
    		refreshOccupation();
    		fetchOccupation();
    	},function(){
    		loadmask.complete({text:transStr('failure'),iconClass:'remove'});
    	});
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>