package lutongda.client.operator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import lutongda.client.data.NetFileData;
import lutongda.client.socket.CmdClientSocket;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FileUploadBeginHandler extends Handler{
	private NetFileData fileData;
	private Context context;
	private String ip;
	private int port;
	private String dirfile;
	private long fileSize;
	long passedlen;
	Socket cs=null;
	public  FileUploadBeginHandler(Context context,NetFileData fileData,String ip) {
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
		fileSize=fileData.getFileSize();
		final ProgressDialog dialog = new ProgressDialog(this.context);
	    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setTitle("正在上传");
        dialog.setMax(100);
        dialog.setMessage("文件上传中");
        dialog.setCancelable(true);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "暂停",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        try {
							cs.close();
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
                int i = 0;
                int j=0;
                while (i < 100) {
                    try {
                    	
                        Thread.sleep(200);
                        // 更新进度条的进度,可以在子线程中更新进度条进度
                        dialog.incrementProgressBy(i-j);
                        // dialog.incrementSecondaryProgressBy(10)//二级进度条更新方式
                        j=i;
                        Log.e("passedlen...........",String.valueOf(passedlen));
                        Log.e("fileSize...........",String.valueOf(fileSize));
                        i=Integer.valueOf(String.valueOf((passedlen*100/fileSize)));

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                dialog.incrementProgressBy(i-j);
                // 在进度条走完时删除Dialog
                dialog.dismiss();

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
      private void connect() throws IOException {//连接服务端函数
	   try
	   {
			/*InetSocketAddress address = new InetSocketAddress(ip, port);
			cs= new Socket();
			cs.connect(address, 2000);*/
		   cs=new Socket(ip,port);
	   if (cs==null) 
	   {
		  // Log.e("uyauisdyuasyduyh","ip错误");
		   return;
	   }
	   passedlen=0;
	   File file=new File("/storage/emulated/0/"+fileData.getFileName());
	   DataInputStream dis=new DataInputStream(new BufferedInputStream(new FileInputStream(file.getPath())));
		dis.readByte();
		System.out.println("文件传输完成");
		DataInputStream fis=new DataInputStream(new BufferedInputStream(new FileInputStream(file.getPath())));
		DataOutputStream ps=new DataOutputStream(cs.getOutputStream());
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
			Log.e("read...........",String.valueOf(read));
			passedlen+=read;
			ps.write(buf,0,read);
		}
		ps.flush();
		fis.close();
		cs.close();
	   }
	   catch (Exception e)
	   {
		   Log.e("uyauisdyuasyduyh","端口错误");
		   e.printStackTrace();
	   }
	}
	
}
