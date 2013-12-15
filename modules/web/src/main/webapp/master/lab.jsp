<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
html,body {
    padding:0;
    margin:0;
}

.container {
    
    position:absolute;
    top:0;bottom:0;left:0;right:0;

    display:-moz-box;
    -moz-box-align:stretch;
    -moz-box-orient:vertical;
}

.container > div {
    -moz-box-flex:1;
    background-color:red;
}

.container > div:last-child {
    background-color:blue;
}

</style>
</head>
<body>
<div class="container">
    <div></div>
    <div></div>
</div>
</body>
</html>