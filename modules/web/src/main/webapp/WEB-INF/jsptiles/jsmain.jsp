<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="../dev-tags.tld"%>

<dev:includeJS src="js/jquery-2.0.3.min.js" />
<dev:includeJS src="js/bootstrap.min.js" />
<dev:includeJS src="js/jquery-extension.js" />

<dev:includeJS src="js/i19.js" />
<script type="text/javascript">
$(document).ready(function() {
	window.queryParams=$.extend({
		tenant:'test',
		store:'s1'
	},getQueryParams());

    $('*[data-i19]').translateText();

    // Register loadingMask for ajax
    $( document ).ajaxSend(function(event, jqXHR, ajaxOptions) {
    	ajaxOptions.mask = $(ajaxOptions.applyElement).mask({
    		loadingText:transStr( ajaxOptions.type==='GET'? 'loading':'saving' )
    	});
    })
    .ajaxError(function(event, jqXHR, ajaxOptions, thrownError){
    	ajaxOptions.mask && ajaxOptions.mask.complete({
    		text:'Error',
    		iconClass:'remove'
    	});
    })
    .ajaxSuccess(function(event, jqXHR, ajaxOptions) {
    	ajaxOptions.mask && ajaxOptions.mask[ajaxOptions.type==='GET'? 'dismiss':'complete']();
    });
    // EOC - Register loadingMask for ajax

    
    $.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
    	options.url = options.url
    	.replace(/{prefix}/,'/m-console/rs')
    	.replace(/{tenant}/,queryParams.tenant)
    	.replace(/{store}/,queryParams.store);

    	var k = "",urlParams=[];
        for(k in options.urlParams){
        	if(options.urlParams.hasOwnProperty(k)){
                urlParams.push(k+'='+encodeURIComponent(options.urlParams[k]));
        	}
        }

        if(urlParams.length>0){
            options.url = options.url+'?'+urlParams.join("&");
        }

    	console.log("ajax",options);
    });

    $.each(window.main||[], function(i,e){
    	e.apply();
    });
});
</script>