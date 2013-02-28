package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class ClientManager implements HttpHandler {
	
	protected ServerControl controller;
	protected String[] paths;
<<<<<<< HEAD
	protected int port;
	protected String response = "No Path Yet!";
	String[] recievedFromClient;
	protected int data = 0;
	protected Gson gson = new Gson();
=======
>>>>>>> Cleaned up and refactored code
	
	protected Gson gson;
	
	//TESTING//
	
	String response = "nope";
	int data = 0;
	
	// END //
	
	public ClientManager(ServerControl controller, String[] paths) {
		System.out.println("Server attempting to open port: \""+controller.port+"\"");  // Debuging
		
		this.paths = paths;
		
		//Try to initiate server here!
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(controller.port), 0);
			
			for (int i = 0; i < paths.length; i++) {
				server.createContext(paths[i], this);
		    }
			
			server.setExecutor(null);    
			server.start();
			
			System.out.println("Server succesfully opened port: \""+controller.port+"\"");  // Debuging
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public String update(String path) {
<<<<<<< HEAD
		if(recievedFromClient==null){
			return "Error, cannot accept non String[] JSON objects";
		}
		if(path.equals("/hi"))
			return "Your path was hi";
		if(path.equals("/help")){
			String returnString = stringArrayToGson(controller.game.gamePaths);
			return returnString;
=======
		if(path.equals("/ping")) {
			return "pong " + System.currentTimeMillis();
		} else if(path.equals("/help")) {
			return GsonConverter.stringArrayToGson(paths);
		}
		
		for(int i = 0; i < paths.length; i++) {
			if(path.equals(paths[i])) {
				return paths[i];
			}
>>>>>>> Cleaned up and refactored code
		}
		
		return "Not a proper path: " + path;
	}
	
	@Override
	public void handle(HttpExchange exchange) {
		InputStream inputStream = exchange.getRequestBody();
		
		try {
<<<<<<< HEAD
=======
			System.out.print("DataFromClient: "); // Debuging
			
>>>>>>> Cleaned up and refactored code
			String dataFromClient = "";
			while(data != -1){
				data = inputStream.read();
				
				if(data == -1) {
					break;
				} else {
					dataFromClient += (char) data;
				}
			}
<<<<<<< HEAD
			//System.out.println(dataFromClient);
			recievedFromClient = gsonToStringArray(dataFromClient);
			System.out.print("DataFromClient:");
			System.out.println(recievedFromClient);
			data = 0;
			//System.out.println("--Data has been read--");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String getPath = exchange.getHttpContext().getPath();
		this.response = update(getPath);
		System.out.println("DataToClient:"+this.response);
		try {
=======
			
			String[] recievedFromClient = GsonConverter.gsonToStringArray(dataFromClient);
			
			System.out.println(recievedFromClient);// Debuging
			
			data = 0;
			
			System.out.println("--Data has been read--"); // Debuging
			
			String getPath = exchange.getHttpContext().getPath();
			this.response = update(getPath);
			
			System.out.println("DataToClient:"+this.response); // Debuging
			
>>>>>>> Cleaned up and refactored code
			exchange.sendResponseHeaders(200, response.length());
			
			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response.getBytes());
			outputStream.close();
			
		} catch (IOException e) { e.printStackTrace(); }
	}
}