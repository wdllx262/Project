package wzu.server.operator;

import java.util.ArrayList;

public class CMD extends BaseOperator{

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		Runtime.getRuntime().exec("cmd /c start "+cmdBody);
		ackMsg.add("ok");
    	ackMsg.add("cmd:"+cmdBody);
		return ackMsg;
	}

}
