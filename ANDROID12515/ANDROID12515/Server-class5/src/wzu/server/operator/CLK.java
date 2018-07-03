package wzu.server.operator;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

public class CLK extends BaseOperator{
	private  Robot robot;
	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		robot=new Robot();
		if (cmdBody.equals("left")) 
		{
			Pressleft();
			Realseleft();
		}
		else if (cmdBody.equals("right"))
		{
			Pressright();
			Realseright();
		}
		else if (cmdBody.equals("left_press")) Pressleft();
		else if (cmdBody.equals("right_press")) Pressright();
		else if (cmdBody.equals("left_release")) Realseleft();
		else if (cmdBody.equals("right_release")) Realseright();
		//ackMsg.add(String.valueOf(2));
    	ackMsg.add("ok");
    	ackMsg.add("clk:"+cmdBody);
		return ackMsg;
	}
	private void Realseright() {
		// TODO Auto-generated method stub
		  robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}
	private void Pressright() {
		// TODO Auto-generated method stub
		 robot.mousePress(InputEvent.BUTTON3_MASK);
	}
	private void Realseleft() {
		// TODO Auto-generated method stub
	    robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	private void Pressleft() {
		// TODO Auto-generated method stub
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}
	public  CLK(){
		super();
	}   
}
