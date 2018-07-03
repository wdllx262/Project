package wzu.server.operator;

import java.awt.Robot;
import java.util.ArrayList;

public class ROL extends BaseOperator{
	private  Robot robot;
	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		robot=new Robot();
		int value=Integer.parseInt(cmdBody);
		robot.mouseWheel(value);
		//ackMsg.add(String.valueOf(2));
		ackMsg.add("ok");
    	ackMsg.add("rol:"+cmdBody);
		return ackMsg;

	}

}
