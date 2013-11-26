
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