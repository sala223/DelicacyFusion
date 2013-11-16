var emptyFn=function(){};

function Polly(context){

    var funcSign = {}, defaultFn,
        argv = function(){
            var ai, argt = [], argv = [];
            for( ai = 0; ai < arguments.length; ai++ ) {
                argt.push(typeof arguments[ai]);
                argv.push(arguments[ai]);
            }

            return {types:argt,args:argv};
        };

	return {
		define:function(){
            var v = argv.apply(null,arguments), sg;

            if( v.types.pop()!=='function'
            	|| v.types.some(function(e){ return e!=='string'; }) ){
                throw 'when(string... , function)';
            }

            v.args.pop();
			sg = v.args.join(',');
            if(funcSign.hasOwnProperty(sg)){
	            throw 'fn('+sg+') already defined';
            }

            funcSign[sg] = arguments[arguments.length - 1];
			return this;
		},
		fallback:function(defFn){
            defaultFn = defFn;
    	    return this;
		},
		end:function(dynamic){
            var fn=function(){
                var v = argv.apply(null,arguments),
                	thisContext=context||this;

                v.args.push({
                    invoke:function(){return fn.apply(thisContext,arguments);}
                });

                return (funcSign[v.types.join(',')]||defaultFn).apply(thisContext,v.args);
            };

            return fn;
		}
	};
};

function transPage(){
	if( ($('html').attr('lang')||'en')==='en'){
		return;
	}

	var i19=window.i19||{},handler={
		SELECT:function(e){
			var je=$(e);
			$('option',je).each(function(oi,oe){
				handler.DEFAULT(oe);
			});
		},
		DEFAULT:function(e){
			var je=$(e);
			je.text(i19[je.text()]);
		}
	};

	$('*[data-i19]').each(function(i,e){
		(handler[e.tagName]||handler.DEFAULT)(e);
	});
}

function transStr(key){
	var i19=window.i19||{}, tpl = i19[key]||key;

	$(arguments).each(function(i,e){
		i>0 && (tpl = tpl.replace('{'+(i-1)+'}',e));
	});

	return tpl;
}

function maskup(cfg){
	var applyElement=$(cfg.applyElement);

    var str=['<div class="loadmask">'];
    str.push('  <div class="inner">');
    str.push('    <div>');
    str.push('      <div>');
    str.push('        <div class="icon"></div>');
	str.push('        <div class="text">'+(cfg.loadingText||'Loading')+'</div>');
    str.push('      </div>');
    str.push('    </div>');
	str.push('  </div>');
	str.push('</div>');

	var loadmask = $(str.join('')).appendTo(applyElement);

	return {
		complete:function(cfg){
			var that=this;
			cfg=$.extend({
				text:'Completed',
				timeout:800,
				fn:emptyFn
			},cfg);

			$('.inner>div>div',loadmask)
				.animate({opacity:0},200,function(){
					$('.icon',loadmask).addClass('complete noanimation');
					$('.text',loadmask).text(cfg.text);
				})
				.animate({opacity:1},200)
				.delay(cfg.timeout,'fx')
				.promise('fx').done(function(){
					that.dismiss();
				});
		},
		dismiss:function(fn){
			loadmask.animate({opacity:0},400,function(){
				(fn||emptyFn).apply(this);
				$(this).remove();
			});
		}
	};
}
