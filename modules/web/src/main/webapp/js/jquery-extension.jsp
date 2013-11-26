var emptyFn=function(){};
RegExp.quote = function(str) {
    return (str+'').replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
};

window.getQueryParams=function() {
    var qs = document.location.search.split("+").join(" ");

    var params = {}, tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;

    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])]
            = decodeURIComponent(tokens[2]);
    }

    return params;
};


/**
 * Elements Translate
 */
function transStr(key){
	var i19=window.i19||{}, tpl = i19[key]||key;

	$(arguments).each(function(i,e){
		i>0 && (tpl = tpl.replace('{'+(i-1)+'}',e));
	});

	return tpl;
}

_jQuery_static_translator={
	SELECT:function(e){
		var je=$(e),that=this;
		$('option',je).each(function(oi,oe){
			that.DEFAULT(oe);
		});
	},
	DEFAULT:function(e){
		var je=$(e);
		je.text(i19[je.text()]);
	}
};
jQuery.fn.extend({
	translateText:function(){
		this.each(function(i,e){
			(_jQuery_static_translator[e.tagName]||_jQuery_static_translator.DEFAULT)(e);
		});
	}
});

/**
 * Data Binding
 */
_jQuery_static_members = {
	bindData:{
		getImageView:function(data){
			return [
			    '<div class="img" style="background-image:url(/m-console'+data.imageLink+')" ',
			    'data-imageid="'+data.imageId+'" ',
			    'data-imagelink="'+data.imageLink+'" ',
			    'data-imagewidth="'+data.width+'" ',
			    'data-imageheight="'+data.heigth+'" ',
			    'data-imageformat="'+data.format+'" ',
			    '></div>'].join('');
		},

		setter:{
			dropdown_val:function(v){
				$('a[data-value='+v+']',this).click();
			},
			tag_val:function(vs){
				var inc = $('input',this);

				$('.input-group .input-group-addon',this).empty();
				$('.input-group-addon.full',this).remove();
				$.each(vs,function(i,e){
					inc.val(e).keypress();
				});
			},
			tag_option:function(vs){
				var options = {};
				this.children().each(function(i,e){
					var oe=$(e).removeClass('selected');
					options[oe.attr('data-value')]=oe;
				});
				$.each(vs,function(i,e){
					options[e].addClass('selected');
				});
			},
			imgview_val:function(pics){
				$('.img',this).remove();

				this.append($.map(pics,function(e,i){
					return _jQuery_static_members.bindData.getImageView(e);
				}).join(''));
			},
			money_text:function(v){
				if(typeof(v)==='string'){
					v = parseFloat(v);
				}
				this.text(v.toFixed(2));
			}
		},
		getter:{
			dropdown_val:function(){
				return $('input',this).val();
			},
			tag_val:function(){
				return $('span.v',this).map(function(i,e){
					return $(e).text();
				}).get();
			},
			tag_option:function(){
				return $('*.selected',this).map(function(i,e){
					return $(e).attr('data-value');
				}).get();
			},
			imgview_val:function(){
				return $('.img',this).map(function(i,e){
					var je = $(e);
					return {
						imageId:je.attr('data-imageid'),
						imageLink:je.attr('data-imagelink'),
						format:je.attr('data-format'),
						heigth:parseInt(je.attr('data-imageheight'),10),
						width:parseInt(je.attr('data-imagewidth'),10)
					};
				}).get();
			}
		}
	}
};

