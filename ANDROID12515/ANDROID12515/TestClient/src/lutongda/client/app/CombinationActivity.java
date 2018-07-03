package lutongda.client.app;

import lutongda.client.operator.MySqliteOpenHelper;
import lutongda.client.operator.ShowRemoteFileHandlerofMouse;
import lutongda.client.socket.CmdClientSocket;
import lutongda.client.view.MyCursorAdapter;

import com.example.testclient.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;

public class CombinationActivity extends Activity {
	ListView lv;
	SQLiteDatabase db;
	Cursor cursor;
	MyCursorAdapter adapter;
	String ip;
	int port;
	long _id;
	ShowRemoteFileHandlerofMouse  showRemoteFileHandler;
    CmdClientSocket cmdClientSocket;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.combination_main);
		Application application = (Application) getApplication();
		ip = application.getIp();
		port = application.getPort();
		showRemoteFileHandler = new ShowRemoteFileHandlerofMouse(CombinationActivity.this);
        cmdClientSocket = new CmdClientSocket(ip, port,showRemoteFileHandler,CombinationActivity.this);
		initWork();
	}

	private void initWork() {
		// TODO Auto-generated method stub
		lv = (ListView) findViewById(R.id.combination_listview);
		db = new MySqliteOpenHelper(this).getWritableDatabase();
		cursor = db.rawQuery("select * from " + MySqliteOpenHelper.TABLE_NAME,
				null);
		adapter = new MyCursorAdapter(this, cursor);
		lv.setAdapter(adapter);
		registerForContextMenu(lv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.combination_menu, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.combination_menu, menu);
		AdapterContextMenuInfo adaptercontextmenuinfo = (AdapterContextMenuInfo) menuInfo;
		_id = adaptercontextmenuinfo.id;
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		processMenuItem(item);
		return super.onContextItemSelected(item);
	}

	private void processMenuItem(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_add:
			processAdd();
			break;
		case R.id.action_edit:
			processEdit();
			break;
		case R.id.action_delete:
			processDel();
			break;
		case R.id.action_send:
			processSend();
			break;
		default:
			break;
		}
	}

	private void processSend() {
		// TODO Auto-generated method stub
		Cursor cursor2 = db.rawQuery("select * from "
				+ MySqliteOpenHelper.TABLE_NAME + " where _id=" + _id, null);
		cursor2.moveToFirst();
		int Index1 = cursor2.getColumnIndex(MySqliteOpenHelper.KEY_NAME);
		int Index2 = cursor2.getColumnIndex(MySqliteOpenHelper.KEY_ACTION_KEY);
		String name = cursor2.getString(Index1);
		String action_key = cursor2.getString(Index2);
		String[] keyPressArray =  action_key.split("\\|");
		for(int i=0;i<keyPressArray.length;i++){//按“+”的顺序按下按键
			  //Log.e("information",keyPressArray[i] );
			  cmdClientSocket.work(keyPressArray[i]);
		      try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	private void processDel() {
		// TODO Auto-generated method stub
		db.delete(MySqliteOpenHelper.TABLE_NAME,"_id=?",new String[]{""+_id});
		adapter.getCursor().requery();
	}

	private void processEdit() {
		// TODO Auto-generated method stub
		Cursor cursor2 = db.rawQuery("select * from "
				+ MySqliteOpenHelper.TABLE_NAME + " where _id=" + _id, null);
		cursor2.moveToFirst();
		int Index1 = cursor2.getColumnIndex(MySqliteOpenHelper.KEY_NAME);
		int Index2 = cursor2.getColumnIndex(MySqliteOpenHelper.KEY_ACTION_KEY);
		String name = cursor2.getString(Index1);
		String action_key = cursor2.getString(Index2);
		View v = LayoutInflater.from(this).inflate(R.layout.add_edit, null);
		final EditText et1 = (EditText) v.findViewById(R.id.EditText1);
		final EditText et2 = (EditText) v.findViewById(R.id.EditText2);
		et1.setText(name);
		et2.setText(action_key);
		new AlertDialog.Builder(this).setTitle("修改信息").setView(v).setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String name=et1.getText().toString();
				String action_key=et2.getText().toString();
				db_edit(name,action_key);
				
			}

		}).setNegativeButton("取消", null).show();
	}

	private void processAdd() {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(this).inflate(R.layout.add_edit, null);
		final EditText et1 = (EditText) v.findViewById(R.id.EditText1);
		final EditText et2 = (EditText) v.findViewById(R.id.EditText2);
		new AlertDialog.Builder(this).setTitle("添加组合").setView(v)
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String name = et1.getText().toString();
						String action_key = et2.getText().toString();
						db_add(name, action_key);

					}
				}).setNegativeButton("取消", null).show();
	}

	ContentValues encodeCV(String name, String action_key) {
		ContentValues cv = new ContentValues();
		cv.put(MySqliteOpenHelper.KEY_NAME, name);
		cv.put(MySqliteOpenHelper.KEY_ACTION_KEY, action_key);
		return cv;
	}

	void db_add(String name, String action_key) {
		// TODO Auto-generated method stub
		db.insert(MySqliteOpenHelper.TABLE_NAME, MySqliteOpenHelper.KEY_NAME,
				encodeCV(name, action_key));
		adapter.getCursor().requery();
	}

	void db_edit(String name, String action_key) {
		// TODO Auto-generated method stub
		db.update(MySqliteOpenHelper.TABLE_NAME, encodeCV(name, action_key),
				"_id=" + _id, null);
		adapter.getCursor().requery();
	}
	
}
