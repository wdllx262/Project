package lutongda.client.data;

import java.util.ArrayList;

public class Movie_HotKey {
	public  ArrayList<HotKeyData> getHotkeyList()
	{
		 ArrayList<HotKeyData> list = new ArrayList<HotKeyData>();

		     HotKeyData data1 = new HotKeyData("最大化","VK_ALT+VK_SPACE+VK_X");
	        list.add(data1);
	        HotKeyData data2 = new HotKeyData("暂停播放","VK_SPACE");
	        list.add(data2);
	        HotKeyData data3 = new HotKeyData("快进","VK_RIGHT");
	        list.add(data3);
	        HotKeyData data4 = new HotKeyData("快退","VK_LEFT");
	        list.add(data4);
	        HotKeyData data5 = new HotKeyData("音量+","VK_UP");
	        list.add(data5);
	        HotKeyData data6 = new HotKeyData("音量-","VK_DOWN");
	        list.add(data6);
	        HotKeyData data7 = new HotKeyData("静音","VK_CTRL+VK_M");
	        list.add(data7);
	        HotKeyData data8 = new HotKeyData("退出","VK_ALT+VK_F4");
	        list.add(data8);
	        HotKeyData data9 = new HotKeyData("切换程序","VK_ALT+VK_TAB,VK_TAB+VK_ALT");
	        list.add(data9);

	        return list;
	}
}
