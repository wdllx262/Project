package wzu.server.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FileDownLoadSocketThread extends Thread{
	private ServerSocket serverSocket;
	private long filePos=0l;
	private long filesize;
	private File file;
	private long passedlen=0;
	public long getPassedlen() {
		return passedlen;
	}

	public void setPassedlen(long passedlen) {
		this.passedlen = passedlen;
	}
	private OutputStreamWriter writer;
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public long getFilePos() {
		return filePos;
	}

	public void setFilePos(long filePos) {
		this.filePos = filePos;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public FileDownLoadSocketThread(File file,long filePos) {
		// TODO Auto-generated constructor stub
		try {
			serverSocket = new ServerSocket(0);//动态分配可用端口
			int port=serverSocket.getLocalPort();
			System.out.println(String.valueOf(port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close( );
		}
		this.file=file;
		this.filePos=filePos;
		this.filesize=file.length();
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
		   Socket s=null;
			if (serverSocket.isClosed())
			{
				System.out.println("The server socket is clised for ever.Please create another CmdServrSocket!");
			}
			while(true)
			{
			try {
				s=serverSocket.accept();
				System.out.println("建立socket链接");
				System.out.println(String.valueOf(serverSocket.getLocalPort()+"........."+s.getInetAddress().getHostAddress()));
				DataInputStream dis=new DataInputStream(new BufferedInputStream(new FileInputStream(file.getPath())));
				dis.readByte();
				System.out.println("文件传输完成");
				DataInputStream fis=new DataInputStream(new BufferedInputStream(new FileInputStream(file.getPath())));
				fis.skip(passedlen);
				DataOutputStream ps=new DataOutputStream(s.getOutputStream());
				int bufferSize=8192;
				byte[] buf=new byte[bufferSize];
				while(true)
				{
					int read=0;
					if (fis!=null)
					{
						read=fis.read(buf);
					}
					if (read==-1) break;
					passedlen+=read;
					ps.write(buf,0,read);
				}
				ps.flush();
				fis.close();
				s.close();
				System.out.println("文件传输完成");
			}catch(Exception e)
			{
				System.out.printf("文件传输失败");
				try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			break;
			}
		super.run();
	}


}
