package util;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scroller extends JPanel implements Runnable{
	  JLabel label;
	  String str;

	  public Scroller(String st){
	    super();
	    str = st;
	    label = new JLabel(str);
	    add(label);
	    Thread t = new Thread(this);
	    t.start();
	  }
	  public void run(){
	    while(true){
	        char c = str.charAt(0);
	        String rest = str.substring(1);
	        str = rest + c;
	        label.setText(str);
	        try{
	            Thread.sleep(200);
	        }catch(InterruptedException e){}
	    }
	  }
	 }