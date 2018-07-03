package lutongda.client.app;

import java.io.File;

import lutongda.client.data.NetFileData;
import lutongda.client.operator.FileTransferBeginHandler;
import lutongda.client.operator.FileUploadBeginHandler;
import lutongda.client.operator.HotKeyGenerator;
import lutongda.client.operator.ShowNonUiUpdateCmdHandler;
import lutongda.client.operator.ShowRemoteFileHandler;
import lutongda.client.socket.CmdClientSocket;
import lutongda.client.view.HotKeyDialog;

import com.example.testclient.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.drm.DrmStore.RightsStatus;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
   TextView tv2;
   EditText et1,et2;
   Button bt;
   ListView lv;
   String ip,poster;
   int flag=0;
   int port;
   ShowRemoteFileHandler showRemoteFileHandler;
   CmdClientSocket cmdClientSocket;
   public static final String KEY_IP="KEY_IP";
   public static final String KEY_PORT="KEY_PORT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bt=(Button) findViewById(R.id.button1);
        et1=(EditText) findViewById(R.id.editText1);
        et2=(EditText) findViewById(R.id.editText2);
        lv=(ListView) findViewById(R.id.listView1);
        registerForContextMenu(lv);
        loadData(this);
    	Application application=(Application) getApplication();
    	application.setIp(ip);
    	application.setPort(port);
        et1.setText(ip);
        et2.setText(String.valueOf(port));
        bt.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					ip=et1.getText().toString();
					poster=et2.getText().toString();
					if (ip.equals("")||poster.equals(""))
					{
					Toast.makeText(MainActivity.this,"ip���߶˿�Ϊ��", Toast.LENGTH_SHORT).show();
					flag=1;
					}
					// TODO: handle exception
				if (flag==0)
				{
				port= Integer.valueOf(poster).intValue();
			    showRemoteFileHandler=new ShowRemoteFileHandler(MainActivity.this,lv);
			    cmdClientSocket=new CmdClientSocket(ip,port,showRemoteFileHandler,MainActivity.this);
			    cmdClientSocket.work("dir:c:\\");
			    saveData(MainActivity.this);
				}
				flag=0;
				
			}
		});
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ShowRemoteFileHandler showRemoteFileHandler = new ShowRemoteFileHandler(MainActivity.this, lv);//�����ListView�ľ��
				ShowNonUiUpdateCmdHandler showNonUiUpdateCmdHandler = new ShowNonUiUpdateCmdHandler(MainActivity.this);//ֱ��Toast��ʾ�ľ����������ListView
				NetFileData fileData = (NetFileData) arg0.getItemAtPosition(arg2);
				String pwd=fileData.getFilePath();
				String filePath="";
				if(pwd.endsWith("/")|pwd.endsWith("\\")){
					//�ļ�·�����ܴ�"/"��β������"c://aaa/b/"Ҳ������"c://aaa/b"�����Ҫ������ȫ
					//����Windowsϵͳ��Linuxϵͳ�ļ��зָ�����ͬ������Щϵͳ���ļ�Ŀ¼�ı�ʾ��"c:\\\\aaa\\b\\"��ע��"\\"ת���"\"
					filePath=fileData.getFileName();
				}else{
					filePath=File.separator+fileData.getFileName();
				}
				//Log.e("2521212",filePath);
				if(fileData.getFileType()==1||fileData.getFileType()==2){
		
					if(fileData.getFileName().equals("...")){
						//�����Ŀ¼���г������̷�
						filePath="...";
					}
					CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,showRemoteFileHandler,MainActivity.this);
					cmdClientSocket.work("dir:"+filePath);
				}else{
					CmdClientSocket cmdClientSocket = new CmdClientSocket(ip,port,showNonUiUpdateCmdHandler,MainActivity.this);
					cmdClientSocket.work("opn:"+filePath);

				}

			}
				
		});
    }

    public  void loadData(Context context){//ͨ��SharedPreferencesȡ����
		//��Ҫ�����Ĵ��ݽ�����ͨ�������������SharedPreferences����
		SharedPreferences sp=context.getSharedPreferences(context.getClass().getCanonicalName(), Context.MODE_PRIVATE) ;
		//sharedPreferences��һ���������ַ�������Ӧһ��xml�ļ����ļ����ƣ�����ɴ���context��Ӧ�������
		ip = sp.getString(KEY_IP, "106.14.119.67");
		port = sp.getInt(KEY_PORT, 8019);
	}
	public  void saveData(Context context){//ͨ��SharedPreferences������
		//��Ҫ�����Ĵ��ݽ�����ͨ�������������SharedPreferences����
		SharedPreferences sp=context.getSharedPreferences(context.getClass().getCanonicalName(), Context.MODE_PRIVATE) ;
		//sharedPreferences��һ���������ַ�������Ӧһ��xml�ļ����ļ����ƣ�����ɴ���context��Ӧ�������
		Editor editor = sp.edit();
		editor.putString(KEY_IP,ip );//KEY_IP,KEY_PORT��Ϊ�Զ���String���ͳ���
		editor.putInt(KEY_PORT, port);
		editor.commit();
	}
	@Override
    	public void onCreateContextMenu(ContextMenu menu, View v,
    			ContextMenuInfo menuInfo) {
    		// TODO Auto-generated method stub
    	    getMenuInflater().inflate(R.menu.action_menu, menu);
    		super.onCreateContextMenu(menu, v, menuInfo);
    	}
    @Override
    	public boolean onContextItemSelected(MenuItem item) {
    		// TODO Auto-generated method stub
    	AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		int pos=contextMenuInfo.position;
		NetFileData netFileData=(NetFileData) lv.getAdapter().getItem(pos);//����listViewΪ��ʾ�ļ��б����ͼ
		switch(item.getItemId()){
		case R.id.action_hotkey:// �����ȼ��Ի���
			showHotKeyDialog(netFileData);//�ܸ���netFileData���;���������Ӧ���ȼ��Ի���
			break;
		case R.id.action_mouse:
			showMouseActivity();
			break;
		case R.id.action_download:
			download(netFileData);
			break;
		default :break;
		}
    		return super.onContextItemSelected(item);
    	}
	private void showMouseActivity() {
		// TODO Auto-generated method stub
    	Intent intent = new Intent(MainActivity.this, MouseActivity.class);
        startActivity(intent);
	}

	public void showHotKeyDialog(NetFileData netFileData){
		CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,showRemoteFileHandler,MainActivity.this);
		//showNonUiUpdateCmdHandler���������socket�Ľ�����Ϣ����Զ�̷������ȷִ����������κ�UI���£���Զ�̷���˳���Toast��ʾ������Ϣ
		new HotKeyDialog(MainActivity.this, HotKeyGenerator.getHotkeyList(netFileData), "�ȼ�������", cmdClientSocket).show();
		//HotKeyDialog�Ĺ��캯��Ϊ��public HotKeyDialog(Context context,ArrayList<HotKeyData> hotKeyList,String title,CmdClientSocket cmdClientSocket)
		//����Context contextΪ������
		//ArrayList<HotKeyData> hotKeyList,������ȼ��б��ȼ�����HotKeyDataΪ�Զ����࣬�����ȼ��������Լ��ȼ���Ӧ�Ĳ���
		//����HotKeyData("�˳�����", "key:vk_alt+vk_f4")������һ������Ϊ"�˳�����"��ͨ������"key:vk_alt+vk_f4"ʵ��alt+f4���ȼ�����
		//HotKeyGenerator.getHotkeyList(netFileData)Ϊ��̬����
		//public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData),����fileData���;�������ʲô�����ȼ�����
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }
    @Override
    	public boolean onOptionsItemSelected(MenuItem item) {
    		// TODO Auto-generated method stub
    	int id = item.getItemId();
		if (id == R.id.action_combination) {
			showCombinationActivity();
			return true;
		}
		if (id == R.id.action_local)
		{
			showLocalActivity();
			return true;
		}
    		return super.onOptionsItemSelected(item);
    	}

	private void showLocalActivity() {
		// TODO Auto-generated method stub
		Intent intent2 = new Intent(MainActivity.this, LocalActivity.class);
        startActivity(intent2);
	}

	private void showCombinationActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, CombinationActivity.class);
        startActivity(intent);
	}
	 private void download(NetFileData netFileData) {
			// TODO Auto-generated method stub
			if (netFileData.getFileType()==1||netFileData.getFileType()==2)
			{
				Toast.makeText(MainActivity.this, "��δʵ�������ļ������ع���",Toast.LENGTH_LONG).show();
			}
			else
			{
				FileTransferBeginHandler filetransferbeginhandler=new FileTransferBeginHandler(MainActivity.this, netFileData,ip);
				CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,filetransferbeginhandler,MainActivity.this);
				cmdClientSocket.work("dlf:" + netFileData.getFileName());
			}
		}
    
}
