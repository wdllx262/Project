package wzu.server.operator;

import java.io.File;
import java.util.ArrayList;

import wzu.server.socket.FileDownLoadSocketThread;
import wzu.server.socket.FileUpLoadSocketThread;

public class ULF  extends BaseOperator{

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		ackMsg.add("ok");
		ackMsg.add("ulf");
		String filename=null;
		String data;
		long fileSize=0;
		int filemode=-1;
		int splitIdx = cmdBody.indexOf("?");
		if (splitIdx<1)
		{
			data=cmdBody;
			int splitIdx2 = data.indexOf("|");
			filename=data.substring(0, splitIdx2);
			fileSize=Long.parseLong(data.substring(splitIdx2+1));
		}
		else
		{
			data=cmdBody.substring(0, splitIdx);
			int splitIdx2 = data.indexOf("|");
			filename=data.substring(0, splitIdx2);
			fileSize=Long.parseLong(data.substring(splitIdx2+1));
			filemode=Integer.valueOf(cmdBody.substring(splitIdx+1));
		}
		File file=new File(filename);
		FileUpLoadSocketThread fileupload;
		fileupload=new FileUpLoadSocketThread(file,filemode,fileSize);
		int port=fileupload.getServerSocket().getLocalPort();
    	//int port=8888;
    	ackMsg.add(String.valueOf(port));
    	if (filemode<1) ackMsg.add("0");
    	else ackMsg.add(String.valueOf(fileupload.getFilesize()));
    	FileNameUtils .checkFile(filename, filemode);
    	fileupload.start();
 		return ackMsg;
	}

}
