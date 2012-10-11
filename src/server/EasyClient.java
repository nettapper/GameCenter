package server;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class EasyClient {
    public String Connect(String Address) {
	System.out.println("Client attempting a connection to \""+Address+".\"");
	
	HttpURLConnection Connection;
	URL Link;
	InputStream Input;
	String Data = "";
	
	try {
	    Link = new URL(Address);
	    Connection = (HttpURLConnection) Link.openConnection();
	    Connection.setAllowUserInteraction(true);
	    Connection.setRequestMethod("GET");
	    Connection.setDoInput(true);
	    Input = Connection.getInputStream();
	    
	    while (Input.available() > 0) {
		int Number = Input.read();
		Data += Character.toString((char) Number);
	    }
	    
	    Connection.disconnect();
	    Input.close();
	    System.out.println("Client succesfully connected to \""+Address+".\" and read \""+Data+".\"");
	} catch (Exception Error) {Error.printStackTrace();}
	
	
	return Data;
    }
}
