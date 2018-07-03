package lutongda.client.view;

import java.util.ArrayList;
import java.util.List;

import com.example.testclient.R;

import lutongda.client.data.HotKeyData;
import lutongda.client.socket.CmdClientSocket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HotKeyGridAdapter extends ArrayAdapter<HotKeyData>{
	Context context;
	ArrayList<HotKeyData> list;
	CmdClientSocket cmdClientSocket;
	public HotKeyGridAdapter(Context context,  ArrayList<HotKeyData> list,CmdClientSocket cmdClientSocket)
	{
		 super(context, android.R.layout.select_dialog_item,list);
		this.context=context;
		this.list=list;
		this.cmdClientSocket=cmdClientSocket;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.d("-----222------",list.get(0).getHotkeyName());
		if(convertView == null)
			convertView=LayoutInflater.from(context).inflate(R.layout.hotkey_row_view,null,false);
		 TextView tv=(TextView) convertView.findViewById(R.id.text);
		 final HotKeyData fileData=list.get(position);
		 tv.setText(fileData.getHotkeyName());
		  tv.setOnClickListener(new TextView.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					clientSocket.work("key:" + list.get(position).getHotkeyCmd());
					cmdClientSocket.work("key:" + fileData.getHotkeyCmd());
				}
			});
		 return convertView;
	}
	
}
