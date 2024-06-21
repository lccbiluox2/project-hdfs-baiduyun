<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>	
<script>
/* (function($){
		$.getUrlParam = function(name){
			var reg= new RegExp("(^|&)"+name +"=([^&]*)(&|$)");
			var r= window.location.search.substr(1).match(reg);
				if (r!=null){
					return unescape(r[2]); 
				}
			return null;
		}
})(jQuery); */

//文档加载完成就去读取/目录
$(function(){ 
	$.getUrlParam = function(name){
		var reg= new RegExp("(^|&)"+name +"=([^&]*)(&|$)");
		var r= window.location.search.substr(1).match(reg);
			if (r!=null){
				return unescape(r[2]); 
			}
		return null;
	}
	
}); 
	
function get(){
	var path =  $.getUrlParam('path');
	//alert(path);
	$(".path").val(path);
	$("#form").submit();
}
</script>
	</head>
	<body>
	<form id="form" action="/indexController/upLoadReal" method="post"  enctype="multipart/form-data">
		<input type="text"   name="path" class="path" value="/"  /><br>
		<input type="file"   name="file" id="uploadFile" /><br>
		<input type="submit"   onclick="get()" value="上传" />
	</form>
	<body>
</html>