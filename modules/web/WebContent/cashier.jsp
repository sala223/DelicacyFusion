<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Test - DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
    <style>
    </style>
  </head>
  <body>
    <div id="page" class="showmenu">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div id="edit">
          <div class="form-group">
            <div>
              <label for="val-category" class="control-label" data-i19="def">item_category</label>

              <div class="tagedit" data-channel="tag_val(categories)" >
                <div class="input-group">
                  <input type="text" class="form-control" placeholder="Enter Category">
                  <div class="input-group-addon"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
    window.main.push(function(){

        
        $('.tagedit input').keypress(function(ev){
            var ine = $(ev.target),t = ine.val().trim(),cont;
            if((ev.which === 13 || ev.which === undefined) && t!==''){
                if(ine.width()<=200){
                    cont = $('.input-group-addon.full',ine.parents('.tagedit'));

                    if(cont.length==0){
                        cont = $('<div class="input-group-addon full"></div>').appendTo(ine.parents('.tagedit'));
                    }

                }else{
                    cont = $('.input-group-addon',ine.parent());
                }
                cont.prepend($('<span></span>').append($('<span class="v"></span>').text(t)).append('<span class="glyphicon glyphicon-remove"></span>'));
                ine.val('');
            }
        });

        $('.tagedit').delegate('.input-group-addon span.glyphicon-remove','click',function(ev){
            $(ev.target).parent().remove();
        });
    	
    	
    	var mask = $('#panel').mask();
    	
    	setTimeout(function(){
    		mask.complete({
    			iconClass:'remove'
    		});
    	},1000);
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>