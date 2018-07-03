package wzu.server.operator;

import java.util.ArrayList;

public class Operator {
       public  static ArrayList<String> exeCmd(String cmdHead,String cmdBody) throws Exception
       {
    	   ArrayList<String> msgBackList=new ArrayList<String>();
    	   if (cmdHead.equals("dir"))
    	   {
    		   msgBackList=new DIR().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("opn"))
    	   {
    		   msgBackList=new OPN().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if  (cmdHead.equals("key"))
    	   {
    		   msgBackList=new KEY().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("mov"))
    	   {
    		   msgBackList=new MOV().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("mva"))
    	   {
    		   msgBackList=new MVA().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("clk"))
    	   {
    		   msgBackList=new CLK().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("rol"))
    	   {
    		   msgBackList=new ROL().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("cmd"))
    	   {
    		   msgBackList=new CMD().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("slp"))
    	   {
    		   msgBackList=new SLP().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("cps"))
    	   {
    		   msgBackList=new CPS().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("dlf"))
    	   {
    		   msgBackList=new DLF().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   if (cmdHead.equals("ulf"))
    	   {
    		   msgBackList=new ULF().exe(cmdBody);
    		   return  msgBackList;
    	   }
    	   throw new Exception("invalid cmd!");
       }
}
