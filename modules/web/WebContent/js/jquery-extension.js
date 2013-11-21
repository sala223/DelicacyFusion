var emptyFn=function(){};
RegExp.quote = function(str) {
    return (str+'').replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
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
					return '<div class="img" style="background-image:url('+e.imageLink+')"></div>';
				}).join(''));
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
				console.log('//To be implemented');
			}
		}
	}
};

jQuery.fn.extend({
	toDataView:function(data){
		$('*[data-channel]',this).each(function(i,e){
			var je = $(e),dc = /^(\w+)\((\w*)\)$/.exec(je.attr('data-channel'));
			if(dc===null){
				return;
			}

			var fn = je[dc[1]];
			if(typeof(fn)==='function'){
				fn.call(je,data[dc[2]]);
			}else{
				_jQuery_static_members.bindData.setter[dc[1]].call(je,data[dc[2]]);
			}
		});

		return this;
	},
	toViewData:function(){
		var data={};
		$('*[data-channel]',this).each(function(i,e){
			var je = $(e),dc = /^(\w+)\((\w*)\)$/.exec(je.attr('data-channel'));
			if(dc===null){
				return;
			}

			var fn = je[dc[1]],v;
			if(typeof(fn)==='function'){
				v = (fn.call(je));
			}else{
				v = _jQuery_static_members.bindData.getter[dc[1]].call(je);
			}

			data[dc[2]] = v;
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
			maskElement:loadmask[0],
			complete:function(cfg){
				var that=this;

				cfg=$.extend({
					text:'Completed',
					iconClass:'ok',
					timeout:800,
					fn:emptyFn,
					dismissFn:emptyFn
				},cfg);

				$('.inner>div>div',loadmask)
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
		var dropTarget = this;

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
			console.log('dropped');

			var preview = dropTarget.removeClass('dragover'),
				file = ev.originalEvent.dataTransfer.files[0],
			    reader = new FileReader();

			$(reader).on('load',function (event) {
				//event.target.result.replace(/^data:([\w-\.]+\/[\w-\.]+)?;base64,/,''));
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
	}
});