package com.biluos.lcc.hdfs.query;

/*
 * 用来存储 网盘显示文件列表的item对象
 * 
 */
public class FileOrDirList extends DomainBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hdfsFileAbsolutePath ;//存储格式如下 hdfs://biluos.com:8020/user/lcc/test1
	private String hdfsFileRelativePath ;//存储格式如下 /user/lcc/test1
	private String currentFileName ;//存储格式如下 test1
	private String fileEnd ;//文件的后缀名
	private boolean isFile ;//
	private boolean isDirectory ;//
	private String  modificationTime ;//文件修改时间
	
	
	
	public String getFileEnd() {
		return fileEnd;
	}
	public void setFileEnd(String fileEnd) {
		this.fileEnd = fileEnd;
	}
	public String getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(String modificationTime) {
		this.modificationTime = modificationTime;
	}
	public String getCurrentFileName() {
		return currentFileName;
	}
	public void setCurrentFileName(String currentFileName) {
		this.currentFileName = currentFileName;
	}
	public String getHdfsFileAbsolutePath() {
		return hdfsFileAbsolutePath;
	}
	public void setHdfsFileAbsolutePath(String hdfsFileAbsolutePath) {
		this.hdfsFileAbsolutePath = hdfsFileAbsolutePath;
	}
	public String getHdfsFileRelativePath() {
		return hdfsFileRelativePath;
	}
	public void setHdfsFileRelativePath(String hdfsFileRelativePath) {
		this.hdfsFileRelativePath = hdfsFileRelativePath;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	
	
	
}
