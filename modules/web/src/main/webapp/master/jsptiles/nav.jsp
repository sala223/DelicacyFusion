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
    <div class="group">
      <!--<div class="title" data-i19="def">STORE</div>-->

<%
    	int menuCount = 0;
    	String value = null;
    	while( (value = request.getParameter("menu"+(menuCount++))) != null ){
      	String[] pair = value.split("\\|");
%>
      <%=menulink(pair[0],pair[1],request)%>
<%	
    	}
%>
    </div>
  </div>

  <a href="landing.html" style="display:none;"></a>
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
	$('#nav-shower').click(function(){
		transitionenabled = true;
        $('#page').addClass('showmenu');
	})
	.mouseover(function(ev){
		showDelay = setTimeout(function(){
			$(ev.target).click();
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