package wzu.server.operator;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class DIR extends BaseOperator{

	private static File file=new File("c:/");

	@Override
	public ArrayList<String> exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub
		File[] listFiles;
		boolean isRoot=false;
		ArrayList<String> ackMsg=new ArrayList<String>();
		ackMsg.add("ok");
		if(cmdBody.equals("...")){
			//ackMsg.add("");
			isRoot=true;
		}
		else if(cmdBody.equals("/..")){
			file=file.getParentFile();
		}else{
			file=new File(cmdBody);
			System.out.println("thuis"+"   "+file.getName());
			System.out.println("this is .....:"+cmdBody);
		}
		if(isRoot){
			listFiles=File.listRoots();
//			System.out.println("");
			for(File mfile:listFiles){
				if(!mfile.canRead()){
					continue;
				}
				String filename=mfile.getPath();
				long lastModified=mfile.lastModified();
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String fileDate=dateFormat.format(new Date(lastModified));
				String fileSize="0";
				String isDir="2";
				ackMsg.add(filename+">"+fileDate+">"+fileSize+">"+isDir+">");
			}	
		}else{
			String pwd="";
			try{
				pwd=file.getCanonicalPath();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//System.out.println("pwd:"+pwd);
			//ackMsg.add("..."+">"+""+">"+"0"+">"+"1"+">");
			ackMsg.add("..."+">"+""+">"+"0"+">"+"1"+">");
			String[] pwdSplits = pwd.split("/");
			String[] pwdSplits2 = pwd.split("\\\\");
			if(pwdSplits.length>1|pwdSplits2.length>1){
				ackMsg.add(".."+">"+""+">"+"0"+">"+"1"+">");
			}
			System.out.println(file.getAbsolutePath());
			listFiles=file.listFiles();
			if(listFiles!=null){
				for(File mfile:listFiles){
					if(!mfile.canRead()){
						continue;
					}
					String filename=mfile.getPath();
					//System.out.println(filename);
					long lastModified=mfile.lastModified();
					SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String fileDate=dateFormat.format(new Date(lastModified));
					String fileSize="0";
					
					String isDir="1";//文件夹
					if(!mfile.isDirectory()){
						isDir="0";//普通文件
						int splitIdx = filename.indexOf(".");
						String idStr=filename.substring(splitIdx+1);
						//if (idStr.equals("avi")||idStr.equals("mp4"))isDir="3";//视频格式
						//if (idStr.equals("pptx")||idStr.equals("ppt")) isDir="4";//PPT格式
						//System.out.println(idStr);
						fileSize=""+mfile.length();
					}
					ackMsg.add(filename+">"+fileDate+">"+fileSize+">"+isDir+">");
				}			
			}		
		}	
		return ackMsg;
	}
	
	
	public DIR(){
		super();
	}
	
	public boolean checkCanAcess(File f){
		if(f.canRead()){
			return false;
		}
		if(f.isHidden()){
			return false;
		}
		return true;
	}
	
	
	
	
	
	
}
