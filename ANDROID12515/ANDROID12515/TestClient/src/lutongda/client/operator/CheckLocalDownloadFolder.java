package lutongda.client.operator;

import java.io.File;

import android.os.Environment;

public class CheckLocalDownloadFolder {
	public static String check()
	{
		File downloadDirectory;
		String path;
		path=Environment.getExternalStorageDirectory().getAbsoluteFile()+"/"+"ltd_download";
		downloadDirectory=new File(path);
		if (!downloadDirectory.exists())
		{
			downloadDirectory.mkdirs();
		}
		return downloadDirectory.getPath();
	}
}
