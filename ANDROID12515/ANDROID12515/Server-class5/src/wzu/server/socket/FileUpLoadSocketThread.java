package wzu.server.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import wzu.server.operator.FileNameUtils;

public class FileUpLoadSocketThread extends Thread{
	private ServerSocket serverSocket;
	private int filemode=-1;
	private long filesize;
	private File file;
	private File newfile;
	int  passedlen;
	String dirfile="C:/load/";
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public int getFilemode() {
		return filemode;
	}
	public void setFilemode(int filemode) {
		this.filemode = filemode;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public  FileUpLoadSocketThread(File file,int filemode,long fileSize) throws IOException {
		// TODO Auto-generated constructor stub
		try {
			serverSocket = new ServerSocket(0);//��̬������ö˿�
			int port=serverSocket.getLocalPort();
			System.out.println(String.valueOf(port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close( );
		}
		this.file=file;
		this.filemode=filemode;
		this.filesize=fileSize;
		newfile=FileNameUtils.checkFile(file.getName(),filemode);
		newfile.createNewFile();
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 Socket s=null;
			if (serverSocket.isClosed())
			{
				System.out.println("The server socket is clised for ever.Please create another CmdServrSocket!");
			}
			while(true)
			{
			try {
				s=serverSocket.accept();
				System.out.println("����socket����");
				System.out.println(String.valueOf(serverSocket.getLocalPort()+"........."+s.getInetAddress().getHostAddress()));
				DataOutputStream out=null;
				   out=new DataOutputStream(s.getOutputStream());
				  //out.writeByte(0x1);
				   //out.flush();
				   DataInputStream inputStream=null;
				   try{
				   inputStream=new DataInputStream(new BufferedInputStream(s.getInputStream()));
				   }catch(Exception e)
				   {
					   System.out.print("������Ϣ�������\n");
					   return;
				   }
				   try
				   {
				   int buffseSize=8192;
				   byte[] buf=new byte[buffseSize];
				   passedlen=0;
				   String savePath=new String(dirfile+"/"+newfile.getName());
				   DataOutputStream fileout=new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
				   System.out.println("��ʼ�����ļ���\n");
				   while(true)
				   {
					   int read=0;
					   if (inputStream!=null)
					   {
						   read=inputStream.read(buf);
					   }
					   if (read==-1) break;
					   passedlen+=read;
				       fileout.write(buf,0,read);
				   }
				   System.out.println("�������!");
				   fileout.close();
				   s.close();
				   if (passedlen!=filesize) newfile.delete();
			}catch(Exception e)
			{
				System.out.printf("�ļ�����ʧ��");
				try {
					s.close();
					newfile.delete();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			 }catch(Exception e)
			   {
				   System.out.println("���մ���");
				   newfile.delete();
				  // file.delete();
				   return;
			   }
			break;
			}
		super.run();
	}
}
