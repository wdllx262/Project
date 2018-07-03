package wzu.server.app;

import java.io.File;
import java.util.HashMap;

import wzu.server.operator.VisualKeyMap;
import wzu.server.socket.CmdServerSocketThread;
import wzu.server.socket.FileDownLoadSocketThread;

public class ServerSocketApp {

	/**
	 * @param args
	 */
	//public static FileDownLoadSocketThread filedownload=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         int cmdPort=8019;
         boolean isFail=true;
         try {
			if (isFail)
			{
				isFail=false;
				CmdServerSocketThread cmdServerSocketThread =new CmdServerSocketThread(cmdPort);
				 cmdServerSocketThread.start();
				//cmdServerSocketThread.start();
				System.out.println("The cmdServerSocketThread if finished!");
			}
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally
		{
			isFail=false;
		}
         
	}

}
