package wzu.server.operator;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.ArrayList;

public class MVA extends BaseOperator{
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
	    double dx=Double.parseDouble(strx);
	    double dy=Double.parseDouble(stry);
	    Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
	    int nowx=point.x;
	    int nowy=point.y;
	    int ex=(int)(dx*winWidth+nowx);
	    int ey=(int)(dy*winHeight+nowy);
	    boolean ok=true;
	    if (ex<0||ey<0||ex>winWidth||ey>winHeight) ok=false;
	    if (ok==true)
	    {
	    	//ackMsg.add(String.valueOf(2));
	    	ackMsg.add("ok");
	    	ackMsg.add("mva:"+String.valueOf(ex)+","+String.valueOf(ey));
	    	robot.mouseMove(ex, ey);
	    }
	    else {
	    	{
	    		if (ex>winWidth&&ey<=winHeight&&ey>=0)
	    		{
	    			robot.mouseMove(winWidth, ey);
	    		}else if (ex<=winWidth&&ey>winHeight&&ex>=0)
	    		{
	    			robot.mouseMove(ex, winHeight);
	    		}else if (ex<0&&ey>=0&&ey<=winHeight)
	    			robot.mouseMove(0, ey);
	    		else if (ex<=winWidth&&ey<0&&ex>=0)
	    			robot.mouseMove(ex,0);
	    		else if (ex<0&&ey<0)
	    			robot.mouseMove(0,0);
	    		else if (ex<0&&ey>winHeight)
	    			robot.mouseMove(0,winHeight);
	    		else if (ex>winWidth&&ey<0)
	    			robot.mouseMove(winWidth,0);
	    		else robot.mouseMove(winWidth,winHeight);
	    		//ackMsg.add(String.valueOf(1));
		    	ackMsg.add("Beyond the display range!");
	    	}
		}
		return ackMsg;
	}
	public  MVA(){
		super();
	}   

}