jQuery.fn.extend({
	toDataView:function(data){
		$('*[data-channel]',this).each(function(i,e){
			var je = $(e),dc = /^(\w+)\((.*)\)$/.exec(je.attr('data-channel'));
			if(dc===null){
				return;
			}

			var fn = je[dc[1]];
				vl = new Function('data','return '+dc[2]+';').call(data, data);
			if(typeof(fn)==='function'){
				fn.call(je,vl);
			}else{
				_jQuery_static_members.bindData.setter[dc[1]].call(je,vl);
			}
		});

		return this;
	},
	toViewData:function(){
		var data={};
		$('*[data-channel]',this).each(function(i,e){
			var je = $(e),dc = /^(\w+)\((.*)\)$/.exec(je.attr('data-channel'));
			if(dc===null){
				return;
			}

			var fn = je[dc[1]],v;
			if(typeof(fn)==='function'){
				v = (fn.call(je));
			}else{
				v = _jQuery_static_members.bindData.getter[dc[1]].call(je);
			}

			data[dc[2].replace(/^(this|data)\./,'')] = v;
		});

		return data;
	}
});

/**
 * Loading Mask
 */
jQuery.fn.extend({
	mask:function(config){
		var applyElement=this;
		var cfg = $.extend({
				loadingText:'Loading'
			},config);

	    var str=['<div class="loadmask">'];
	    str.push('  <div><div>');
	    str.push('    <div class="icon"><span class="glyphicon glyphicon-refresh"></span></div>');
		str.push('    <div class="text">'+(transStr(cfg.loadingText))+'</div>');
	    str.push('  </div></div>');
		str.push('</div>');

		var loadmask = $(str.join('')).appendTo(applyElement);

		return {
			element:loadmask,
			complete:function(cfg){
				var that=this;

				cfg=$.extend({
					text:'completed',
					iconClass:'ok',
					timeout:800,
					fn:emptyFn,
					dismissFn:emptyFn
				},cfg);

				$('>div>div',loadmask)
				.animate({opacity:0},200,function(){
					$('.icon .glyphicon',loadmask)
					.removeClass('glyphicon-refresh')
					.addClass('glyphicon-'+cfg.iconClass);

					$('.text',loadmask).text(transStr(cfg.text));
				})
				.animate({opacity:1},200)
				.delay(cfg.timeout,'fx')
				.promise('fx').done(function(){
					cfg.fn.apply(loadmask);
					that.dismiss(cfg.dismissFn);
				});
			},
			clearIndicator:function(){
				loadmask.children().remove();
			},
			dismiss:function(fn){
				loadmask.animate({opacity:0},400,function(){
					(fn||emptyFn).apply(this);
					$(this).remove();

					applyElement.removeData('mask');
				});
			}
		};
	}
});

/**
 * Image Dropper
 */
jQuery.fn.extend({
	imageDropper:function(){
		var dropTarget = this, ret = {
			clearImage:function(){
				$('.glyphicon-remove',dropTarget).click();
			}
		};

		dropTarget.append([
        '<span class="glyphicon glyphicon-plus"></span>',
        '<div>',
        '  <div class="ab">',
        '    <span class="glyphicon glyphicon-ok"></span>',
        '    <span class="glyphicon glyphicon-remove"></span>',
        '  </div>',
        '</div>'
		].join(''));

		dropTarget.addClass('image-dropper')
		.children()
		.on('dragover',function(ev){
			$(ev.target).parent().addClass('dragover');
			ev.preventDefault();
		})
		.on('dragleave',function(ev){
			console.log('dragleave');
			$(ev.target).parent().removeClass('dragover');
			ev.preventDefault();
		})
		.on('drop',function(ev){
			var preview = dropTarget.removeClass('dragover'),
				file = ev.originalEvent.dataTransfer.files[0],
			    reader = new FileReader();

			$(reader).on('load',function (event) {
				//event.target.result.replace(/^data:([\w-\.]+\/[\w-\.]+)?;base64,/,''));
				ret.imageContent = event.target.result;
				preview.css('background-image','url('+event.target.result+')');
				preview.addClass('dropped');
			});

			reader.readAsDataURL(file);
			ev.preventDefault();
			return false;
		});

		$('.glyphicon-remove',this).click(function(){
			dropTarget.removeClass('dropped').css('backgroundImage','none');
		});

		return ret;
	}
});