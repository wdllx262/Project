package lutongda.client.data;

import java.util.ArrayList;

public class Movie_HotKey {
	public  ArrayList<HotKeyData> getHotkeyList()
	{
		 ArrayList<HotKeyData> list = new ArrayList<HotKeyData>();

		     HotKeyData data1 = new HotKeyData("���","VK_ALT+VK_SPACE+VK_X");
	        list.add(data1);
	        HotKeyData data2 = new HotKeyData("��ͣ����","VK_SPACE");
	        list.add(data2);
	        HotKeyData data3 = new HotKeyData("���","VK_RIGHT");
	        list.add(data3);
	        HotKeyData data4 = new HotKeyData("����","VK_LEFT");
	        list.add(data4);
	        HotKeyData data5 = new HotKeyData("����+","VK_UP");
	        list.add(data5);
	        HotKeyData data6 = new HotKeyData("����-","VK_DOWN");
	        list.add(data6);
	        HotKeyData data7 = new HotKeyData("����","VK_CTRL+VK_M");
	        list.add(data7);
	        HotKeyData data8 = new HotKeyData("�˳�","VK_ALT+VK_F4");
	        list.add(data8);
	        HotKeyData data9 = new HotKeyData("�л�����","VK_ALT+VK_TAB,VK_TAB+VK_ALT");
	        list.add(data9);

	        return list;
	}
}
