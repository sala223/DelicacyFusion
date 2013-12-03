
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
	INPUT:function(e){
    	var je=$(e), placeholder = je.attr('placeholder');
    	if(placeholder!==undefined && placeholder!==''){
    		je.attr('placeholder',i19[placeholder]||placeholder);
		}
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
