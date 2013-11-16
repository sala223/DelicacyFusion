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
			imgview_val:function(pics){
				$('>div.clearboth .img',this).remove();

				$('>div.clearboth',this).append($.map(pics,function(e,i){
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
	},

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