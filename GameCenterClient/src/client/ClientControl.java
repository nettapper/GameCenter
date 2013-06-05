/**
 * ClientControl.java
 * 
 * An example client class that works with the GameCenter Server
 * 
 * @author Calvin Bochulak, Conner Dunn
 * @version 0.1
 */
package client;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientControl {
		
	public static final String IP_ADDRESS = "localhost";
	public static final int PORT = 65535;
	public static final String ADDRESS = "http://" + IP_ADDRESS + ":" + PORT;
	
	protected String[] paths;
	
	public ClientControl() {
		
		String gsonSessionID = connect("/getSessionID", GsonConverter.objectArrayToGson(Packager.toStandardForm("/getSessionID", "")));
		Object[] arrraySessionID = GsonConverter.gsonToObjectArray(gsonSessionID);
		String myID = (String) Packager.getReturnValue(arrraySessionID);
		
		connect("/joinLobby", GsonConverter.objectArrayToGson(Packager.toStandardForm("/joinLobby", myID, myID)));
		
		String serverData = connect("/getPaths", GsonConverter.objectArrayToGson(Packager.toStandardForm("/getPaths", "paths please!", myID)));
		
		if (!serverData.equals("")) {
			try {
				Object[] data = GsonConverter.gsonToObjectArray(serverData);
				Object[] recievedPaths = (Object[]) Packager.getReturnValue(data);
				
				paths = new String[recievedPaths.length];
				for(int i = 0; i < paths.length; i++) {
					paths[i] = (String) recievedPaths[i];
				}
			} catch(Exception e) {
				paths = new String[0];
				
				System.out.println("Request from Server for paths FAILED.");
				e.printStackTrace();
			}
		}
		
		// DEBUGGING //				
		//Testing with our tictactoe game
		
		connect("/help", GsonConverter.objectArrayToGson(Packager.toStandardForm("/help", "/ping", myID)));

		connect("/ping", GsonConverter.objectArrayToGson(Packager.toStandardForm("/ping", new Long(System.currentTimeMillis()), myID)));
		
		while(! (Boolean) Packager.getReturnValue(GsonConverter.gsonToObjectArray(connect("/hasWinner", GsonConverter.objectArrayToGson(Packager.toStandardForm("/hasWinner", "Game Done??", myID)))))) {
			if ((Boolean) Packager.getReturnValue(GsonConverter.gsonToObjectArray(connect("/canPlay", GsonConverter.objectArrayToGson(Packager.toStandardForm("/canPlay", "X", myID)))))) {
				connect("/placeAt", GsonConverter.objectArrayToGson(Packager.toStandardForm("/placeAt", new Object[] {0,0}, myID)));
			}
		}
		//end of testing with the tictactoe game
		// END //
	}
	
	/**
	 * Connects to server after changing object[] to string
	 * 
	 * @param path The path connecting to
	 * @param outputData The 'packaged' output data to the server
	 * 
	 * @return String The data from the server
	 */
	public static String connect(String path, Object[] outputData) {
		
		return connect(path, GsonConverter.objectArrayToGson(outputData));
	}
	
	/**
	 * Connects to server and sends the json string 'outputData'
	 * 
	 * @param path The path connecting to
	 * @param outputData The json formated string to be sent to the server
	 * 
	 * @return String The data from the server
	 */
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
				while (input.available() <= 0); //no data from server, waiting...
				while (input.available() > 0) {
					int number = input.read();
					data += (char) number;
				}
			    input.close();
			} catch (Exception e) {
				e.printStackTrace();
				data = ""; //in the case of an exception, data will not be 1/2 complete (always all or nothing)
			}
		    
		    connection.disconnect();
		    
		    System.out.println("Client succesfully connected to   : " + ADDRESS + path); // Debugging
		    
		} catch (Exception e) { e.printStackTrace(); }
		
		// DEBUGGING //
		
		System.out.println("Data To Server: " + outputData);
		System.out.println("Data From Server: " + data);
		System.out.println("---------------------------------------");
		
		// END //
		
		return data;
	}
	
	public static void main(String[] args) {
		
		new ClientControl();
	}
}