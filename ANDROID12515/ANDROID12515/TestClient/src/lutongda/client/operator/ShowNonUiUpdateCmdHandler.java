package lutongda.client.operator;

import java.util.ArrayList;

import lutongda.client.data.NetFileData;
import lutongda.client.socket.CmdClientSocket;
import lutongda.client.view.NetFileListAdpter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class ShowNonUiUpdateCmdHandler extends Handler{
	private Context context;
	private ListView lv;
	public ShowNonUiUpdateCmdHandler(Context context) {
		super();
		this.context = context;
		this.lv = lv;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle bundle = msg.getData();
		ArrayList<String> ack = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
			Toast.makeText(context, ack.toString(), Toast.LENGTH_SHORT).show();
		super.handleMessage(msg);
	}
	
	
}
