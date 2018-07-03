
package wzu.server.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import wzu.server.operator.Operator;
import wzu.server.operator.VisualKeyMap;
public class CmdServerSocketThread extends Thread{
     int port=8019;
     private BufferedReader bufferedReader;
     private OutputStreamWriter writer;
     private ServerSocket serverSocket;
     ArrayList<String> msgBackList=new ArrayList<String>();
     public CmdServerSocketThread() {
		// TODO Auto-generated constructor stub
    	 
	}
     public CmdServerSocketThread(int port)
     {
    	 super();
    	 this.port=port;
     }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			serverSocket =new ServerSocket(port);
			doCmdTask(serverSocket);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			close( );
		}
	}
	private void close() {
		// TODO Auto-generated method stub
		if (serverSocket!=null)
		{
			try {
				serverSocket.close();
				System.out.println("The CmdServerSocket is closed");
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	private void doCmdTask(ServerSocket serverSocket2) {
		// TODO Auto-generated method stub
		while(true)
		{
			if (serverSocket.isClosed())
			{
				System.out.println("The server socket is clised for ever.Please create another CmdServrSocket!");
				break;
			}
			System.out.println("Waiting client to connect......");
			Socket socket;
			try {
				socket=serverSocket.accept();
				System.out.println("Client connected from: "+socket.getRemoteSocketAddress().toString());
			    try {
					getAndDealCmd(socket);
				} catch (Exception e) {
					// TODO: handle exception
					cmdFail(e.getMessage());
					System.out.println("getAndDealCmd(socket) error:"+e.getMessage());
				}
			    writeBackMsg(socket);
			    bufferedReader.close();
			    writer.close();
			    socket.close();
			    System.out.println("��ǰSocket�������");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}
		}
	}
	private void writeBackMsg(Socket socket) throws Exception {
		// TODO Auto-generated method stub
		BufferedOutputStream os=new BufferedOutputStream(socket.getOutputStream());
		writer=new OutputStreamWriter(os,"UTF-8");//���Խ��ַ�����ĳ�"GB2312"
		writer.write(""+msgBackList.size()+"\n");//δ����д�����������������ڴ���
		writer.flush();//д������������������ݴ����ȥ
		for(int i=0;i<msgBackList.size();i++){
			writer.write(msgBackList.get(i)+"\n");
			writer.flush();
		}
	}
	private void cmdFail(String message) {
		// TODO Auto-generated method stub
		msgBackList.clear();
		msgBackList.add(message);
	}
	private void getAndDealCmd(Socket socket)  throws Exception{
		// TODO Auto-generated method stub
		ArrayList<String> cmdList=readSocketMsg(socket);
	     String[] str=new String[]{};
		if (cmdList.size()==1)
		{
			String cmd=cmdList.get(0); 
			processCmd(cmd);
		}
		else
		{
			cmdFail("Cmd size not right. "+cmdList.toString());
		}
	}
	private ArrayList<String> readSocketMsg(Socket socket) throws Exception{
		// TODO Auto-generated method stub
		ArrayList<String> msgList=new ArrayList<String>();
		InputStream inputStream=socket.getInputStream();
		InputStreamReader reader=new InputStreamReader(inputStream,"UTF-8");
		bufferedReader=new BufferedReader(reader);
		String lineNumStr = bufferedReader.readLine();
		int lineNum=Integer.parseInt(lineNumStr);
		for(int i=0;i<lineNum;i++){
			String str = bufferedReader.readLine();
			msgList.add(str);
		}
		//��ȡ���������������ܹرգ���ʱ�رգ��Ὣsocket�رգ��Ӷ����º�����socketд�����޷�ʵ��
		return msgList;
	}
	private void processCmd(String cmd) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("Client Command:"+cmd);
		int splitId=cmd.indexOf(":");
		if (splitId<1)
		{
			
			cmdFail("Invalid Cmd from client:"+ cmd);
			return;
		}
		 String cmdHead=cmd.substring(0,splitId);
    	 String cmdBody=cmd.substring(splitId+1);
    	 msgBackList=Operator.exeCmd(cmdHead.toLowerCase(), cmdBody);
    	 //msgBackList.add(0,"ok");
	}
     
}
