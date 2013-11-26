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
