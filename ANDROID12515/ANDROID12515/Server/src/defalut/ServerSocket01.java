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
    static int connect_count=0;//���Ӵ���ͳ��
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
//                writer.write("Hello, ���Ƿ����ServerSocket�����ǵ�"+(++connect_count)+"������");
//                writer.flush();
//                writer.close();
//                socket.close();
//                System.out.println("��ǰSocket�������");
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
			long lastModified = mfile.lastModified();//��ȡ�ļ��޸�ʱ��
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//��ʱ���ʽ�����磺2018-03-16 09:50:23
			String fileDate = dateFormat.format(new Date(lastModified));//ȡ���ļ�����޸�ʱ�䣬������ʽתΪ�ַ���
			String fileSize="0";
			String isDir="1";
			if(!mfile.isDirectory()){//�ж��Ƿ�ΪĿ¼
				isDir="0";
				fileSize=""+mfile.length();
			}
			msgBackList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">");
		}
		
	}
	public ArrayList<String> readSocketMsg(Socket socket) throws IOException {
		// ��socket���������������socket�������Ѿ����ӳɹ�δ���ڹرյ�socket
		//���ȶ�ȡһ�У�������ȡ���ַ�������ת��Ϊint�����ݣ��ѻ�ú�����Ҫ��ȡ������
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
		//��ȡ���������������ܹرգ���ʱ�رգ��Ὣsocket�رգ��Ӷ����º�����socketд�����޷�ʵ��
		return msgList;
	}	
	private void writeBackMsg(Socket socket) throws IOException {
		// TODO Auto-generated method stub
		BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());//socket.getOutputStream()���������BufferedOutputStream�����װΪ������������

		//			OutputStreamWriter writer=new OutputStreamWriter(os);//Ĭ�ϵ��ַ����룬�п�����GB2312Ҳ�п�����UTF-8��ȡ����ϵͳ
		//			//���鲻Ҫ��Ĭ���ַ����룬����ָ��UTF-8���Ա�֤���ͽ����ַ�����һ�£������ڳ�����
		//��������ֽڴ���ģ������߱��ַ���ֱ��д�빦�ܣ�����ٽ����װ��OutputStreamWriter��ʹ��֧���ַ���ֱ��д��
		writer=new OutputStreamWriter(os,"UTF-8");//���Խ��ַ�����ĳ�"GB2312"
		writer.write(""+msgBackList.size()+"\n");//δ����д�����������������ڴ���
		writer.flush();//д������������������ݴ����ȥ
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
