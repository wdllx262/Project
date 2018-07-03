package lutongda.client.view;

import java.util.ArrayList;

import com.example.testclient.R;

import lutongda.client.data.HotKeyData;
import lutongda.client.socket.CmdClientSocket;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

public class HotKeyDialog  {
	Context context;
	ArrayList<HotKeyData> hotKeyList;
	String title;
	CmdClientSocket cmdClientSocket;
	public HotKeyDialog(Context context,ArrayList<HotKeyData> hotKeyList,String title,CmdClientSocket cmdClientSocket)
	{
		this.context=context;
		this.hotKeyList=hotKeyList;
		this.title=title;
		this.cmdClientSocket=cmdClientSocket;
	}
	public void show() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		final AlertDialog dialog=builder.create();
		View view=View.inflate(context, R.layout.mydialog,null);
		dialog.setView(view);
		GridView gridView=(GridView) view.findViewById(R.id.gridView1);
		HotKeyGridAdapter adapter=new HotKeyGridAdapter(context, hotKeyList, cmdClientSocket);
		gridView.setAdapter(adapter);
		dialog.setTitle(title);
		dialog.show();
	
	}
    
	
}
