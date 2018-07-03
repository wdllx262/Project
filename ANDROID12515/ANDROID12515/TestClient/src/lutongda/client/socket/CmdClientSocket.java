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
		connect();//���ӷ���ˣ������쳣������׽
		writeCmd(cmd);//�����˷������δ�ر������
		msgList = readSocketMsg();//��ȡsocket��������Ϣ�������������msgList�б�
		//������˷�����Ϣ��״̬Ϊ"ok"����msgType����Ϊ�Զ��峣��SERVER_MSG_OK��ʵ��ֵΪ0��
		//����˷�����Ϣ״̬����"ok"����msgTypeΪSERVER_MSG_ERROR��ʵ��ֵΪ1��
		msgType=SERVER_MSG_OK;
		close();//�ر�Socket���������������
	} catch (IOException e) {
		Log.e("uyauisdyuasyduyh","�˿ڴ���");
		// TODO Auto-generated catch block
		msgType=SERVER_MSG_ERROR;//����׽���쳣��������msgTypeΪSERVER_MSG_ERROR��ʵ��ֵΪ1��
		//SERVER_MSG_ERROR��SERVER_MSG_OKΪ�Զ��峣��
		//public static int SERVER_MSG_OK=0;//���ڷ��͸��������Ϣ����,������Ϣ��arg2�У���ʾ���������
		//public static int SERVER_MSG_ERROR=1;//��ʾ����˳���
		msgList.add(e.toString());//��msgList�б��з��������Ϣ
		e.printStackTrace();
	}
	Message message = handler.obtainMessage();
	Bundle bundle = new Bundle();
	bundle.putStringArrayList(KEY_SERVER_ACK_MSG, msgList);//�ش�������Ҫ��msgList��size�����жϣ�����0��Ϊ��Ч
	message.arg2=msgType;
	//���bundle��handleMessage(Message msg)���������ȶ���Ϣ��arg2�����жϣ�����SERVER_MSG_ERROR���ͣ��򲻸����б�Toast��ʾ������Ϣ
	//��message.arg2��SERVER_MSG_OK��������б�UI
	message.setData(bundle);
	handler.sendMessage(message);// ͨ�����֪ͨ��UI���ݴ�����ϣ����ش�����
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

   private void connect() throws IOException {//���ӷ���˺���
		InetSocketAddress address = new InetSocketAddress(ip, port);
		socket = new Socket();
		
		socket.connect(address, connect_timeout);
		if(!isDebug){//�������ڵ���ģʽ��������socket���ݴ��䳬ʱ
			socket.setSoTimeout(transfer_timeout);//���ô������ݵĳ�ʱʱ��
		}

	}
}
