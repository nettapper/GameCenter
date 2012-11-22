package client;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientControl {
	//Class variables
	protected static final String ipaddress = "127.0.0.1";
	protected static final String port = "65535";
	protected static final String[] path = {"hi","help"};
	protected static int indexNum = 0;
	protected static String data = "lol lol lol im better than you!";
	
	//Constructor
	public static void main(String[] args) {
		while(indexNum < path.length){
			connect("http://"+ipaddress+":"+port+"/"+path[indexNum], data);
			indexNum++;
		}
	}
	//HTTP-related methods
	public static String connect(String address, String outputData) {
		System.out.println("Client attempting a connection to \""+address+".\"");
		
		HttpURLConnection connection;
		URL link;
		InputStream input;
		String data = "";
		DataOutputStream output;
		
		try {
		    link = new URL(address);
		    connection = (HttpURLConnection) link.openConnection();
		    connection.setAllowUserInteraction(true);
		    connection.setRequestMethod("POST");
		    connection.setDoOutput(true);
		    connection.setDoInput(true);
		    connection.setInstanceFollowRedirects(false);
		    
		    output = new DataOutputStream(connection.getOutputStream());
		    output.writeBytes(outputData);
		    output.flush();
		    output.close();
		    
		    input = connection.getInputStream();
		    while (input.available() > 0) {
			int number = input.read();
			data += Character.toString((char) number);
		    }
		    connection.disconnect();
		    input.close();
		    System.out.println("Client succesfully connected to \""+address+".\" and read \""+data+".\"");
		} catch (Exception Error) {Error.printStackTrace();}
		return data;
	    }
}