<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="/css/wangpan/theme.css" />
		
		<script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>	
		
<script>
function xinjian(){
	$(".form").show();
}

function xinjian2(){
	$(".form").hide();
}

//文档加载完成就去读取/目录
$(function(){ 
	 showDir("/");
}); 

function showDir(path){
	
	$(".path").val(path);
	
	$.ajax( {  
      url:'http://localhost:8080/indexController/showDir',
      data:{
		"path":path
	  },  
     type:'post',  
     cache:false,  
     dataType:'json',
	 xhrFields: {
        withCredentials: true
      },	 
     success:function(data) {  
    	 showData(data);
      },  
      error : function() {   
           alert("异常！");  
      }  
	}); 
	
}

function showData(data){

	 var mytable = '';
	 mytable = mytable+"<table>";
	 mytable = mytable+"<tr>";
	 mytable = mytable+"<td class=\"folder_img\" ></td>";
	 mytable = mytable+"<td class=\"folder_name\" >文件</td>";
	 mytable = mytable+"<td>操作</td>";
	 mytable = mytable+"<td>修改时间</td>";
	 mytable = mytable+"</tr>";
	
	 for(var i=0;i<data.length;i++){
		 
		 mytable = mytable+"<tr>";
		 mytable = mytable+"<td class=\"folder_img\" >";

		 if(data[i].fileEnd == "dir" ){
			 mytable = mytable+"<img src=\"/img/wangpan/foler.png\" /> ";
		 }else if(data[i].fileEnd == "xml" ){
			 mytable = mytable+"<img src=\"/img/wangpan/xml.png\" /> ";
		 }else if(data[i].fileEnd == "file" ){
			 mytable = mytable+"<img src=\"/img/wangpan/file.png\" /> ";
		 }else if(data[i].fileEnd == "bmp" || data[i].fileEnd == "gif" || data[i].fileEnd == "jpg" || data[i].fileEnd == "pic" || data[i].fileEnd == "png" || data[i].fileEnd == "tif" ){
			 mytable = mytable+"<img src=\"/img/wangpan/img.png\" /> ";
		 }else if(data[i].fileEnd == "wav" || data[i].fileEnd == "aif" || data[i].fileEnd == "au" || data[i].fileEnd == "mp3" || data[i].fileEnd == "ram"      ){
			 mytable = mytable+"<img src=\"/img/wangpan/music.png\" /> ";
		 }else if(data[i].fileEnd == "doc" || data[i].fileEnd == "docx"  ){
			 mytable = mytable+"<img src=\"/img/wangpan/doc.png\" /> ";
		 }else if(data[i].fileEnd == "pdf" ){
			 mytable = mytable+"<img src=\"/img/wangpan/pdf.png\" /> ";
		 }else if(data[i].fileEnd == "rar" || data[i].fileEnd == "zip" ){
			 mytable = mytable+"<img src=\"/img/wangpan/rar.png\" /> ";
		 }else if(data[i].fileEnd == "txt" ){
			 mytable = mytable+"<img src=\"/img/wangpan/txt.png\" /> ";
		 }else if(data[i].fileEnd == "avi" || data[i].fileEnd == "rmvb"  || data[i].fileEnd == "rm" || data[i].fileEnd == "asf" || data[i].fileEnd == "divx" || data[i].fileEnd == "mpg" || data[i].fileEnd == "mpeg" || data[i].fileEnd == "mpe" || data[i].fileEnd == "wmv" || data[i].fileEnd == "mp4" || data[i].fileEnd == "mkv" || data[i].fileEnd == "vob"  ){
			 mytable = mytable+"<img src=\"/img/wangpan/video.png\" /> ";
		 }else if(data[i].fileEnd == "xls" ){
			 mytable = mytable+"<img src=\"/img/wangpan/xls.png\" /> ";
		 }else if(data[i].fileEnd == "ppt" ){
			 mytable = mytable+"<img src=\"/img/wangpan/ppt.png\" /> ";
		 }else{
			 mytable = mytable+"<img src=\"/img/wangpan/file.png\" /> ";
		 }
		
		 mytable = mytable+"</td>";
		 mytable = mytable+"<td class=\"folder_name\" >";
		 mytable = mytable+"<a href=\"javascript:void(0);\"  onclick=\"showDir('"+data[i].hdfsFileRelativePath+"')\" > "+data[i].currentFileName+" </a>";
		 mytable = mytable+"</td>";
		 mytable = mytable+"<td>";
		 mytable = mytable+"<a href=\"\" >分享</a>";
		 mytable = mytable+"<a href=\"javascript:void(0);\" onclick=\"downloadFile('"+data[i].hdfsFileRelativePath+"')\" >下载</a>";
		 mytable = mytable+"<a href=\"\" >重命名</a>";
		 mytable = mytable+"<a href=\"javascript:void(0);\" onclick=\"delFile('"+data[i].hdfsFileRelativePath+"')\" >删除</a>";
		 mytable = mytable+"</td>";
		 mytable = mytable+"<td>";
		 mytable = mytable+data[i].modificationTime;
		 mytable = mytable+"</td>";
		 mytable = mytable+"</tr>";
		 
	 }
	 mytable = mytable+"<table>";
	 $(".show_item").html(mytable);
	
	
}

