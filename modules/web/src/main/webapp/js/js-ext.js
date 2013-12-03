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

window.nextValue = function(currValue){
    var intValue = parseInt(currValue,10);
    if(typeof(currValue)==='number' || !isNaN(intValue)){
        return currValue + 1;
    }


    var v = currValue.toUpperCase(), i = 0 ,cc, cr = 1,
        vc = [];

    while(i<v.length){
        vc.push(v.charCodeAt(i));
        i++;
    }

    i = v.length - 1;
    while( cr > 0 ){
        cc = vc[i] + cr;
        cr = 0;
        if(cc === 91){
            cc = 65;
            cr = 1;
        } else if(cc === 58) {
            cc = 48
            cr = 1;
        }

        vc[i] = cc;
        i--;
    }

    return vc.map(function(c){
        return String.fromCharCode(c);
    }).join('');
};