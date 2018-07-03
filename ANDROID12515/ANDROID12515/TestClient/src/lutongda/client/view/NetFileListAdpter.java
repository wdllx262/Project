package lutongda.client.view;
import com.example.testclient.R;
import java.util.ArrayList;

import lutongda.client.data.Default_HotKey;
import lutongda.client.data.Movie_HotKey;
import lutongda.client.data.NetFileData;
import lutongda.client.data.PPT_HotKey;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NetFileListAdpter extends ArrayAdapter<NetFileData>{
	private ArrayList<NetFileData> netFileList;
	private Context context;
	public NetFileListAdpter(Context context, ArrayList<NetFileData> netFileList) {
		super(context, android.R.layout.simple_expandable_list_item_1,netFileList);
		this.netFileList = netFileList;
		this.context = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int image_folder_id=R.drawable.folder;
		int image_file_id=R.drawable.file;
		int image_disk_id=R.drawable.disk;
		int image_video_id=R.drawable.video;
		int image_ppt_id=R.drawable.picture_ppt;
		if (convertView==null)
		{
			convertView=LayoutInflater.from(context).inflate(R.layout.file_row_view,null,false);
		}
		
		ImageView iv=(ImageView)convertView.findViewById(R.id.imageView1);
		TextView tv1=(TextView)convertView.findViewById(R.id.textView1);
		TextView tv2=(TextView)convertView.findViewById(R.id.textView2);
		TextView tv3=(TextView)convertView.findViewById(R.id.textView3);
		NetFileData fileData=netFileList.get(position);
		String filename=fileData.getFileName();
		int splitIdx = filename.indexOf(".");
		String idStr=filename.substring(splitIdx+1);
		if (fileData.getFileType()==1)
		{
			iv.setImageResource(image_folder_id);
			tv1.setText("");
		}
		else if (fileData.getFileType()==0)
		{
			if (idStr.equals("avi")||idStr.equals("mp4")) 
			{
				iv.setImageResource(image_video_id);
				tv1.setText(fileData.getFileSizeStr());
			}else if (idStr.equals("pptx")||idStr.equals("ppt")) 
			{
				iv.setImageResource(image_ppt_id);
				tv1.setText(fileData.getFileSizeStr());
			}
			else
			{
			iv.setImageResource(image_file_id);
			tv1.setText(fileData.getFileSizeStr());
			}
		}
		else if (fileData.getFileType()==2)
		{
			iv.setImageResource(image_disk_id);
			tv1.setText(fileData.getFileSizeStr());
		} 
		tv2.setText(fileData.getFileName());
		tv3.setText(fileData.getFileModifiedDate());
		return convertView;
	}
    

}
