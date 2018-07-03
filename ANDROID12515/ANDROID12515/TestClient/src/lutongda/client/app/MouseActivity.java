package lutongda.client.app;

import lutongda.client.operator.ShowRemoteFileHandler;
import lutongda.client.operator.ShowRemoteFileHandlerofMouse;
import lutongda.client.socket.CmdClientSocket;

import com.example.testclient.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MouseActivity extends Activity implements OnTouchListener{
	TextView tv1,tv2;
	Button bt1,bt2,bt3;
	String ip;
	int port;
	int num;
	int type;
	float nowx, nowy;
	float width;
    float height;
    float height2;
    float rolsy;
    float sx,sy;
    int d;
    ShowRemoteFileHandlerofMouse  showRemoteFileHandler;
    CmdClientSocket cmdClientSocket;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mouse_main);
		Application application=(Application) getApplication();
		ip=application.getIp();
		port=application.getPort();
		showRemoteFileHandler = new ShowRemoteFileHandlerofMouse(MouseActivity.this);
        cmdClientSocket = new CmdClientSocket(ip, port,showRemoteFileHandler,MouseActivity.this);
		tv1=(TextView) findViewById(R.id.leftview);
		tv2=(TextView) findViewById(R.id.rightview);
		bt1=(Button) findViewById(R.id.leftbutton);
		bt2=(Button) findViewById(R.id.middlebutton);
		bt3=(Button) findViewById(R.id.rightbutton);
		num=0;
		initwork();
	}
	  private void initwork() {
		// TODO Auto-generated method stub
		  tv1.setOnTouchListener(this);
		  tv2.setOnTouchListener(this);
		  bt1.setOnClickListener(new Button.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cmdClientSocket.work("clk:left");
				}
			});
		  bt2.setOnClickListener(new Button.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (num%2==0) cmdClientSocket.work("clk:left_press");
					else cmdClientSocket.work("clk:left_release");
					num++;
				}
			});
		  bt3.setOnClickListener(new Button.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					cmdClientSocket.work("clk:right");
				}
			});
		  
	}
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	 }
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction()){  
        case MotionEvent.ACTION_DOWN:  
            //System.out.println("---action down-----");  
            //tv1.setText("起始位置为："+"("+event.getX()+" , "+event.getY()+")");  
            nowx=event.getX();
            nowy=event.getY();
            width=tv1.getWidth();
            height=tv1.getHeight();
            float ex,ey;
            ex=nowx/width;
            ey=nowy/height;
            if (v.getId()==tv2.getId())
            {
            	rolsy=nowy;
            }
            else 
            {
            	sx=event.getX();
            	sy=event.getY();
            }
            break;  
        case MotionEvent.ACTION_MOVE:  
            nowx=event.getX();
            nowy=event.getY();
            ex=nowx/width;
            ey=nowy/height;
        	//cmdClientSocket.work("mva:"+String.valueOf(ex)+","+String.valueOf(ey));
            //System.out.println("---action move-----");  
            tv1.setText("移动中坐标为："+"("+event.getX()+" , "+event.getY()+")");  
            break;  
        case MotionEvent.ACTION_UP:  
        	   nowx=event.getX();
               nowy=event.getY();
               ex=nowx/width;
               ey=nowy/height;
               if (v.getId()==tv2.getId())
               {
            	    d=(int)((nowy-rolsy)/height*20);
                	cmdClientSocket.work("rol:"+String.valueOf(d));
               }
               else
               {
            	  ex=event.getX();
               	  ey=event.getY();
               	  float dx,dy;
               	  dx=(ex-sx)/width;
               	  dy=(ey-sy)/height;
            	  cmdClientSocket.work("mva:"+String.valueOf(dx)+","+String.valueOf(dy));
               }
            //System.out.println("---action up-----");  
            //tv1.setText("最后位置为："+"("+event.getX()+" , "+event.getY()+")");  
        }  
        return true;  
	}

}
