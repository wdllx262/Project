package lutongda.client.operator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteOpenHelper extends SQLiteOpenHelper{
	private static String dbName="test1.db";
	public static final String KEY_NAME="name";
	public static final String KEY_ACTION_KEY="action_key";
	public static final String TABLE_NAME="Conbination";
	public MySqliteOpenHelper(Context context) 
	{
		super(context,dbName,null,2);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql=String.format("create table %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, %s text,%s text)",TABLE_NAME,KEY_NAME,KEY_ACTION_KEY);
		db.execSQL(sql);
		ContentValues cv=new ContentValues();
		cv.put(KEY_NAME, "ÈÈ¾ç×·¾ç²Ù×÷");
		cv.put(KEY_ACTION_KEY, "for:1|cmd:www.iqiyi.com|slp:4000|cps:android");
		db.insert(TABLE_NAME, null, cv);
		cv.put(KEY_NAME, "NBAÖ±²¥");
		cv.put(KEY_ACTION_KEY, "for:1|cmd:http://kbs.sports.qq.com/#nba|slp:4000");
		db.insert(TABLE_NAME, null, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.v(TABLE_NAME,"Upgranding database,which will destory all old data");
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);
	}

}
