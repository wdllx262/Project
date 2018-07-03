package lutongda.client.data;

import java.util.ArrayList;

public class PPT_HotKey {
	public  ArrayList<HotKeyData> getHotkeyList()
	{
		ArrayList<HotKeyData>  lst=new ArrayList<HotKeyData>();

		HotKeyData data=new HotKeyData("切换程序","VK_ALT+VK_TAB,VK_TAB+VK_ALT");
		lst.add(data);
		HotKeyData data2=new HotKeyData("ESC","VK_ESCAPE");
		lst.add(data2);
		HotKeyData data3=new HotKeyData("上一页","VK_UP");
		lst.add(data3);
		HotKeyData data4=new HotKeyData("下一页","VK_DOWN");
		lst.add(data4);
		HotKeyData data5=new HotKeyData("从头放映","VK_F5");
		lst.add(data5);
		HotKeyData data6=new HotKeyData("当前放映","VK_SHIFT+VK_F5");
		lst.add(data6);
		 HotKeyData data7 = new HotKeyData("最大化","VK_ALT+VK_SPACE+VK_X");
		lst.add(data7);
		HotKeyData data8=new HotKeyData("黑屏/正常","VK_SPACE");
		lst.add(data8);
		return lst;
	}
}
