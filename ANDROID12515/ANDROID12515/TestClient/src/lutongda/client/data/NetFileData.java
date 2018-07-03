package lutongda.client.data;

public class NetFileData {
   private long fileSize=0;
   private String fileName="$error";
   private String filePath=".\\";
   private String fileSizeStr="0";
   private String fileModifiedDate="1970-01-01 00:00:00";
   private int FileType;
   

public NetFileData(String fileInfo,String filePath)
   {
	   String[] attrs=fileInfo.split(">");
	   if(attrs.length == 4)
	   {
		   this.filePath=filePath;
		   this.fileName=attrs[0];
		   this.fileModifiedDate=attrs[1];
		   this.fileSize=new Long(attrs[2]);
		   this.FileType=Integer.valueOf( attrs[3]).intValue();  
		   this.fileSizeStr=parseFileSize(this.fileSize);
		   
	   }
   }

public long getFileSize() {
	return fileSize;
}

public void setFileSize(long fileSize) {
	this.fileSize = fileSize;
}

public String getFileName() {
	return fileName;
}

public void setFileName(String fileName) {
	this.fileName = fileName;
}

public String getFilePath() {
	return filePath;
}

public void setFilePath(String filePath) {
	this.filePath = filePath;
}

public String getFileSizeStr() {
	return fileSizeStr;
}

public void setFileSizeStr(String fileSizeStr) {
	this.fileSizeStr = fileSizeStr;
}


public String getFileModifiedDate() {
	return fileModifiedDate;
}

public void setFileModifiedDate(String fileModifiedDate) {
	this.fileModifiedDate = fileModifiedDate;
}
public int getFileType() {
	return FileType;
}

public void setFileType(int fileType) {
	FileType = fileType;
}
private static String parseFileSize(long fileSize) {
	// TODO Auto-generated method stub
	String sizeStr="";
	double KB=(double)1024.0;
	double MB=(double)(KB*1024.0);
	double GB=(double)(MB*1024.0);
	double sizef=(double)(fileSize);
	if (sizef>=GB)
	{
		sizeStr=new String().format("%.3fGB", sizef/GB);
		return sizeStr;
	}
	if (sizef>=MB)
	{
		sizeStr=new String().format("%.2fMB", sizef/MB);
		return sizeStr;
	}
	if (sizef>=KB)
	{
		sizeStr=new String().format("%.1fKB", sizef/KB);
		return sizeStr;
	}
	sizeStr=new String().format("%dB", (int)fileSize);
	return sizeStr;
}
}
