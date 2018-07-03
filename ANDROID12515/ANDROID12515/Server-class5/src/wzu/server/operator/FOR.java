package wzu.server.operator;

import java.util.ArrayList;

public class FOR extends BaseOperator{

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		ackMsg.add("ok");
    	ackMsg.add("for:"+cmdBody);
		return ackMsg;
	}

}
