<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ page import="rps.dev.web.DevUtil" %>

<div id="nav">
  <div class="content">
    <div class="header"></div>
    <div class="group">
      <div class="title" data-i19="def">STORE</div>
      <div class="item" data-i19="def">STORE MANAGEMENT</div>
      <div class="item" data-i19="def">STAFF MANAGEMENT</div>
      <div class="item" data-i19="def">TABLE MANAGEMENT</div>
      <div class="item" data-i19="def">STORE LAYOUT DESIGN</div>
    </div>
    <div class="group">
      <div class="title" data-i19="def">DISH</div>
      <div class="item" data-i19="def">DISH MANAGEMENT</div>
      <div class="item" data-i19="def">MENU DESIGN</div>
    </div>
  </div>

  <a href="logon.html" style="display:none;"></a>
</div>
<div id="nav-shower">
  <span class="glyphicon glyphicon-chevron-right"></span>
</div>

<script type="text/javascript">
window.main.push(function(){
	var transitionenabled=true;

	$('#nav').on('transitionend',function(){
		transitionenabled = false;
	});

	var showDelay = 0;
	$('#nav-shower').mouseover(function(ev){
		showDelay = setTimeout(function(){
			transitionenabled = true;
	 	    $('#page').addClass('showmenu');
		},300);
	})
	.mouseout(function(){
		clearTimeout(showDelay);
	});

	$('#panel').mouseover(function(){
		if(!transitionenabled){
	        $('#page').removeClass('showmenu');
		}
	})
	.click(function(){
        $('#page').removeClass('showmenu');
	});
});
</script>