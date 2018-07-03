package wzu.server.operator;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class VisualKeyMap {
	private static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
	private static final VisualKeyMap VISUAL_KEY_MAP = new VisualKeyMap();

	private VisualKeyMap() {
		hashMap.put("VK_MINUS", KeyEvent.VK_MINUS);
		hashMap.put("VK_PLUS", KeyEvent.VK_PLUS);
		hashMap.put("VK_ESCAPE", KeyEvent.VK_ESCAPE);
		hashMap.put("VK_ENTER", KeyEvent.VK_ENTER);
		hashMap.put("VK_SPACE", KeyEvent.VK_SPACE);
		hashMap.put("VK_DOWN", KeyEvent.VK_DOWN);
		hashMap.put("VK_UP", KeyEvent.VK_UP);
		hashMap.put("VK_LEFT", KeyEvent.VK_LEFT);
		hashMap.put("VK_RIGHT", KeyEvent.VK_RIGHT);
		hashMap.put("VK_INSERT", KeyEvent.VK_INSERT);
		hashMap.put("VK_DELETE", KeyEvent.VK_DELETE);
		hashMap.put("VK_PAGE_DOWN", KeyEvent.VK_PAGE_DOWN);
		hashMap.put("VK_PAGE_UP", KeyEvent.VK_PAGE_UP);
		hashMap.put("VK_TAB", KeyEvent.VK_TAB);
		hashMap.put("VK_F5", KeyEvent.VK_F5);
		hashMap.put("VK_SHIFT", KeyEvent.VK_SHIFT);
		hashMap.put("VK_CONTROL", KeyEvent.VK_CONTROL);
		hashMap.put("VK_W", KeyEvent.VK_W);
		hashMap.put("VK_SPACE", KeyEvent.VK_SPACE);
		hashMap.put("VK_0", KeyEvent.VK_0);//��д��Key���Է���¼�룬�ͻ��˷��ʹ�Сд������
        hashMap.put("VK_WIN", KeyEvent.VK_WINDOWS);
        hashMap.put("VK_ALT", KeyEvent.VK_ALT);
        hashMap.put("VK_C", KeyEvent.VK_C);
        hashMap.put("VK_R", KeyEvent.VK_R);
        hashMap.put("VK_N", KeyEvent.VK_N);
        hashMap.put("VK_X", KeyEvent.VK_X);
        hashMap.put("VK_V", KeyEvent.VK_V);
        hashMap.put("VK_M", KeyEvent.VK_M);
        hashMap.put("VK_D", KeyEvent.VK_D);
        hashMap.put("VK_E", KeyEvent.VK_E);
        hashMap.put("VK_Q", KeyEvent.VK_Q);
        hashMap.put("VK_DELETE", KeyEvent.VK_DELETE);
        hashMap.put("VK_F4", KeyEvent.VK_F4);
        hashMap.put("VK_WINDOWS", KeyEvent.VK_WINDOWS);
}	
	public static int getVisualKey(String key) {
		//����ʱֻ��VisualKeyMap.getVisualKey(String key)����
		return hashMap.get(key.toUpperCase());//��keyתΪ��д
	}
}
