package wzu.server.operator;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.ArrayList;

public class MOV extends BaseOperator{
	private  Robot robot;
	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg=new ArrayList<String>();
		robot=new Robot();
		Dimension winSize=Toolkit.getDefaultToolkit().getScreenSize();
		int winWidth=(int) winSize.getWidth();
		int winHeight=(int) winSize.getHeight();
		int splitIdx = cmdBody.indexOf(",");
		String strx=cmdBody.substring(0, splitIdx);
	    String stry=cmdBody.substring(splitIdx+1);
	    int dx=Integer.parseInt(strx);
	    int dy=Integer.parseInt(stry);
	    Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
	    int nowx=point.x;
	    int nowy=point.y;
	    int ex=point.x+dx;
	    int ey=point.y+dy;
	    boolean ok=true;
	    if (ex<0||ey<0||ex>winWidth||ey>winHeight) ok=false;
	    if (ok==true)
	    {
	    	//ackMsg.add(String.valueOf(2));
	    	ackMsg.add("ok");
	    	ackMsg.add("mov:"+String.valueOf(ex)+","+String.valueOf(ey));
	    	robot.mouseMove(ex, ey);
	    }
	    else {
	    	{
	    		//ackMsg.add(String.valueOf(1));
		    	ackMsg.add("Beyond the display range!");
	    	}
		}
		return ackMsg;
	}
	public  MOV(){
		super();
	}   
}
