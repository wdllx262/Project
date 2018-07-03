package lutongda.client.operator;



import java.util.ArrayList;

import android.util.Log;

import lutongda.client.data.Default_HotKey;
import lutongda.client.data.HotKeyData;
import lutongda.client.data.Movie_HotKey;
import lutongda.client.data.NetFileData;
import lutongda.client.data.PPT_HotKey;

public class HotKeyGenerator {
	public static ArrayList<HotKeyData> getHotkeyList(NetFileData fileData)
	{
		ArrayList<HotKeyData> lst=new ArrayList<HotKeyData>();
		String filename=fileData.getFileName();
		int splitIdx = filename.indexOf(".");
		String idStr=filename.substring(splitIdx+1);
		if (idStr.equals("avi")||idStr.equals("mp4")) 
		{
			Movie_HotKey ppt=new Movie_HotKey();
			lst=ppt.getHotkeyList();
		}
		else if (idStr.equals("pptx")||idStr.equals("ppt")) 
		{
			PPT_HotKey ppt=new PPT_HotKey();
			lst=ppt.getHotkeyList();
		}
		else 
		{
			Default_HotKey item=new Default_HotKey();
			lst=item.getHotkeyList();	
		}
		return lst;
	}
}
