package lutongda.client.socket;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CmdLocalSocket {
	 Context context;
	  Handler handler;
	  static String PATH = Environment.getExternalStorageDirectory() + "/";  
	  private static File file=new File(PATH);
	 public CmdLocalSocket(Handler handler,Context context) {
			super();
			this.context=context;
			this.handler=handler;
		}
	 private ArrayList<String> doCmdTask(String cmd) {
		 ArrayList<String> msgList=new ArrayList<String>();
		 msgList.add("ok");
		 File[] listFiles;
		 boolean isRoot=false;
		 int splitId=cmd.indexOf(":");
		 String cmdHead=cmd.substring(0,splitId);
    	 String cmdBody=cmd.substring(splitId+1);
    	 if(cmdHead.equals("dir"))
    	 {
    		 if(cmdBody.equals("...")){
    				//ackMsg.add("");
    				isRoot=true;
    			}
    			else if(cmdBody.equals("..")){
    				file=file.getParentFile();
    			}else{
    				//Log.e("dsjoioqipoioipi",cmdBody);
    				file=new File(cmdBody);
    			}
    		 if(isRoot){
    				listFiles=File.listRoots();
    				for(File mfile:listFiles){
    					if(!mfile.canRead()){
    						continue;
    					}
    					String filename=mfile.getPath();
    					long lastModified=mfile.lastModified();
    					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    					String fileDate=dateFormat.format(new Date(lastModified));
    					String fileSize="0";
    					String isDir="2";
    					msgList.add(filename+">"+fileDate+">"+fileSize+">"+isDir+">");
    				}	
    			}else{
    				String pwd="";
    				try{
    					pwd=file.getCanonicalPath();
    					
    				} catch (Exception e) {
    					// TODO: handle exception
    					e.printStackTrace();
    				}
    				//System.out.println("pwd:"+pwd);
    				//ackMsg.add("..."+">"+""+">"+"0"+">"+"1"+">");
    				msgList.add("..."+">"+""+">"+"0"+">"+"1"+">");
    				String[] pwdSplits = pwd.split("/");
    				String[] pwdSplits2 = pwd.split("\\\\");
    				if(pwdSplits.length>1|pwdSplits2.length>1){
    					msgList.add(".."+">"+""+">"+"0"+">"+"1"+">");
    				}
    				listFiles=file.listFiles();
    				 //Log.e("datalen",String.valueOf(msgList.size()));
    				// Log.e("datalen",file.getAbsolutePath());
    				if(listFiles!=null){
    					for(File mfile:listFiles){
    						if(!mfile.canRead()){
    							continue;
    						}
    						String filename=mfile.getPath();
    						//System.out.println(filename);
    						long lastModified=mfile.lastModified();
    						SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    						String fileDate=dateFormat.format(new Date(lastModified));
    						String fileSize="0";
    						
    						String isDir="1";//文件夹
    						if(!mfile.isDirectory()){
    							isDir="0";//普通文件
    							int splitIdx = filename.indexOf(".");
    							String idStr=filename.substring(splitIdx+1);
    							//if (idStr.equals("avi")||idStr.equals("mp4"))isDir="3";//视频格式
    							//if (idStr.equals("pptx")||idStr.equals("ppt")) isDir="4";//PPT格式
    							//System.out.println(idStr);
    							fileSize=""+mfile.length();
    						}
    						String test="/storage/emulated/0/";
    						filename=filename.replace(test, "");
    						msgList.add(filename+">"+fileDate+">"+fileSize+">"+isDir+">");
    						//Log.e("datalen",filename);
    					}			
    				}		
    			}	
    	 }
    	// Log.e("datalen",String.valueOf(msgList.size()));
    	 Message message = handler.obtainMessage();
    		Bundle bundle = new Bundle();
    		bundle.putStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG, msgList);//回传数据需要对msgList的size进行判断，大于0才为有效
    		message.arg2=CmdClientSocket.SERVER_MSG_OK;;
    		//句柄bundle在handleMessage(Message msg)函数中首先对消息的arg2进行判断，若是SERVER_MSG_ERROR类型，则不更新列表，Toast显示错误信息
    		//若message.arg2是SERVER_MSG_OK，则更新列表UI
    		message.setData(bundle);
    		handler.sendMessage(message);// 通过句柄通知主UI数据传输完毕，并回传数据
        return msgList;
		}
	 public void work(final String cmd)
	   {
		   new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				doCmdTask(cmd);
			}
		}).start();
	   }
}
