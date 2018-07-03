package wzu.server.operator;

import java.awt.Robot;
import java.util.ArrayList;

public class KEY extends BaseOperator {
	private  Robot robot;
	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		// cmdBody�����ж��ţ���ʾ��ϼ�������Ϊ����
		// ��ϼ�ʾ����VK_ALT+VK_TAB,VK_TAB+VK_ALT ��ʾ�Ȱ���alt�����ٰ���tab�������ͷ�tab�������ͷ�alt��
		// ����ǰ���+��ʾ���¼���˳�򣬶��ź����+��ʾ�ͷż���˳��
		ArrayList<String> ackMsg = new ArrayList<String>();
		ackMsg.add("ok");
		robot=new Robot();
		int splitIdx = cmdBody.indexOf(",");
		//���жϼ��������޴�","�ģ����У����¼���˳����ͷż���˳���ɶ���ǰ�ġ�+���Ͷ��ź�ġ�+��������˳��
		if (splitIdx < 1) {
			int splitIdx2 = cmdBody.indexOf("+");//����+�����ָ���ϼ�
			if(splitIdx2<1){
				singleKeyPress(cmdBody);//û�С�+���ű�ʾ������
			}else{
				simpleComboKeyPress(cmdBody);//��ϼ�
			}
		}else{
			String keyPressStr=cmdBody.substring(0, splitIdx);//ȡ0-��spisplitIdx-1���������ɵ����ַ���
			String keyReleaseStr=cmdBody.substring(splitIdx+1);//ȡsplitIdx+1����β�����ַ���
			comboKeyPress(keyPressStr,keyReleaseStr);
		}

		ackMsg.add("key:"+cmdBody);
		return null;
	}
	private void simpleComboKeyPress(String keyPressStr){
		String[] keyPressArray = keyPressStr.split("\\+");
		//split����ַ�����������ʽ���� "+����������ʽ�Ĺؼ��ʣ�����ֱ���ã���Ҫת�壬��\������ת�壬������Ҫ\\����ʾ
		for(int i=0;i<keyPressArray.length;i++){//����+����˳���°���
			int keycode = VisualKeyMap.getVisualKey(keyPressArray[i]);
			robot.keyPress(keycode);
			
		}
		for(int i=keyPressArray.length-1;i>=0;i--){//�����ͷŰ���
			int keycode = VisualKeyMap.getVisualKey(keyPressArray[i]);
			robot.keyRelease(keycode);	
		}
	}

	private  void comboKeyPress(String keyPressStr, String keyReleaseStr) {
		// TODO Auto-generated method stub
		String[] keyPressArray = keyPressStr.split("\\+");
		String[] keyReleaseArray = keyReleaseStr.split("\\+");
		for(int i=0;i<keyPressArray.length;i++){
			int keycode = VisualKeyMap.getVisualKey(keyPressArray[i]);
			robot.keyPress(keycode);
			
		}
		for(int i=0;i<keyReleaseArray.length;i++){
			int keycode = VisualKeyMap.getVisualKey(keyReleaseArray[i]);
			robot.keyRelease(keycode);
		}
	}

	private void singleKeyPress(String cmdBody) {
		// TODO Auto-generated method stub
		int keycode = VisualKeyMap.getVisualKey(cmdBody);
		robot.keyPress(keycode);
		robot.keyRelease(keycode);
		
	}

}

