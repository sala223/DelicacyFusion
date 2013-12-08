
jQuery.fn.extend({
    numberDragger:function(config){
        var cfg = $.extend({
            unitWidth:0,
            unitCount:25,
            unitText:{"0":'00:00',"12":'12:00',"24":'24:00'},
            markers:1
        },config);


        this.addClass('number-dragger');

        var sb = [], unit = 0, cls,
            width = cfg.unitWidth==0 ? ( (this.width() - 1) / (cfg.unitCount - 1) ) : cfg.unitWidth;

        sb.push('<div class="marker"></div>');
        sb.push('<div class="marker"></div>');
        
        for(;unit<cfg.unitCount;unit++){
            cls = unit==0?' u0':( unit==cfg.unitCount-1?' un':'' );
            sb.push('<div class="unit'+(cfg.unitText[unit]===undefined?'':' t')+cls+'" ');
            sb.push('style="z-index:'+(cfg.unitCount - unit)+';width:'+ width +'px;left:'+(unit*width)+'px">');
            sb.push(cfg.unitText[unit]===undefined?'':('<span>'+cfg.unitText[unit]+'</span>'));
            sb.push('</div>');
        }

        this.append(sb.join(''));

        $('.marker',this)
        .drag('start',function(ev,dragData){
            dragData.targetCSSX = $(dragData.target).position().left;
        })
        .drag(function(ev,dragData){
            var newx = Math.round((dragData.targetCSSX+dragData.deltaX)/width)*width;
            newx = Math.min(Math.max(0,newx), width*(cfg.unitCount-1) );
            $(dragData.target).css('left',newx+'px');
        });
    }
});