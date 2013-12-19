pkg('dao').getItems = function(fn){
    $.ajax({url:'/m-console/rs/tenant/test/itpl/'})
    .done(function(data){
        fn.call(null,data);
    });
};