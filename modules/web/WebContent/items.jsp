<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ch">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>DelicacyFusion Web</title>
    <jsp:include page="WEB-INF/jsptiles/setup.jsp" />
  </head>
  <body>
    <div id="page" class="showmenu">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div id="dishes" class="tilecontainer"></div>
        <div id="edit">
          <div>
            <div class="form-group">
              <div class="input-group">
                <input id="val-name" type="text" class="form-control" placeholder="Enter Name" data-channel="val(name)" tabindex="1" />
                <label for="val-name" class="control-label" data-i19="def">item_name</label>

                <div class="input-group-btn" data-channel="dropdown_val(itemUnit)" >
                  <button id="btn-unit" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span id="txt-unit"></span>
                    <span class="caret"></span>
                  </button>
                  <input type="hidden" id="val-unit" />
                  <ul class="dropdown-menu pull-right align-right" data-val-fld="val-unit" data-txt-fld="txt-unit">
                    <li><a href="javascript:;" data-value="DISK" data-i19="def">unit_disk</a></li>
                    <li><a href="javascript:;" data-value="JIN" data-i19="def">unit_jin</a></li>
                    <li><a href="javascript:;" data-value="KILOGRAM" data-i19="def">unit_kg</a></li>
                    <li><a href="javascript:;" data-value="BOTTLE" data-i19="def">unit_bottle</a></li>
                    <li><a href="javascript:;" data-value="CUP" data-i19="def">unit_cup</a></li>
                  </ul>
                </div>
                
              </div>
            </div>

            <div class="form-group">
              <div class="input-group">
                <input id="val-code" class="form-control" type="text" placeholder="Enter Code" data-channel="val(code)" tabindex="2" />
                <label for="val-code" class="control-label" data-i19="def">item_code</label>

                <div class="input-group-btn" data-channel="dropdown_val(type)" >
                  <button id="btn-type" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <span id="txt-type"></span>
                    <span class="caret"></span>
                  </button>
                  <input type="hidden" id="val-type" />
                  <ul class="dropdown-menu pull-right align-right" data-val-fld="val-type" data-txt-fld="txt-type">
                    <li><a href="javascript:;" data-value="FOOD" data-i19="def">type_foods</a></li>
                    <li><a href="javascript:;" data-value="GOOD" data-i19="def">type_goods</a></li>
                  </ul>
                </div>
              </div>
            </div>

            <div class="form-group">
              <!--<textarea id="val-desc" class="form-control" placeholder="Enter Description" data-channel="val(description)" ></textarea>-->
              <input type="text" id="val-desc" class="form-control" placeholder="Enter Description" data-channel="val(description)" tabindex="3"/>
              <label for="val-desc" class="control-label" data-i19="def">item_desc</label>
            </div>

            <div class="form-group" id="tagcloud">
              <div>
                <div class="tagselect form-control clearboth" data-channel="tag_option(categories)" ></div>
                <label class="control-label" data-i19="def">item_category</label>
              </div>
            </div>

            <div class="form-group">
              <div class="imageview form-control clearboth" data-channel="imgview_val(pictureSet)">
                <div class="imgcreator"></div>
                <div class="img"></div>
              </div>
              <label class="control-label" data-i19="def">item_image</label>
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

    	$('#btnOK').click(function(){
    		$('.tagedit input').keypress();
    		var edit = $('#edit'),idx=edit.data('editingIdx');
    		memoryStorage['items'][idx] = $.extend(memoryStorage['items'][idx],edit.toViewData());

    		var savemask = $('#edit').mask({loadingText:transStr('saving')});
    		$.ajax('{prefix}/tenant/{tenant}/itpl/',{
    			type:'PUT',
    			contentType:'application/json',
    			data:JSON.stringify(memoryStorage['items'][idx])
    		}).done(function(){
    			savemask.complete({text:transStr('completed'),fn:function(){
    				$('#edit').removeClass('transform0');	
    			}});
    			
    		}).fail(function(){
    			savemask.complete({text:transStr('failure'),iconClass:'remove'});
    		});
    	});

    	$('#btnCancel').click(function(){
            $('#edit').removeClass('transform0');
    	});

    	// slide in
    	$('#dishes').delegate('.tile > div','click',function(ev){
    	    var dataIdx = parseInt($(ev.currentTarget).attr('data-idx'),10);

    		$('#edit')
    		.data('editingIdx',dataIdx)
    		.toDataView(memoryStorage['items'][dataIdx])
    		.addClass('transform0');

    	});

    	// EOC - slide in

        $('.dropdown-menu').delegate('a','click',function(ev){
            var dt = $(ev.delegateTarget),
                ct = $(ev.currentTarget),
                vf = $('#'+dt.attr('data-val-fld')),
                tf = $('#'+dt.attr('data-txt-fld'));
            tf.text(ct.text());
            vf.val(ct.attr('data-value'));
        });

    	

    	$('.imgcreator').imageDropper();

    	
    	$('.tagselect').delegate('>*','click',function(ev){
    		$(ev.target).toggleClass('selected');
    	});

    	var loadmask = $('#page').mask({loadingText:transStr('loading')});
    	$.when($.ajax('{prefix}/tenant/{tenant}/itpl/'),$.ajax('{prefix}/tenant/{tenant}/category'))
    	.then(function(respTpl,respCategory){
    	    var itplData = respTpl[0],categoryData = respCategory[0];
    		memoryStorage['items'] = typeof(itplData)==='object'? itplData : JSON.parse(itplData);
            $('#dishes').empty().append(memoryStorage['items'].map(function(e,i){
                var x = [];
                x.push('<div class="text">'+e.name+'</div>');
                return '<div class="tile"><div data-idx="'+i+'">'+x.join('')+'</div></div>';
            }).join(''));

            $('#tagcloud .tagselect').empty().append(categoryData.map(function(e){
                return '<div data-value="'+e.code+'">'+e.name+'</div>'; 
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