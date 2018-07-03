package wzu.server.operator;

import java.util.ArrayList;

public class SLP extends BaseOperator{

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		int sleeptime=Integer.parseInt(cmdBody);
		Thread.sleep(sleeptime);
		ackMsg.add("ok");
    	ackMsg.add("slp:"+cmdBody);
		return ackMsg;
	}

}
