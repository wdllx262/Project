package lutongda.client.operator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import lutongda.client.app.Application;
import lutongda.client.app.CombinationActivity;
import lutongda.client.app.MainActivity;
import lutongda.client.data.NetFileData;
import lutongda.client.socket.CmdClientSocket;
import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FileTransferBeginHandler  extends Handler{
	private NetFileData fileData;
	private Context context;
	private String ip;
	private int port;
	private String dirfile;
	private long fileSize;
	ShowRemoteFileHandler showRemoteFileHandler;
	CmdClientSocket cmdClientSocket;
	long passedlen;
	Socket cs=null;
	int mode1=0;
	int num=1;
	public FileTransferBeginHandler(Context context,NetFileData fileData,String ip) {
		super();
		this.fileData = fileData;
		this.context=context;
		this.ip=ip;
	}
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle bundle = msg.getData();
		ArrayList<String> ack = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
		port=Integer.parseInt(ack.get(2));
		fileSize=Integer.parseInt(ack.get(3));
		passedlen=Integer.parseInt(ack.get(4));
		Log.e("passedlen........",String.valueOf(passedlen));
		Log.e("ip........",String.valueOf(ip));
		Log.e("port........",String.valueOf(port)); 
		dirfile=CheckLocalDownloadFolder.check();
		Log.e("path............", dirfile);
		final File file=new File(dirfile+"/"+fileData.getFileName());
		 if (file.exists()){  
	            file.delete();  
	            try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        }  
	        try {  
	              //�����ļ�  
	              file.createNewFile();  
	              //��һ����˾��ʾ����ʾ�����ɹ�  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	        }  
		final ProgressDialog dialog = new ProgressDialog(this.context);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    dialog.setCancelable(true);// �����Ƿ����ͨ�����Back��ȡ��
        dialog.setCanceledOnTouchOutside(false);// �����ڵ��Dialog���Ƿ�ȡ��Dialog������
	    dialog.setIndeterminate(false);
	    dialog.setTitle("��������");
        dialog.setMax(100);
        dialog.setMessage("�ļ�������");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                       try {
							cs.close();
							file.delete();
							dialog.dismiss();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                });
        dialog.show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int i = Integer.valueOf(String.valueOf((passedlen*100/fileSize)));;
                Log.e("i................",String.valueOf(i));
                //Log.e("passedlen................",String.valueOf(passedlen));
                dialog.incrementProgressBy(i);
                int j=i;
                while (i < 100) {
                    try {
                    	
                        Thread.sleep(200);
                        // ���½������Ľ���,���������߳��и��½���������
                        dialog.incrementProgressBy(i-j);
                        // dialog.incrementSecondaryProgressBy(10)//�������������·�ʽ
                        j=i;
                        i=Integer.valueOf(String.valueOf((passedlen*100/fileSize)));
    
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                dialog.incrementProgressBy(i-j);
                // �ڽ���������ʱɾ��Dialog
                dialog.dismiss();
                //passedlen=0;

            }
        }).start();
        new Thread(new Runnable() {
      		
      		@Override
      		public void run() {
      			// TODO Auto-generated method stub
      			try {
      				connect();
      			} catch (IOException e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
      		}
      	}).start();
		super.handleMessage(msg);
	}
	 private void connect() throws IOException {//���ӷ���˺���
		   try
		   {
				/*InetSocketAddress address = new InetSocketAddress(ip, port);
				cs= new Socket();
				cs.connect(address, 2000);*/
			   cs=new Socket(ip,port);
		   if (cs==null) 
		   {
			  // Log.e("uyauisdyuasyduyh","ip����");
			   return;
		   }
		   }
		   catch (Exception e)
		   {
			   Log.e("uyauisdyuasyduyh","�˿ڴ���");
			   e.printStackTrace();
		   }
		   DataOutputStream out=null;
		   out=new DataOutputStream(cs.getOutputStream());
		  //out.writeByte(0x1);
		   //out.flush();
		   DataInputStream inputStream=null;
		   try{
		   inputStream=new DataInputStream(new BufferedInputStream(cs.getInputStream()));
		   }catch(Exception e)
		   {
			   Log.e("tagtag","������Ϣ�������");
			   //System.out.print("������Ϣ�������\n");
			   return;
		   }
		   try
		   {
		   int buffseSize=8192;
		   byte[] buf=new byte[buffseSize];
		   String savePath=new String(dirfile+"/"+fileData.getFileName());
		   DataOutputStream fileout=new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
		   //System.out.println("��ʼ�����ļ���\n");
		   Log.e("tagtag","��ʼ�����ļ���");
		   while(true)
		   {
			   if (mode1==1)  this.wait();
			   else if (mode1==2) this.notify();
			   int read=0;
			   if (inputStream!=null)
			   {
				   read=inputStream.read(buf);
			   }
			   if (read==-1) break;
			   passedlen+=read;
			
		       fileout.write(buf,0,read);
		   }
		   //System.out.println("�������!");
		   Log.e("tagtag","�������!");
		   fileout.close();
		   cs.close();
		   }catch(Exception e)
		   {
			   //System.out.println("���մ���");
			   Log.e("tagtag","���մ���");
			   cs.close();
			   return;
		   }
		}
}
