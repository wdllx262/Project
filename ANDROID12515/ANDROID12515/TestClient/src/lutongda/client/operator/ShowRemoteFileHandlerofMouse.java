package lutongda.client.operator;

import java.util.ArrayList;

import lutongda.client.socket.CmdClientSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

public class ShowRemoteFileHandlerofMouse extends Handler{
	private Context context;
	public ShowRemoteFileHandlerofMouse(Context context) {
		super();
		this.context = context;
	}
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle bundle = msg.getData();
		int msgType=msg.arg2;
		ArrayList<String> ack = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
		Toast.makeText(context, ack.toString(), Toast.LENGTH_SHORT).show();
		super.handleMessage(msg);
	}
	
}
