package wzu.server.operator;

import java.io.File;

public class FileNameUtils {
	public static File checkFile(String filename, int mode)
	{
		if (mode>=1)
		{
			return new  File(filename);
		}
		else
		{
			String path="/C:/load/"+filename;
			File file=new File(path);
			int i=1;
			while (file.exists())
			{
				String newpath=path+"("+String.valueOf(i)+")";
				file=new File(newpath);
				i++;
			}
			return file;
		}
	}
}
