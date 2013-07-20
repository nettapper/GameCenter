/**
 * ClientControl.java
 * 
 * An example client class that works with the GameCenter Server
 * 
 * @author Calvin Bochulak, Conner Dunn
 */
package client;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientControl {
		
	public static final String IP_ADDRESS = "localhost";
	public static final int PORT = 65535;
	public static final String ADDRESS = "http://" + IP_ADDRESS + ":" + PORT;
	
	protected ArrayList<String> paths;
	protected String userSessionID;
	
	public ClientControl() {
		
		Pack clientPack = new Pack("/getSessionID", "");
		
		String gsonServerPack = connect(clientPack);
		Pack serverPack = GsonConverter.gsonToPack(gsonServerPack);
		
		this.userSessionID = (String) serverPack.getReturnValueAt(0);
		
		clientPack = new Pack("/joinLobby", userSessionID);
		
		connect(clientPack);
		
		clientPack = new Pack("/getPaths", userSessionID);
		
		gsonServerPack = connect(clientPack);
		serverPack = GsonConverter.gsonToPack(gsonServerPack);
		
		if (serverPack != null) {
			try {
				ArrayList<Object> returnValue = serverPack.getReturnValue();
				
				this.paths = new ArrayList<String>();
				for(Object path : returnValue) {
					this.paths.add((String) path);
				}
			} catch(Exception e) {
				paths = new ArrayList<String>();
				
				System.out.println("Request from Server for paths FAILED.");
				e.printStackTrace();
			}
		}
		
		// DEBUGGING //	
		
		clientPack = new Pack("/help", userSessionID);
		clientPack.setArgAt(0, "/ping");
		
		//connect(clientPack);
		
		clientPack = new Pack("/ping", userSessionID);
		clientPack.setArgAt(0, System.currentTimeMillis());
		
		gsonServerPack = connect(clientPack);
		serverPack = GsonConverter.gsonToPack(gsonServerPack);
		
		// Testing with our tictactoe game
		Scanner input = new Scanner(System.in);
		int x = 0;
		int y = 0;
		
		do {
			
			System.out.println("Your turn: " + serverPack.getReturnValueAt(0));
			
			System.out.print("\nPlace at values: ");
			x = input.nextInt();
			y = input.nextInt();
			System.out.println();
			
			clientPack = new Pack("/canPlay", userSessionID);
			
			gsonServerPack = connect(clientPack);
			serverPack = GsonConverter.gsonToPack(gsonServerPack);
			
			if ((Boolean) serverPack.getReturnValueAt(0)) {
				
				clientPack = new Pack("/placeAt", userSessionID);
				clientPack.setArgAt(0, x);
				clientPack.setArgAt(1, y);
				
				gsonServerPack = connect(clientPack);
				serverPack = GsonConverter.gsonToPack(gsonServerPack);
			}
			
			clientPack = new Pack("/hasWinner", userSessionID);
			
			gsonServerPack = connect(clientPack);
			serverPack = GsonConverter.gsonToPack(gsonServerPack);
		} while(!(Boolean) serverPack.getReturnValueAt(0));
		// end of testing with the tictactoe game
		// END //
	}
	
	/**
	 * Connects to server with the given Pack's path
	 * 
	 * @param path The path connecting to
	 * @param outputData The 'packaged' output data to the server
	 * 
	 * @return String The data from the server
	 */
	public static String connect(Pack clientPack) {
		
		return connect(clientPack.getPath(), GsonConverter.packToGson(clientPack));
	}
	
	/**
	 * Connects to server and sends the json string 'outputData'
	 * 
	 * @param path The path connecting to
	 * @param gsonClientPack The json formated string to be sent to the server
	 * 
	 * @return String The data from the server
	 */
	public static String connect(String path, String gsonClientPack) {		
		
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
		    output.writeBytes(gsonClientPack);
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
				data = gsonClientPack; //in the case of an exception, return the original pack
			}
		    
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// DEBUGGING //
		
		System.out.println("Client succesfully connected to   : " + ADDRESS + path); // Debugging
		System.out.println("Data To Server: " + gsonClientPack);
		System.out.println("Data From Server: " + data);
		System.out.println("---------------------------------------");
		
		// END //
		
		return data;
	}
	
	public static void main(String[] args) {
		
		new ClientControl();
	}
}