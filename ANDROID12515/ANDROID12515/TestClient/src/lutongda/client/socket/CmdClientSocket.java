package lutongda.client.socket;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import lutongda.client.app.MainActivity;
import lutongda.client.operator.ShowRemoteFileHandlerofMouse;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CmdClientSocket  {
   int port;
   String ip;
   int connect_timeout=2000;
   int transfer_timeout=2000;
   Context context;
   public int getPort() {
	return port;
}
public void setPort(int port) {
	this.port = port;
}
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
   int flag=0;
   Handler handler;
   Socket socket;
   private BufferedReader bufferedReader;
   private OutputStreamWriter writer;
   public static final String KEY_SERVER_ACK_MSG="KEY_SERVER_ACK_MSG";
   public static final int SERVER_MSG_ERROR=1;
   public static final int SERVER_MSG_OK=0;
   int msgType=0;
   boolean isDebug=true;
public CmdClientSocket(String ip, int port, Handler handler,Context context) {
	super();
	this.port = port;
	this.ip = ip;
	this.handler = handler;
	this.context=context;
}
   /*private void doCmdTask(String cmd)
   {
	   ArrayList<String> msgList=new ArrayList<String>();
	   connect();
	   try
	   {
		   writeCmd(cmd);
		   msgList=readSocketMsg();
	   }catch (IOException e)
	   {
		   e.printStackTrace();
	   }
	   close();
	   Message message=handler.obtainMessage();
	   Bundle bundle=new Bundle();
	   bundle.putStringArrayList(KEY_SERVER_ACK_MSG, msgList);
	   message.setData(bundle);
	   handler.sendMessage(message);
   }*/
private void doCmdTask(String cmd) {
	ArrayList<String> msgList=new ArrayList<String>();
	try {
		connect();//连接服务端，若有异常，被捕捉
		writeCmd(cmd);//向服务端发送命令，未关闭输出流
		msgList = readSocketMsg();//读取socket输入流信息，并将结果存入msgList列表
		//若服务端返回信息的状态为"ok"，则将msgType设置为自定义常量SERVER_MSG_OK（实际值为0）
		//服务端返回信息状态不是"ok"，则将msgType为SERVER_MSG_ERROR（实际值为1）
		msgType=SERVER_MSG_OK;
		close();//关闭Socket的输入流、输出流
	} catch (IOException e) {
		Log.e("uyauisdyuasyduyh","端口错误");
		// TODO Auto-generated catch block
		msgType=SERVER_MSG_ERROR;//若捕捉到异常，则设置msgType为SERVER_MSG_ERROR（实际值为1）
		//SERVER_MSG_ERROR和SERVER_MSG_OK为自定义常量
		//public static int SERVER_MSG_OK=0;//用于发送给句柄的消息类型,放在消息的arg2中，表示服务端正常
		//public static int SERVER_MSG_ERROR=1;//表示服务端出错
		msgList.add(e.toString());//在msgList列表中放入错误信息
		e.printStackTrace();
	}
	Message message = handler.obtainMessage();
	Bundle bundle = new Bundle();
	bundle.putStringArrayList(KEY_SERVER_ACK_MSG, msgList);//回传数据需要对msgList的size进行判断，大于0才为有效
	message.arg2=msgType;
	//句柄bundle在handleMessage(Message msg)函数中首先对消息的arg2进行判断，若是SERVER_MSG_ERROR类型，则不更新列表，Toast显示错误信息
	//若message.arg2是SERVER_MSG_OK，则更新列表UI
	message.setData(bundle);
	handler.sendMessage(message);// 通过句柄通知主UI数据传输完毕，并回传数据
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
   private void writeCmd(String cmd) throws IOException
   {
	   BufferedOutputStream os=new BufferedOutputStream(socket.getOutputStream());
	   writer=new OutputStreamWriter(os,"UTF-8");
	   writer.write("1\n");
	   writer.write(cmd+"\n");
	   writer.flush();
   }
   private ArrayList<String> readSocketMsg() throws IOException
   {
	   ArrayList<String> msgList=new ArrayList<String>();
	   InputStream inputStream=socket.getInputStream();
	   InputStreamReader reader=new InputStreamReader(inputStream,"UTF-8");
	   bufferedReader=new BufferedReader(reader);
	   String lineNumStr=bufferedReader.readLine();
	   int lineNum=Integer.parseInt(lineNumStr);
	  // Log.e("sfijfidj",lineNumStr);
	   for (int i=0;i<lineNum;i++)
	   {
		   String str=bufferedReader.readLine();
		   msgList.add(str);
	   }
	   return msgList;
   }
   private void close()
   {
	   try
	   {
		   bufferedReader.close();
		   writer.close();
		   socket.close();
	   }
	   catch(IOException e)
	   {
		   e.printStackTrace();
	   }
   }

   private void connect() throws IOException {//连接服务端函数
		InetSocketAddress address = new InetSocketAddress(ip, port);
		socket = new Socket();
		
		socket.connect(address, connect_timeout);
		if(!isDebug){//若不处于调试模式，则设置socket数据传输超时
			socket.setSoTimeout(transfer_timeout);//设置传输数据的超时时间
		}

	}
}
