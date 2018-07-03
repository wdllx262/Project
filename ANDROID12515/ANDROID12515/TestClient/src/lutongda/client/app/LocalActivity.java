package lutongda.client.app;

import java.io.File;

import com.example.testclient.R;

import lutongda.client.data.NetFileData;
import lutongda.client.operator.FileTransferBeginHandler;
import lutongda.client.operator.FileUploadBeginHandler;
import lutongda.client.operator.ShowNonUiUpdateCmdHandler;
import lutongda.client.operator.ShowRemoteFileHandler;
import lutongda.client.socket.CmdClientSocket;
import lutongda.client.socket.CmdLocalSocket;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class LocalActivity  extends Activity{
    ListView lv;
    static String PATH = Environment.getExternalStorageDirectory() + "/";  
    String ip;
    int port;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loacl_main);
		lv=(ListView) findViewById(R.id.listView1);
	    registerForContextMenu(lv);
	    ShowRemoteFileHandler showRemoteFileHandler=new ShowRemoteFileHandler(LocalActivity.this,lv);
	    CmdLocalSocket cmdClientSocket=new CmdLocalSocket(showRemoteFileHandler,LocalActivity.this);
	    cmdClientSocket.work("dir:"+PATH);
	    Application application=(Application) getApplication();
		ip=application.getIp();
		port=application.getPort();
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ShowRemoteFileHandler showRemoteFileHandler=new ShowRemoteFileHandler(LocalActivity.this,lv);
				NetFileData fileData = (NetFileData) arg0.getItemAtPosition(arg2);
				String pwd=fileData.getFilePath();
				//Log.e("this is pwd.....",pwd);
				String filePath="";
				if(pwd.endsWith("/")|pwd.endsWith("\\")){
					//�ļ�·�����ܴ�"/"��β������"c://aaa/b/"Ҳ������"c://aaa/b"�����Ҫ������ȫ
					//����Windowsϵͳ��Linuxϵͳ�ļ��зָ�����ͬ������Щϵͳ���ļ�Ŀ¼�ı�ʾ��"c:\\\\aaa\\b\\"��ע��"\\"ת���"\"
					filePath=fileData.getFileName();
				}else{
					filePath=File.separator+"storage/emulated/0/"+fileData.getFileName();
					if (fileData.getFileName().equals("..")) filePath="..";
					Log.e("this is pwd.....",filePath);
				}
				//Log.e("2521212",filePath);
				if(fileData.getFileType()==1||fileData.getFileType()==2){
		
					if(fileData.getFileName().equals("...")){
						//�����Ŀ¼���г������̷�
						filePath="...";
					}
					CmdLocalSocket cmdClientSocket=new CmdLocalSocket(showRemoteFileHandler,LocalActivity.this);
					cmdClientSocket.work("dir:"+filePath);
				}

			}
				
		});
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
	    getMenuInflater().inflate(R.menu.action_local_menu, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	 @Override
 	public boolean onContextItemSelected(MenuItem item) {
 		// TODO Auto-generated method stub
 	AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		int pos=contextMenuInfo.position;
		NetFileData netFileData=(NetFileData) lv.getAdapter().getItem(pos);//����listViewΪ��ʾ�ļ��б����ͼ
		switch(item.getItemId()){
		case R.id.action_upload:
			upload(netFileData);
			break;
		default :break;
		}
 		return super.onContextItemSelected(item);
 	}
	  private void upload(NetFileData netFileData) {
			// TODO Auto-generated method stub
		  if (netFileData.getFileType()==1||netFileData.getFileType()==2)
			{
				Toast.makeText(LocalActivity.this, "��δʵ�������ļ������ع���",Toast.LENGTH_LONG).show();
			}
		  else
		  {
			    FileUploadBeginHandler FileUploadBeginHandler=new FileUploadBeginHandler(LocalActivity.this,netFileData,ip);
				CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,FileUploadBeginHandler,LocalActivity.this);
				cmdClientSocket.work("ulf:" + netFileData.getFileName()+"|"+String.valueOf(netFileData.getFileSize()));
		  }
		}

}
