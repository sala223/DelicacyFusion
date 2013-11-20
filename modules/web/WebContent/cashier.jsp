<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Test - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page" class="showmenu">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div id="tables" class="tilecontainer"></div>
        <div id="edit"></div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){
    	
    	$('#tables').delegate('.tile>div','click',function(ev){
    		var je=$(ev.target);
    		console.log(je[0]);
    		$.ajax('{prefix}/tenant/{tenant}/store/{store}/order/table/'+je.attr('data-id'));
    	});
    	
    	// Loading tables
    	var loadmask = $('#page').mask({loadingText:transStr('loading')});
    	$.when($.ajax('{prefix}/tenant/{tenant}/store/{store}/table'))
    	.then(function(tableData){
    		memoryStorage['tables']=tableData;
    		$('#tables').empty().append(memoryStorage['tables'].map(function(t,i){
    			var str=['<div class="tile"><div data-id="'+t.barCode+'">'];
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