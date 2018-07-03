package lutongda.client.data;

import java.util.ArrayList;

public class Default_HotKey {
	public  ArrayList<HotKeyData> getHotkeyList()
	{
		 ArrayList<HotKeyData> list = new ArrayList<HotKeyData>();

	        HotKeyData data1 = new HotKeyData("切换程序","VK_ALT+VK_TAB,VK_TAB+VK_ALT");
	        list.add(data1);
	        HotKeyData data2 = new HotKeyData("退出","VK_ALT+VK_F4");
	        list.add(data2);
	        HotKeyData data3 = new HotKeyData("最大化","VK_ALT+VK_SPACE+VK_X");
	        list.add(data3);
	        HotKeyData data4 = new HotKeyData("还原","VK_ALT+VK_SPACE+VK_R");
	        list.add(data4);
	        HotKeyData data5 = new HotKeyData("最小化","VK_ALT+VK_SPACE+VK_N");   //最小化
	        list.add(data5);
	        return list;
	}
}
