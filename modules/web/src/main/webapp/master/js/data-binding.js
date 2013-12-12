
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
			},
			addr_val:function(v){
    			var addrParts = v.split(',');
    			$('input',this).each(function(i,e){
        			$(e).val( addrParts[i]||'' );
    			});
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
			},
			addr_val:function(){
    			return $('input',this).map(function(i,e){
        			return $(e).val();
    			}).get().join(',');
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
