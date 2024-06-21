package com.biluos.lcc.myblog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biluos.lcc.hdfs.query.FileOrDirList;
import com.biluos.lcc.hdfs.utils.HdfsUtils;
import com.biluos.lcc.hdfs.utils.UploadImgToPointDir;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("indexController")
public class IndexController {

	
	@RequestMapping("/toIndex")
	public String toIndex(Model model,HttpServletRequest req,HttpServletResponse res){
		
		List<FileOrDirList>  list = new HdfsUtils().listFile("/");
		System.out.println(list.toString());
		model.addAttribute("list", list);
		return "wangpan/index";
	}
	
	
	//显示某个文件夹下面的所有文件包括文件夹
	@RequestMapping("/showDir")
	public String showDir(String path,Model model,HttpServletRequest req,HttpServletResponse res){
		List<FileOrDirList>  list = new HdfsUtils().listFile(path);

		res.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = JSONArray.fromObject(list).toString();
		out.print(s);
		out.close();
	    
		return null;
	
	}
	
	//添加文件夹 在指定的文件夹下添加文件
	@RequestMapping("/addDir")
	public String addDir(String path,String dirName,Model model,HttpServletRequest req,HttpServletResponse res){
		String Path = "";
		if(path.equals("/")){
			Path = "/"+dirName;
			System.out.println("要建立的文件夹在主目录下为"+Path);
		}else{
			Path =  path+"/"+dirName;
			System.out.println("要建立的文件夹为"+Path);
		}
		
 		boolean flag = new HdfsUtils().createDir(Path);
 		
 		if(flag){
	    	List<String> u = new ArrayList<String>() ;
			PrintWriter out = null;
			try {
				out = res.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag){
				u.add("1");//登录成功
			}else{
				u.add("0");//登录失败
			}
			String s = JSONArray.fromObject(u).toString();
			out.print(s);
			out.close();
	    }
 		
		return null;
	
	}
	
	
	@RequestMapping("/upLoad")
	public String upLoad(String path,Model model,HttpServletRequest req,HttpServletResponse res){
		
		System.out.println(path);

		return "wangpan/upload";
	}
	
	@RequestMapping("/upLoadReal")
	public String upLoadReal(Model model,HttpServletRequest req,HttpServletResponse res){
		
		String path = req.getParameter("path");
		
		
		
		// 上传文件到服务器了
		String uploadfileDir = "/upload/temp";
		String newFileName = UploadImgToPointDir.getDate();
		String fileNameString = UploadImgToPointDir.uploadImgToPointDir2(req, uploadfileDir, newFileName);
		
		System.out.println("真正去上传1"+path);
		System.out.println("真正去上传2"+fileNameString);
	
		String pathDir =  "/upload" ;
		String webPath = req.getSession().getServletContext().getRealPath(pathDir);
		System.out.println("真正去上传3"+webPath);
		webPath = webPath+"/temp/"+fileNameString;
		System.out.println("真正去上传4"+webPath);
		
		boolean flag = new HdfsUtils().putFileToHdfs(path,webPath,fileNameString);
		
		return "wangpan/upload";
	}
	
	
	
	//删除文件  或者文件夹
	@RequestMapping("/delFile")
	public String delFile(String path,String localFilePath,String dirName,Model model,HttpServletRequest req,HttpServletResponse res){
		
		
		
		boolean flag = new HdfsUtils().deleteHDFSFile(path);
		if(flag){
	    	List<String> u = new ArrayList<String>() ;
			PrintWriter out = null;
			try {
				out = res.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag){
				u.add("1");//登录成功
			}else{
				u.add("0");//登录失败
			}
			String s = JSONArray.fromObject(u).toString();
			out.print(s);
			out.close();
	    }
 		
		return null;
	
	}
	
	//删除文件  或者文件夹
	@RequestMapping("/downloadFile")
	public String downloadFile(String path,Model model,HttpServletRequest req,HttpServletResponse res){
		
		System.out.println("path=============>"+path);
		
		String localFilePath="C:/Users/Administrator/Desktop";
		boolean flag = new HdfsUtils().downloadFile(path,localFilePath);
		if(flag){
	    	List<String> u = new ArrayList<String>() ;
			PrintWriter out = null;
			try {
				out = res.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag){
				u.add("1");//登录成功
			}else{
				u.add("0");//登录失败
			}
			String s = JSONArray.fromObject(u).toString();
			out.print(s);
			out.close();
	    }
 		
		return null;
	
	}
	
	
}
