<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="dev" uri="../../WEB-INF/dev-tags.tld"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta content='user-scalable=0' name='viewport' />
        

        <title>DelicacyFusion Tablet</title>
        <link rel="stylesheet" type="text/css" href="css/main.css" />
    </head>
    <body>
        <div id="page">
            <div id="titlebar"></div>
            <div id="content">
                <div></div>
            </div>
        </div>

        <dev:includeJS src="js/zepto.min.js" />
        <dev:includeJS src="js/dfusion-libs.js" />
        <script type="text/javascript">
        $(document).ready(function(){

            dao.getItems(function(items){
                console.log(items);
                $('#content>div').empty().append( $.map(items,function(item){
                    return [
                    '<div>',
                    '<div style="background-image:url(/m-console'+item.pictureSet[0].imageLink+')">',
                    '<div class="banner">'+ item.name +'</div>',
                    '</div>',
                    '</div>'].join('');
                }).join(''));
            });
            
        });
        </script>
    </body>
</html>