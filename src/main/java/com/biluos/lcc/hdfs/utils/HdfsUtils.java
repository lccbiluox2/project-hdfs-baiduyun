package com.biluos.lcc.hdfs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import com.biluos.lcc.hdfs.query.FileOrDirList;

public class HdfsUtils {

	

	//打印某个目录下的所有文件  返回一个list对象
	public List<FileOrDirList> listFile(String myPath) {
		System.setProperty("hadoop.home.dir", "G:\\05-hadoop\\01-2.5\\hadoop-2.5.2\\");
		
		List<FileOrDirList> list = new ArrayList<FileOrDirList>();
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://biluos.com:8020");
			conf.set("yarn.resourcemanager.hostname", "biluos1.com");
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(myPath);
			FileStatus status[] = hdfs.listStatus(path);
			for (int i = 0; i < status.length; i++) {
				
				FileOrDirList fileOrDirList = new FileOrDirList();
				String hdfsFileAbsolutePath = status[i].getPath().toString();
				fileOrDirList.setHdfsFileAbsolutePath(hdfsFileAbsolutePath);
				String hdfsFileRelativePath = hdfsFileAbsolutePath.substring(22, hdfsFileAbsolutePath.length());
				fileOrDirList.setHdfsFileRelativePath(hdfsFileRelativePath);
				fileOrDirList.setFile(status[i].isFile());
				fileOrDirList.setDirectory(status[i].isDirectory());
				
				SimpleDateFormat fm1 = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
				String date = fm1.format(status[i].getModificationTime());
				fileOrDirList.setModificationTime(date );
				
				
				//提取当前目录下的文件和文件夹名称
				String[] sourceStrArray = hdfsFileRelativePath.split("\\/");
				String currentFileName = sourceStrArray[sourceStrArray.length-1];
				fileOrDirList.setCurrentFileName(currentFileName);
				
				//判断  文件 是否包含点    因为linux系统 不包含后缀名
				if(currentFileName.indexOf(".") != -1)  
				{  
			     	//得到文件的后缀名
					String[] fileNameAndEnd = currentFileName.split("\\.");
					//如果是文件夹就没有后缀名   默认为dir  其他的都填写自己的后缀名
					if(status[i].isFile()){
						fileOrDirList.setFileEnd(fileNameAndEnd[1]);
					}
				
				//上面判断没有点 可能是文件夹 也可能是文件    这里判断如果是文件夹
				}else if(status[i].isDirectory()){
					fileOrDirList.setFileEnd("dir");
				//这里判断的是 不知道什么类型的文件
				}else if(status[i].isFile()){
					fileOrDirList.setFileEnd("file");
				}    
				
				list.add(fileOrDirList);
			}
			hdfs.close();
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//创建文件夹  在某个目录下
	public boolean createDir(String path) {
		System.setProperty("hadoop.home.dir", "G:\\05-hadoop\\01-2.5\\hadoop-2.5.2\\");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://biluos.com:8020");
		conf.set("yarn.resourcemanager.hostname", "biluos1.com");
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.mkdirs(new Path(path));
			return true ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

	
	//上传文件到hdfs系统
	public  boolean  putFileToHdfs(String path,String localFilePath,String fileName) {
		// TODO Auto-generated method stub
		System.setProperty("hadoop.home.dir", "G:\\05-hadoop\\01-2.5\\hadoop-2.5.2\\");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://biluos.com:8020");
		FSDataOutputStream outStream  = null;
		FileInputStream inStream =  null;
		String remotePath = "";
		try {
			FileSystem fileSystem =  FileSystem.get(new URI("hdfs://biluos.com:8020"),conf);
			if(path.equals("/")){
				remotePath = "/"+fileName;
				System.out.println("要上传在主目录下为"+remotePath);
			}else{
				remotePath =  path+"/"+fileName;
				System.out.println("要上传在"+remotePath);
			}
			
			
			outStream = fileSystem.create(new Path(remotePath) );
			inStream = new FileInputStream(new File(localFilePath) );
			IOUtils.copyBytes(inStream, outStream,4096,false);
			return true;
			
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return true;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeStream(inStream);
			IOUtils.closeStream(outStream);
		}
		return false;
	}
	
	
	//删除文件 不论是文件还是文件夹
	 public  boolean deleteHDFSFile(String delPath) {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://biluos.com:8020");
        FileSystem hdfs;
        boolean isDeleted = false;
		try {
			hdfs = FileSystem.get(conf);
			Path path = new Path(delPath);
		    isDeleted = hdfs.delete(path, true);
		    hdfs.close();
		        
		} catch (IOException e) {
			e.printStackTrace();
		}
        return isDeleted;
	 }
	    
	
	 
	 /** 
      * 下载 hdfs上的文件  这个下载会自动把hdfs上的文件先下载到本地   然后删除hdfs上的文件
      */  
     public  boolean downloadFile2(String remoteFilePath, String localFilePath) {  
    	 	Configuration conf = new Configuration();
    	 	conf.set("fs.defaultFS", "hdfs://biluos.com:8020"); 
    	 	Path path = new Path(remoteFilePath);  
    	 	FileSystem fileSystem;
			try {
				fileSystem = FileSystem.get(new URI("hdfs://biluos.com:8020"),conf);
				fileSystem.copyToLocalFile(true,path, new Path(localFilePath));  
	            System.out.println("download: from" + remoteFilePath + " to " + localFilePath);  
	            fileSystem.close();  
	            System.out.println("下载成功");
	            return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
    	 	
      }  
     
     //这个下载不会删除hdfs上的文件
     public  boolean downloadFile(String remoteFilePath, String localFilePath) {  
    	 // 云端HDFS上的文件
         String CLOUD_DESC = "hdfs://biluos.com:8020"+remoteFilePath;
         // down到本地的文件
         String LOCAL_SRC = localFilePath+remoteFilePath;
         
         System.out.println("下载成功1"+CLOUD_DESC);
         
         System.out.println("下载成功2"+LOCAL_SRC);
         
         // 获取conf配置
         Configuration conf = new Configuration();
         // 实例化一个文件系统
         FileSystem fs;
		try {
			fs = FileSystem.get(URI.create(CLOUD_DESC), conf);
			  // 读出流
	         FSDataInputStream HDFS_IN = fs.open(new Path(CLOUD_DESC));
	         // 写入流
	         OutputStream OutToLOCAL = new FileOutputStream(LOCAL_SRC);
	         // 将InputStrteam 中的内容通过IOUtils的copyBytes方法复制到OutToLOCAL中
	         IOUtils.copyBytes(HDFS_IN, OutToLOCAL, 1024, true);
	         return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
 	 	return false;
     }  
     
    
    
     
	// 读取hdfs分布式文件系统上的文件并且打印到控制台
	public void read() {

		System.setProperty("hadoop.home.dir", "G:\\05-hadoop\\01-2.5\\hadoop-2.5.2\\");

		String fileUrl = "/user/lcc/test1/input";
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://biluos.com:8020");
		FSDataInputStream inStream = null;
		try {
			FileSystem fileSystem = FileSystem.get(conf);
			inStream = fileSystem.open(new Path(fileUrl));
			IOUtils.copyBytes(inStream, System.out, 4096, false);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(inStream);
		}
	}

}
