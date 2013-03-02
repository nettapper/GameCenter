package client;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientControl {
		
	public static final String IP_ADDRESS = "localhost";
	public static final String PORT = "65534";
	
	// TESTING //
	
	public static String[] paths = {"/hi", "/help"};
	public static int index = 0;
	public static String testData = "lolol lolol";
	
	// END //
	
	public ClientControl() {}
	
	public static String connect(String address, String outputData) {
		
		System.out.println("Client attempting a connection to \""+address+".\""); // Debuging
		
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

		    try {  //Try to read the data from server
				input = connection.getInputStream();
				while (input.available() > 0) {
					int number = input.read();
					data += (char) number;
				}
			    input.close();
			} catch (Exception e) {
				System.out.println("No \'input\' data");
				data = "";
			}
		    
		    connection.disconnect();
		    
		    System.out.println("Client succesfully connected to \""+address+".\" and read \""+data+".\""); // Debuging
		    
		} catch (Exception e) { e.printStackTrace(); }
		
		return data;
	}
	
	public static void main(String[] args) {
		String retrievedData = connect("http://"+IP_ADDRESS+":"+PORT+"/"+"helps", GsonConverter.stringArrayToGson(paths));
		
		// DEBUGING //
		
		//System.out.println(GsonConverter.stringArrayToGson(paths));
		
		System.out.println("--------------------------------------");
		if (retrievedData != "") {
			System.out.println(retrievedData);
			String[] array = GsonConverter.gsonToStringArray(retrievedData);
			System.out.println("arrLen: " + array.length);
			for (int i = 0; i < array.length; i++) {
				System.out.println(array[i]);
			}
		} else {
			System.out.println("Nothing from Server");
		}
		
		// END //
	}
}