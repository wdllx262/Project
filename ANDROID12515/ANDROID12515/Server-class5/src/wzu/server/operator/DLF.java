package wzu.server.operator;

import java.io.File;
import java.util.ArrayList;

import wzu.server.app.ServerSocketApp;
import wzu.server.socket.FileDownLoadSocketThread;

public class DLF extends BaseOperator {

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		ackMsg.add("ok");
    	ackMsg.add("dlf");
    	File file=new File(cmdBody);
    	FileDownLoadSocketThread filedownload;
    	filedownload=new FileDownLoadSocketThread(file, 1);
    	//FileDownLoadSocketThread filedownload=new FileDownLoadSocketThread(file,1); 
    	int port=filedownload.getServerSocket().getLocalPort();
    	//int port=8888;
    	ackMsg.add(String.valueOf(port));
         long size=filedownload.getFilesize();
    	//long size=0;
    	ackMsg.add(String.valueOf(size));
    	long passedlen=filedownload.getPassedlen();
    	ackMsg.add(String.valueOf(passedlen));
    	filedownload.start();
    	System.out.println("The cmdServerSocketThread if finished!");
    	return ackMsg;
	}

}
