package util;
import java.io.InputStream;
import java.io.OutputStream;

public class ExternalProgram extends Thread{
	 private String arguments;
	
	public ExternalProgram(String s){
		arguments = s;
	}

	public static void runBatch(String path)
	{
		try{
			Process pr = Runtime.getRuntime().exec("cmd /c start "+ path);
	        InputStream in = pr.getInputStream();
	        OutputStream out = pr.getOutputStream();
	        InputStream err = pr.getErrorStream();
	        in.close();
	        out.close();
	        err.close();
	        System.exit(0);
	    }catch(Exception e){
	        e.printStackTrace();
		}
	}
	public void run(){
        try{
        	Process pr = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/C", arguments});
        	InputStream in = pr.getInputStream();
            OutputStream out = pr.getOutputStream();
            InputStream err = pr.getErrorStream();
            in.close();
            out.close();
            err.close();
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
}