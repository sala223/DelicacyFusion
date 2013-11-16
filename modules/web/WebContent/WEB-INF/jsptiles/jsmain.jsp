<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="../dev-tags.tld"%>

<dev:includeJS src="js/jquery-2.0.3.min.js" />
<dev:includeJS src="js/jquery.easing.1.3.js" />
<dev:includeJS src="js/jquery-extension.js" />
<dev:includeJS src="js/bootstrap.min.js" />
<dev:includeJS src="js/common.js" />
<dev:includeJS src="js/i19.js" />
<script type="text/javascript">
$(document).ready(function() {
    transPage();

    // Register loadingMask for ajax
    $( document ).ajaxSend(function(event, jqXHR, ajaxOptions) {
    	ajaxOptions.mask = maskup({
    		applyElement:ajaxOptions.applyElement,
    		loadingText:transStr( ajaxOptions.type==='GET'? 'loading':'saving' )
    	});
    })
    .ajaxComplete(function(event, jqXHR, ajaxOptions) {
    	//TODO Error Or Success
    	ajaxOptions.mask && ajaxOptions.mask[ajaxOptions.type==='GET'? 'dismiss':'complete']();
    });
    // EOC - Register loadingMask for ajax

    (window.main||function(){}).apply();
});
</script>