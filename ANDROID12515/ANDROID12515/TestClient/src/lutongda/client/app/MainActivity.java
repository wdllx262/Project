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
					Toast.makeText(MainActivity.this,"ip或者端口为空", Toast.LENGTH_SHORT).show();
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
				ShowRemoteFileHandler showRemoteFileHandler = new ShowRemoteFileHandler(MainActivity.this, lv);//会更新ListView的句柄
				ShowNonUiUpdateCmdHandler showNonUiUpdateCmdHandler = new ShowNonUiUpdateCmdHandler(MainActivity.this);//直接Toast显示的句柄，不更新ListView
				NetFileData fileData = (NetFileData) arg0.getItemAtPosition(arg2);
				String pwd=fileData.getFilePath();
				String filePath="";
				if(pwd.endsWith("/")|pwd.endsWith("\\")){
					//文件路径可能带"/"结尾，例如"c://aaa/b/"也可能是"c://aaa/b"因此需要考虑周全
					//另外Windows系统和Linux系统文件夹分隔符不同，对有些系统其文件目录的表示是"c:\\\\aaa\\b\\"，注意"\\"转义成"\"
					filePath=fileData.getFileName();
				}else{
					filePath=File.separator+fileData.getFileName();
				}
				//Log.e("2521212",filePath);
				if(fileData.getFileType()==1||fileData.getFileType()==2){
		
					if(fileData.getFileName().equals("...")){
						//处理根目录，列出所有盘符
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

    public  void loadData(Context context){//通过SharedPreferences取数据
		//需要上下文传递进来，通过上下文来获得SharedPreferences对象
		SharedPreferences sp=context.getSharedPreferences(context.getClass().getCanonicalName(), Context.MODE_PRIVATE) ;
		//sharedPreferences第一个参数是字符串，对应一个xml文件的文件名称，这里干脆用context对应类的名称
		ip = sp.getString(KEY_IP, "106.14.119.67");
		port = sp.getInt(KEY_PORT, 8019);
	}
	public  void saveData(Context context){//通过SharedPreferences存数据
		//需要上下文传递进来，通过上下文来获得SharedPreferences对象
		SharedPreferences sp=context.getSharedPreferences(context.getClass().getCanonicalName(), Context.MODE_PRIVATE) ;
		//sharedPreferences第一个参数是字符串，对应一个xml文件的文件名称，这里干脆用context对应类的名称
		Editor editor = sp.edit();
		editor.putString(KEY_IP,ip );//KEY_IP,KEY_PORT等为自定义String类型常量
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
		NetFileData netFileData=(NetFileData) lv.getAdapter().getItem(pos);//其中listView为显示文件列表的视图
		switch(item.getItemId()){
		case R.id.action_hotkey:// 弹出热键对话框
			showHotKeyDialog(netFileData);//能根据netFileData类型决定弹出相应的热键对话框
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
		//showNonUiUpdateCmdHandler句柄，处理socket的接收信息，若远程服务端正确执行命令，不做任何UI更新；若远程服务端出错，Toast显示出错信息
		new HotKeyDialog(MainActivity.this, HotKeyGenerator.getHotkeyList(netFileData), "热键操作表", cmdClientSocket).show();
		//HotKeyDialog的构造函数为：public HotKeyDialog(Context context,ArrayList<HotKeyData> hotKeyList,String title,CmdClientSocket cmdClientSocket)
		//其中Context context为上下文
		//ArrayList<HotKeyData> hotKeyList,传入的热键列表，热键对象HotKeyData为自定义类，具有热键的名称以及热键对应的操作
		//例如HotKeyData("退出程序", "key:vk_alt+vk_f4")则构造了一个名称为"退出程序"，通过命令"key:vk_alt+vk_f4"实现alt+f4的热键操作
		//HotKeyGenerator.getHotkeyList(netFileData)为静态方法
		//public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData),根据fileData类型决定产生什么样的热键数据
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
				Toast.makeText(MainActivity.this, "暂未实现整个文件夹下载功能",Toast.LENGTH_LONG).show();
			}
			else
			{
				FileTransferBeginHandler filetransferbeginhandler=new FileTransferBeginHandler(MainActivity.this, netFileData,ip);
				CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,filetransferbeginhandler,MainActivity.this);
				cmdClientSocket.work("dlf:" + netFileData.getFileName());
			}
		}
    
}
