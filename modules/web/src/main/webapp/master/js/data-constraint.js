jQuery.fn.extend({
	attachConstraint:function(){
	    var constraints={
	        integer:function(je,text){
	            if(text===''){
	                text = '0';
	            }

	            return /^-?\d*$/.test(text);
	        },
	        currency:function(je,text){
	            if(text===''){
                    text = '0.00';
                }
	            return /^\d*(\.\d{0,2})?$/.test(text);
	        },
	        defaults:function(){
	            return true;
	        }
	    };

	    this.delegate('*[data-constraint]','keydown',function(ev){
	        var je = $(ev.target),
	            origText = je.val(),
	            key = ev.which,
	            range = je.selection('getPos');

	        //console.log(origText,range,ev.which,ev);

	        if(key>=35&&key<=40){
	            // HOME, END, ARROWS
	            return true;
	        }

	        var start='',finish='',captured=false;
	        switch(ev.which){
	        case 8:
	            // BACKSPACE
	            if(range.start!==range.end){
	                start = origText.substring(0,range.start);
	                finish = origText.substring(range.end);
	            }else{
	                start = origText.substring(0,range.start - 1);
	                finish = origText.substring(range.end);
	            }
	            captured = true;
	            break;
	        case 46:
	            // DELETE
                if(range.start!==range.end){
                    start = origText.substring(0,range.start);
                    finish = origText.substring(range.end);
                }else{
                    start = origText.substring(0,range.start);
                    finish = origText.substring(range.end + 1);
                }
                captured = true;
	            break;
	        }

            if(captured){
                return (constraints[je.attr('data-constraint')]||constraints.defaults)(je,start+''+finish);
            }else{
                return true;
            }
	    })
	    .delegate('*[data-constraint]','keypress',function(ev){
	        console.log(String.fromCharCode(ev.charCode),ev);

            var je = $(ev.target),
                origText = je.val(),
                range = je.selection('getPos'),
                start = origText.substring(0,range.start),
                finish = origText.substring(range.end);

            return (constraints[je.attr('data-constraint')]||constraints.defaults)(je,start+String.fromCharCode(ev.charCode)+finish);
	    });
	}
});