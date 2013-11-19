<%@ page language="java" pageEncoding="UTF-8"
  contentType="text/html; charset=UTF-8"%>
<%@ page import="rps.dev.web.DevUtil" %>

<%!
public String menulink(String url,String text, HttpServletRequest req) {
	final String curr = DevUtil.extJsp2Html(req.getServletPath()).substring(1);
    return "<a class=\"item"+(url.equals(curr)?" curr":"")+"\" data-i19=\"def\" href=\""+url+"\">"+text+"</a>";
}
%>

<div id="nav">
  <div class="content">
    <div class="header"></div>
    <div class="group">
      <div class="title" data-i19="def">STORE</div>
      <%=menulink("cashier.html","CASHIER",request)%>
      <%=menulink("#","STORE MANAGEMENT",request)%>
      <%=menulink("#","STAFF MANAGEMENT",request)%>
      <%=menulink("#","TABLE MANAGEMENT",request)%>
      <%=menulink("#","STORE LAYOUT DESIGN",request)%>
    </div>
    <div class="group">
      <div class="title" data-i19="def">DISH</div>
      <%=menulink("items.html","DISH MANAGEMENT",request)%>
      <%=menulink("#","MENU DESIGN",request)%>
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
	
	/*
	var href = /[^/]+$/.exec(window.location.pathname)[0];
	$('#nav .item').each(function(i,e){
		var je=$(e);
		href === je.attr('href') && je.addClass('curr');
	});
	*/
});
</script>