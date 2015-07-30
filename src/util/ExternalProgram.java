package util;

import java.io.InputStream;
import java.io.OutputStream;

public class ExternalProgram extends Thread{
	 private String[] arguments = {"cmd", "/C", "start", "il tuo command"};
	
	public ExternalProgram(String s){
		arguments[3] = s;
	}
	
	public void run(){
        try{
            Process pr = Runtime.getRuntime().exec(arguments);
            InputStream in = pr.getInputStream();
            OutputStream out = pr.getOutputStream();
            InputStream err = pr.getErrorStream();
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
}