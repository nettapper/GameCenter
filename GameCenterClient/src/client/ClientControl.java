package client;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientControl {
		
	public static final String IP_ADDRESS = "localhost";
	public static final int PORT = 65533;
	public static final String ADDRESS = "http://" + IP_ADDRESS + ":" + PORT;
	
	protected String[] paths;
	
	// TESTING //
	
	public static int index = 0;
	public static String testData = "lolol lolol";
	
	// END //
	
	public ClientControl() {
		String retrievedPaths = connect("/help", GsonConverter.stringArrayToGson(new String[] {"Nothing here."}));
		
		// DEBUGGING //
		
		System.out.print("READ ---->   ");
		
		if (retrievedPaths != "") {
			this.paths = GsonConverter.gsonToStringArray(retrievedPaths);
			
			System.out.println(retrievedPaths);
		} else {
			System.out.println("Nothing from Server");
		}
		System.out.println("---------------------------------------");
		
		String ping = connect("/ping", GsonConverter.objectArrayToGson(new Object[] {new Long(System.currentTimeMillis())}));
		System.out.println("ping (ms): " + ping);
		
		// END //
	}
	
	public static String connect(String path, Object[] outputData) {
		return connect(path, GsonConverter.objectArrayToGson(outputData));
	}
	
	public static String connect(String path, String outputData) {
		
		System.out.println("Client attempting a connection to : " + ADDRESS + path); // Debugging
		
		HttpURLConnection connection;
		URL link;
		InputStream input;
		String data = "";
		DataOutputStream output;
		
		try {
		    link = new URL(ADDRESS + path);
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
		    
		    System.out.println("Client succesfully connected to   : " + ADDRESS + path); // Debugging
		    
		} catch (Exception e) { e.printStackTrace(); }
		
		return data;
	}
	
	public static void main(String[] args) {
		new ClientControl();
	}
}