<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>DelicacyFusion Web</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css" />
    <link rel="stylesheet" type="text/css" href="css/bootstrap-extension.css" />
    <link rel="stylesheet" type="text/css" href="css/main.css" />
    <style>
    #edit {
      width:0;
    }
    #edit > div {
      padding:20px;
      width:600px;
    }

    ul.dropdown-menu.align-right li a {
      text-align:right;
    }

    #txt-type,#txt-unit {
      display:inline-block;
      width:30px;
      text-align:right;
    }
    
    .input-group-btn button {
      padding-right:8px;
    }
    
    .btn-group {
      margin-top:10px;
    }
    .btn-group button {
      min-width:100px;
    }
    </style>
  </head>
  <body>
    <div id="page">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div id="dishes" class="tilecontainer"></div>
        <div id="edit">
          <div>
            <div class="form-group">
              <label for="val-name" class="control-label" data-i19="def">item_name</label>
              <div class="input-group">
                <input id="val-name" type="text" class="form-control" placeholder="Enter Name" data-channel="val(name)" />

                <div class="input-group-btn" data-channel="dropdown_val(itemUnit)" >
                  <button id="btn-unit" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span id="txt-unit"></span>
                    <span class="caret"></span>
                  </button>
                  <input type="hidden" id="val-unit" />
                  <ul class="dropdown-menu pull-right align-right" data-val-fld="val-unit" data-txt-fld="txt-unit">
                    <li><a href="javascript:;" data-value="DISH" data-i19="def">unit_dish</a></li>
                    <li><a href="javascript:;" data-value="JIN" data-i19="def">unit_jin</a></li>
                    <li><a href="javascript:;" data-value="KILOGRAM" data-i19="def">unit_kg</a></li>
                    <li><a href="javascript:;" data-value="BOTTLE" data-i19="def">unit_bottle</a></li>
                    <li><a href="javascript:;" data-value="CUP" data-i19="def">unit_cup</a></li>
                  </ul>
                </div>
                
              </div>
            </div>

            <div class="form-group">
              <label for="val-code" class="control-label" data-i19="def">item_code</label>
              <div class="input-group">
                <input id="val-code" class="form-control" type="text" placeholder="Enter Code" data-channel="val(code)" />

                <div class="input-group-btn" data-channel="dropdown_val(type)" >
                  <button id="btn-type" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span id="txt-type"></span>
                    <span class="caret"></span>
                  </button>
                  <input type="hidden" id="val-type" />
                  <ul class="dropdown-menu pull-right align-right" data-val-fld="val-type" data-txt-fld="txt-type">
                    <li><a href="javascript:;" data-value="FOODS" data-i19="def">type_foods</a></li>
                    <li><a href="javascript:;" data-value="GOODS" data-i19="def">type_goods</a></li>
                  </ul>
                </div>
              </div>
            </div>

            <div class="form-group">
              <label for="val-desc" class="control-label" data-i19="def">item_desc</label>
              <textarea id="val-desc" class="form-control" placeholder="Enter Description" data-channel="val(description)" ></textarea>
            </div>

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

            <div class="btn-group">
              <button type="button" class="btn btn-primary" id="btnOK">OK</button>
              <button type="button" class="btn btn-default" id="btnCancel">Cancel</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
    var memoryStorage={};
    function main(){

    	$('#btnOK').click(function(){
    		$('.tagedit input').keypress();
    		var edit = $('#edit'),idx=edit.data('editingIdx');
    		memoryStorage['items'][idx] = $.extend(memoryStorage['items'][idx],edit.toViewData());

    		console.log(JSON.stringify(memoryStorage['items'][idx]));
    	});

    	$('#btnCancel').click(function(){
            $('#edit').animate({width:0});
            $('#dishes').css({"right":0}).removeClass('min');
    	});

    	// slide in
    	$('#dishes').delegate('.tile > div','click',function(ev){
    	    var innerWidth=$('#edit>div').outerWidth(),
    	        dataIdx = parseInt($(ev.currentTarget).attr('data-idx'),10);

    		$('#edit')
    		.data('editingIdx',dataIdx)
    		.toDataView(memoryStorage['items'][dataIdx])
    		.animate({width:innerWidth+'px'},{
    			easing:'easeOutCubic',
    			duration:400
    		}).promise('fx').done(function(){
    			/*
    			$('#dishes')
    			.css({
    				"right":(panelWidth-minbarWidth)+'px'
    			})
    			.addClass('min');
    		    */
    		});
    	});

    	// EOC - slide in

    	// Pop out
    	/*
        $('#dishes').delegate('.tile > div','click',function(ev){
        	var sp = $(ev.currentTarget).parent().position(),
        	    ph = $('#panel').height(),
        	    pw = $('#panel').width(),
        	    origin = {
                    //display:'none',
                    left:(sp.left + 12)+'px',
                    top:(sp.top + 12)+'px',
                    right:(pw - sp.left - 112),
                    bottom:(ph - sp.top - 112)
                };

        	$('#edit').css(origin).data('origin',origin)
        	.show()
        	.animate({
        		left:'18px',
                right:'18px',
                top:'12px',
                bottom:'12px'
        	});

        	$('#dishes').addClass('min');
        });
    	*/

    	/*
    	$('#edit').click(function(ev){
            var me=$(this);

            me.animate(me.data('origin')).animate({'opacity':0})
            .promise().done(function(){
            	me.hide().css({opacity:1});
            	me.removeData('origin');
            	$('#dishes').removeClass('min');
            });
    	});
    	*/
        // EOC - Pop out

        $('.dropdown-menu').delegate('a','click',function(ev){
            var dt = $(ev.delegateTarget),
                ct = $(ev.currentTarget),
                vf = $('#'+dt.attr('data-val-fld')),
                tf = $('#'+dt.attr('data-txt-fld'));
            tf.text(ct.text());
            vf.val(ct.attr('data-value'));
        });
    	
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
        

    	
    	$.ajax('latency/testdata/items.json',{applyElement:'#panel'})
        .done(function(data){
        	memoryStorage['items'] = JSON.parse(data);
            $('#dishes').empty().append(memoryStorage['items'].map(function(e,i){
            	var x = [];
            	x.push('<div class="text">'+e.name+'</div>');
                return '<div class="tile"><div data-idx="'+i+'">'+x.join('')+'</div></div>';
            }).join(''));
        })
        .always(function() {

        });

        //var m = maskup({applyElement:'#page',loadingText:transStr('loading')});
        //window.setTimeout(function(){ m.complete({text:transStr('completed')}); },1200);
    }
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>