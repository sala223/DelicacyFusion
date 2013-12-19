var pkg = function(pkgName){
    if(window[pkgName]===undefined){
        window[pkgName] = {};
    }
    return window[pkgName];
};
