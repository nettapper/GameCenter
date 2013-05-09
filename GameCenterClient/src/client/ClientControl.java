package client;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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
		
		String ping = connect("/ping", GsonConverter.objectArrayToGson(new Object[] {System.currentTimeMillis()}));
		System.out.println(ping);
		
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
		//new ClientControl();
		
		// for Number guessing game //
		System.out.println("gussing game starting");
		System.out.println("type \'exit\' to exit");
		Scanner sc = new Scanner(System.in);
		String userIn = "";
		while(!(userIn.equalsIgnoreCase("exit"))){
			System.out.println("1-send, 2-read, 3-\'/help\'");
			userIn = sc.nextLine();
			if(userIn.length() > 1)
				continue;
			if(userIn.equalsIgnoreCase(""))
				continue;
			try {
				switch(Integer.parseInt(userIn)){
				case 1:
					System.out.println("--Sending--");
					System.out.print("path:");
					String userPath = sc.nextLine();
					String userGuess = "no data to send";
					String userGuessType = "String";
					if(userPath.equalsIgnoreCase("/guess")){
						System.out.print("guess (int):");
						userGuess = sc.nextLine();
						userGuessType = "int";
					}
					Object[] userInput = new Object[1];
					if(userGuessType.equalsIgnoreCase("int")){
						userInput[0] = Integer.parseInt(userGuess);
					}
					String serverReturn = connect(userPath, userInput);
					System.out.println("serverReturn" + serverReturn);
					break;
				case 2:
					System.out.println("--Reading--");
					break;
				case 3:
					System.out.println("--\'/help\'--");
					String retrievedPaths = connect("/help", GsonConverter.stringArrayToGson(new String[] {"Nothing here."}));
					System.out.println(retrievedPaths);
					break;
				default:
					System.out.println("Not a valid choice");
					break;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("something went wrong");
				continue;
			}
		}
		// end Number guessing game //
	}
}