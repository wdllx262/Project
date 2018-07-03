package wzu.server.operator;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

public class CPS extends BaseOperator{

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		  ArrayList<String> msgBackList=new ArrayList<String>();
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取剪切板
	    Transferable tText = new StringSelection(cmdBody);//cmdBody为String字符串，需要拷贝进剪贴板的内容
	    clip.setContents(tText, null); //设置剪切板内容
	    msgBackList=new KEY().exe("vk_control+vk_v");
		ackMsg.add("ok");
    	ackMsg.add("cps:"+cmdBody);
		return ackMsg;
	}

}
