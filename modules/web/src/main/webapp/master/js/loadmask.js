/**
 * Loading Mask
 */
jQuery.fn.extend({
	mask:function(config){
		var applyElement=this;
		var cfg = $.extend({
				loadingText:'Loading',
				noIndicator:false
			},config);

	    var str=['<div class="loadmask">'];

        if(!cfg.noIndicator){
	        str.push('  <div><div>');
	        str.push('    <div class="icon"><span class="glyphicon glyphicon-refresh"></span></div>');
		    str.push('    <div class="text">'+(transStr(cfg.loadingText))+'</div>');
	        str.push('  </div></div>');
		    str.push('</div>');
	    }

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
				return this;
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
