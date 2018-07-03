package defalut;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class ServerSocket01 {
    int port=8019;
	private BufferedReader bufferedReader;
	private OutputStreamWriter writer;
	private ArrayList<String> msgBackList=new ArrayList<String>();
    static int connect_count=0;//连接次数统计
    public ServerSocket01() {
        // TODO Auto-generated constructor stub
    }
    public ServerSocket01(int port) {
        super();
        this.port = port;
    }
    public void work() throws IOException {
            ServerSocket serverSocket=new ServerSocket(port);
            while(true){
                System.out.println("Waiting client to connect.....");
                Socket socket = serverSocket.accept();
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("Client connected from: "+socket.getRemoteSocketAddress().toString());
                ArrayList<String> msgIns = readSocketMsg(socket);
                processMsg(msgIns);
                writeBackMsg(socket);
                bufferedReader.close();
                writer.close();
                socket.close();
                
                
                
//                BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
//                OutputStreamWriter writer=new OutputStreamWriter(os,"gb2312");
//                writer.write("Hello, 我是服务端ServerSocket！你是第"+(++connect_count)+"个连接");
//                writer.flush();
//                writer.close();
//                socket.close();
//                System.out.println("当前Socket服务结束");
            }
    }
    private void processMsg(ArrayList<String> msgIns) {
		// TODO Auto-generated method stub
		if(msgIns.size()==1)
		{
		     String cmd=msgIns.get(0);
		     cmd=cmd.toLowerCase();
		     int idx=cmd.indexOf(":");
		     if (idx>2)
		     {
		    	 String cmdHead=cmd.substring(0,idx);
		    	 String cmdBody=cmd.substring(idx+1);
		    	 processCmd(cmdHead,cmdBody);
		    	 
		     }
		     else
		     {
		    	 sendFailMsg("Invaild Cmd format!");
		     }
		}else{
			sendFailMsg("Invaild Cmd format!");
		}
	}
	private void processCmd(String cmdHead, String cmdBody) {
		// TODO Auto-generated method stub
		if (cmdHead.equals("dir"))
		{
			exeDir(cmdBody);
			return;
		}
	}
	private void sendFailMsg(String string) {
		// TODO Auto-generated method stub
		msgBackList.clear();
		msgBackList.add(string);
		
	}
	/**
     * @param args
     */
	private void exeDir(String cmdBody) {
		// TODO Auto-generated method stub
		File file = new File(cmdBody);
		File[] listFiles = file.listFiles();
		msgBackList.clear();
		for(File mfile:listFiles){
			String fileName = mfile.getName();
			long lastModified = mfile.lastModified();//获取文件修改时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//给时间格式，例如：2018-03-16 09:50:23
			String fileDate = dateFormat.format(new Date(lastModified));//取得文件最后修改时间，并按格式转为字符串
			String fileSize="0";
			String isDir="1";
			if(!mfile.isDirectory()){//判断是否为目录
				isDir="0";
				fileSize=""+mfile.length();
			}
			msgBackList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">");
		}
		
	}
	public ArrayList<String> readSocketMsg(Socket socket) throws IOException {
		// 读socket的输入流，传入的socket参数是已经连接成功未处于关闭的socket
		//首先读取一行，并将读取的字符串内容转换为int型数据，已获得后续需要读取的行数
		ArrayList<String> msgList=new ArrayList<String>();
		InputStream inputStream = socket.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
		bufferedReader=new BufferedReader(reader);
		String lineNumStr = bufferedReader.readLine();
		int lineNum=Integer.parseInt(lineNumStr);
		for(int i=0;i<lineNum;i++){
			String str = bufferedReader.readLine();
			msgList.add(str);
		}
		//读取结束后，输入流不能关闭，此时关闭，会将socket关闭，从而导致后续对socket写操作无法实现
		return msgList;
	}	
	private void writeBackMsg(Socket socket) throws IOException {
		// TODO Auto-generated method stub
		BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());//socket.getOutputStream()是输出流，BufferedOutputStream则将其封装为带缓冲的输出流

		//			OutputStreamWriter writer=new OutputStreamWriter(os);//默认的字符编码，有可能是GB2312也有可能是UTF-8，取决于系统
		//			//建议不要用默认字符编码，而是指定UTF-8，以保证发送接收字符编码一致，不至于出乱码
		//输出流是字节传输的，还不具备字符串直接写入功能，因此再将其封装入OutputStreamWriter，使其支持字符串直接写入
		writer=new OutputStreamWriter(os,"UTF-8");//尝试将字符编码改成"GB2312"
		writer.write(""+msgBackList.size()+"\n");//未真正写入的输出流，仅仅在内存中
		writer.flush();//写入输出流，真正将数据传输出去
		for(int i=0;i<msgBackList.size();i++){
			writer.write(msgBackList.get(i)+"\n");
			writer.flush();
		}
	}
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            new ServerSocket01().work();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
