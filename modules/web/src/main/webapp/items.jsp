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
    <div id="page">
      <jsp:include page="WEB-INF/jsptiles/nav.jsp" />
      <div id="panel">
        <div class="tiletoolbar">
        </div>
        <div id="dishes" class="tilecontainer"></div>
        <div id="edit" class="above-loadmask">
          <div>

            <div class="row-layout clearboth">
              <div>
	            <div class="form-group">
	              <div class="input-group">
	                <input id="val-name" type="text" class="form-control" placeholder="Enter Name" data-channel="val(this.name)" tabindex="1" />
	                <label for="val-name" class="control-label" data-i19="def">item_name</label>
	              
	                <div class="input-group-btn" data-channel="dropdown_val(this.type)" >
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
	
	            <div class="form-group" style="display:none;">
	              <input id="val-code" class="form-control" type="text" placeholder="Enter Code" data-channel="val(this.code)" tabindex="2" />
	              <label for="val-code" class="control-label" data-i19="def">item_code</label>
	            </div>
	
	            <div class="form-group">
	              <div class="input-group">
	                <input type="text" id="val-cap" class="form-control"
	                  placeholder="Enter Price"
	                  data-channel="val(this.price)"
	                  data-constraint="currency"
	                  tabindex="3"/>
	                <label for="val-cap" class="control-label" data-i19="def">price</label>
	                <div class="input-group-btn" data-channel="dropdown_val(this.itemUnit)" >
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
	              <input type="text" id="val-desc" class="form-control" placeholder="Enter Description" data-channel="val(this.description)" tabindex="3"/>
	              <label for="val-desc" class="control-label" data-i19="def">item_desc</label>
	            </div>
              
              </div>
              <div class="menuimage">
                <div></div>
              </div>
            </div>

            <div class="form-group" id="tagcloud">
              <div>
                <div class="tagselect form-control clearboth" data-channel="tag_option(this.categories)" ></div>
                <label class="control-label" data-i19="def">item_category</label>
              </div>
            </div>

            <div class="form-group">
              <div class="imageview form-control clearboth" data-channel="imgview_val(this.pictureSet)">
                <div class="imgcreator"></div>
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

        var defaultItem = {
            "code": "",
            "createdTime": "2013-09-15 08:09:21 +0800",
            "changedTime": "2013-11-23 19:44:32 +0800",
            "createdBy": 0,
            "name": "",
            "type": "FOOD",
            "categories": [],
            "pictureSet": [],
            "description": "",
            "price": 1.0,
            "currency": "RMB",
            "itemUnit": "DISK",
            "enabled": true
        };

        var editmask = null;
        var showItemDetails = function(idxStr){
            var data = defaultItem, idx = parseInt(idxStr,10);
            if(!isNaN(idx)){
                data = memoryStorage['items'][idx];
            }

            $('#edit')
            .data('editingIdx',idx)
            .toDataView(data)
            .addClass('transform0');

            editmask = $('#panel').mask({noIndicator:true}); 
            editmask.element.click(function(){
                hideItemDetails();
            });
        };

        var hideItemDetails = function(){
            $('#edit')
            .removeData('editingIdx')
            .removeClass('transform0');
            editmask.dismiss();
        };

        $('#btnCreate').click(function(ev){
            showItemDetails('NaN');
        });

    	$('#btnOK').click(function(){
    		$('.tagedit input').keypress();
    		var edit = $('#edit'),idx=parseInt(edit.data('editingIdx')),
    		    data = edit.toViewData();

    		if(!isNaN(idx)){
    		    data = $.extend({},memoryStorage['items'][idx],data);
    		}else{
    		    var lastCode = "";
    		    $.each(memoryStorage['items'],function(i,itpl){
    		        lastCode = lastCode < itpl.code ? itpl.code : lastCode;
    		    });
    		    if(lastCode===''){
    		        lastCode = "I00000";
    		    }

    		    data = $.extend({},defaultItem,data,{
    		        code:nextValue(lastCode)
    		    });
    		}

    		var savemask = $('#edit').mask({loadingText:'saving'});
    		$.ajax('{prefix}/tenant/{tenant}/itpl/',{
    			type:isNaN(idx)?'POST':'PUT',
    			contentType:'application/json; charset=UTF-8',
    			data:JSON.stringify(data)
    		}).done(function(serverData){
    			savemask.complete({text:'completed',fn:function(){
    			    hideItemDetails();

                    if(!isNaN(idx)){
                        //TODO update tile
                        memoryStorage['items'][idx] = data;
                        $('.text',$('#dishes').children()[idx]).text(data.name);
                    }else{
                        memoryStorage['items'].push(serverData);
                        $('#dishes').append('<div class="tile"><div data-idx="'+(memoryStorage['items'].length-1)+'"><div class="text">'+serverData.name+'</div></div></div>');
                    }
    			}});
    		}).fail(function(){
    			savemask.complete({text:'failure',iconClass:'remove'});
    		});
    	});

    	$('#btnCancel').click(function(){
            hideItemDetails();
    	});

    	// slide in
    	$('#dishes').delegate('.tile > div','click',function(ev){
    	    var dataIdx = parseInt($(ev.currentTarget).attr('data-idx'),10);
    		showItemDetails(dataIdx);
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


    	var imageDropper = $('.imgcreator').imageDropper();
    	$('.imgcreator').delegate('.glyphicon-ok','click',function(ev){
    		
    		$.ajax({
    			type:'PUT',
    			url:'{prefix}/tenant/{tenant}/itpl/'+memoryStorage['items'][$('#edit').data('editingIdx')].code+'/image',
    			data:imageDropper.imageContent.replace(/^data:([\w-\.]+\/[\w-\.]+)?;base64,/,'')
    		}).done(function(data){
    			imageDropper.clearImage();
    			$('.imgcreator').after( _jQuery_static_members.bindData.getImageView(data) );
    			
    		});
    	});

    	$('.tagselect').delegate('>*','click',function(ev){
    		$(ev.target).toggleClass('selected');
    	});

    	var loadmask = $('#page').mask({loadingText:'loading'});
    	$.when($.ajax('{prefix}/tenant/{tenant}/itpl/'),$.ajax('{prefix}/tenant/{tenant}/category'))
    	.then(function(respTpl,respCategory){
    	    var itplData = respTpl[0],categoryData = respCategory[0];
    		memoryStorage['items'] = typeof(itplData)==='object'? itplData : JSON.parse(itplData);
            $('#dishes').empty().append(memoryStorage['items'].map(function(e,i){
                var x = [];
                x.push('<div class="text">'+e.name+'</div>');
                return '<div class="tile"><div data-idx="'+i+'">'+x.join('')+'</div></div>';
            }).join('') +
            '<div class="tile"><div data-idx="NaN"></div></div>');

            $('#tagcloud .tagselect').empty().append(categoryData.map(function(e){
                return '<div data-value="'+e.code+'">'+e.name+'</div>'; 
            }).join(''));
            
            loadmask.dismiss();
    	},function(){
    		loadmask.complete({text:'failure',iconClass:'remove'});
    	});
    });
    </script>
    <jsp:include page="WEB-INF/jsptiles/jsmain.jsp" />
  </body>
</html>