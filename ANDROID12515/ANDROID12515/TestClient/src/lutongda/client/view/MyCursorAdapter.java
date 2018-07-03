package lutongda.client.view;

import lutongda.client.operator.MySqliteOpenHelper;

import com.example.testclient.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter{

	Context context;
	public MyCursorAdapter(Context context, Cursor c) {
		super(context, c);
		this.context=context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindView(View view, Context arg1, Cursor cursor) {
		// TODO Auto-generated method stub 
		   TextView tv1=(TextView) view.findViewById(R.id.textView1);
		   int Index1=cursor.getColumnIndex(MySqliteOpenHelper.KEY_NAME);
		   int Index2=cursor.getColumnIndex(MySqliteOpenHelper.KEY_ACTION_KEY);
		   String name=cursor.getString(Index1);
		   String action_key=cursor.getString(Index2);
		   tv1.setText(name);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		 View v=LayoutInflater.from(context).inflate(R.layout.rowview, null);
			return v;  
	}

}
