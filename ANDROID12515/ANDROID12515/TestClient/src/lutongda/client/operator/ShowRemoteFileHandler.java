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

public class ShowRemoteFileHandler extends Handler{

	private Context context;
	private ListView lv;
	public ShowRemoteFileHandler(Context context, ListView lv) {
		super();
		this.context = context;
		this.lv = lv;
	}
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		ArrayList<NetFileData> netFileList = new ArrayList<NetFileData>();
		 Log.e("datalen",String.valueOf(netFileList.size()));
		Bundle bundle = msg.getData();
		int msgType=msg.arg2;
		ArrayList<String> ack = bundle
				.getStringArrayList(CmdClientSocket.KEY_SERVER_ACK_MSG);
		if(msgType==CmdClientSocket.SERVER_MSG_OK){

			String filePath = ack.get(0);// 第0个位置是文件列表的路径
			Log.e("file path is ..........",filePath);
			for (int i = 1; i < ack.size(); i++) {
				String fileInfo = ack.get(i);
				NetFileData netFileData = new NetFileData(fileInfo, filePath);
				netFileList.add(netFileData);
			}
			NetFileListAdpter adapter = new NetFileListAdpter(context,
					netFileList);
			lv.setAdapter(adapter);// 直接在句柄里实现文件列表视图更新
		}else{
			Toast.makeText(context, ack.toString(), Toast.LENGTH_SHORT).show();
		}

		super.handleMessage(msg);
	}
	
}
