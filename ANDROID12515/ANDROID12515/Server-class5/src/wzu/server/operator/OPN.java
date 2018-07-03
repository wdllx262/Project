package wzu.server.operator;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;

public class OPN extends BaseOperator{

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		Desktop desktop=Desktop.getDesktop();
		File file=new File(cmdBody);
		desktop.open(file);
		ackMsg.add("ok");
		ackMsg.add("OPN:"+cmdBody);
		return null;
	}
     
}