function newDir(){

	var path = $(".path").val();
	var dirName = $(".form_text").val();

	$.ajax( {  
      url:'http://localhost:8080/indexController/addDir',
      data:{
		"path":path,
		"dirName":dirName
	  },  
     type:'post',  
     cache:false,  
     dataType:'json',
	 xhrFields: {
        withCredentials: true
      },	 
     success:function(data) {  
		if(data ==  1){
			var path = $(".path").val();
			showDir(path)
		}
      },  
      error : function() {   
           alert("异常！");  
      }  
	});
	
}

var idInt = null; 
function uploadFile() {

	   var path = $(".path").val();
	   var url = '/indexController/upLoad?path='+path;
	   window.open(url,'上传附件','status,top=200,left=200,height=200,width=500');
}



//返回上级目录
function upDir(){
	var path = $(".path").val();
    var strs = new Array();
	if(path == "/" ){
		
	}else{
		strs = path.split("/");
		var upPath = path.substring(0,(path.length-strs[strs.length-1].length)-1 );
		if(upPath==""){
			upPath = "/";
		}
		showDir(upPath)
	}
	
}

function delFile(path){
	
	$.ajax( {  
	      url:'http://localhost:8080/indexController/delFile',
	      data:{
			"path":path
		  },  
	     type:'post',  
	     cache:false,  
	     dataType:'json',
		 xhrFields: {
	        withCredentials: true
	      },	 
	     success:function(data) {  
	    	 if(data ==  1){
	 			var path = $(".path").val();
	 			showDir(path)
	 		}
	      },  
	      error : function() {   
	           alert("异常！");  
	      }  
		}); 
	
}

function downloadFile(path){
	
	$.ajax( {  
	      url:'http://localhost:8080/indexController/downloadFile',
	      data:{
			"path":path
		  },  
	     type:'post',  
	     cache:false,  
	     dataType:'json',
		 xhrFields: {
	        withCredentials: true
	      },	 
	     success:function(data) {  
	    	 if(data ==  1){
	 			alert("已经成功下载到桌面 ");
	 		}
	      },  
	      error : function() {   
	           alert("异常！");  
	      }  
		}); 
	
	
}

</script>
	</head>
	<body>
		<div id="body" >
		
			<div id="top" >
				<div class="top_item" ><img src="/img/wangpan/mywangpan.jpg" /></div>
				<div class="top_item" >碧落网盘</div>
				<div class="top_item" >网盘</div>
			</div>
			<div id="left" >
				<div class="left_item1" >全部文件</div>
				            
				<div class="left_item2" >图片</div>
				<div class="left_item2" >文档</div>
				<div class="left_item2" >视频</div>
				<div class="left_item2" >种子</div>
				<div class="left_item2" >音乐</div>
				<div class="left_item2" >其他</div>
				            
				<div class="left_item1" >我的分享</div>
				<div class="left_item1" >回收站</div>

			</div>
			<div id="content" >
				<div class="caozuo" >
					<input type="text"    class="path" value="/"  />
				</div>
				<div class="caozuo" >
					<div class="caozuo_item" >
						<a href="javascript:void(0);" onclick="uploadFile()" >上传</a>
					</div>
					<div class="caozuo_item" >
						<a href="javascript:void(0);" onclick="xinjian()" >新建文件夹</a>
					</div>
					<div class="caozuo_item" >
						<a href="javascript:void(0);" onclick="upDir()" >上一级目录</a>
					</div>
				</div>
				<div class="show" >
					<div class="form" >
						<input type="text"    class="form_text" placeholder="新建文件夹"  />
						<input type="button"  class="form_bt" value="√"  onclick="newDir()" />
						<input type="button"  class="form_bt" value="X" onclick="xinjian2()" />
					</div>
				</div>
				<div class="show_item" >
					
					
				</div>
			</div>
		</div>
	<body>
</html>