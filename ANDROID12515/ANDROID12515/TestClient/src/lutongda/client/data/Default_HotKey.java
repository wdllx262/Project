package lutongda.client.data;

import java.util.ArrayList;

public class Default_HotKey {
	public  ArrayList<HotKeyData> getHotkeyList()
	{
		 ArrayList<HotKeyData> list = new ArrayList<HotKeyData>();

	        HotKeyData data1 = new HotKeyData("�л�����","VK_ALT+VK_TAB,VK_TAB+VK_ALT");
	        list.add(data1);
	        HotKeyData data2 = new HotKeyData("�˳�","VK_ALT+VK_F4");
	        list.add(data2);
	        HotKeyData data3 = new HotKeyData("���","VK_ALT+VK_SPACE+VK_X");
	        list.add(data3);
	        HotKeyData data4 = new HotKeyData("��ԭ","VK_ALT+VK_SPACE+VK_R");
	        list.add(data4);
	        HotKeyData data5 = new HotKeyData("��С��","VK_ALT+VK_SPACE+VK_N");   //��С��
	        list.add(data5);
	        return list;
	}
}
