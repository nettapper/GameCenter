package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import client.GsonConverter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class ClientManager implements HttpHandler {
	
	protected ServerControl controller;
	protected String[] paths;
	
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
		System.out.println("path from client: " + path); //Debugging
		if(path.equals("/ping")) {
			String [] strArray = new String[1];
			strArray[0] = "Server has succesfully read path \" ping \" : " + System.currentTimeMillis();
			return GsonConverter.stringArrayToGson(strArray);
		} else if(path.equals("/helps")) {
			String [] strArray = new String[1];
			strArray[0] = "Server has succesfully read path \" help \" : " + System.currentTimeMillis();
			return GsonConverter.stringArrayToGson(strArray);
		}
		
		for(int i = 0; i < paths.length; i++) {
			if(path.equals(paths[i])) {
				String [] strArray = new String[1];
				strArray[0] = paths[i];
				return GsonConverter.stringArrayToGson(strArray);
			}
		}
		
		String [] strArray = new String[1];
		strArray[0] = path + " is not a proper path.";
		return GsonConverter.stringArrayToGson(strArray);
	}
	
	@Override
	public void handle(HttpExchange exchange) {
		InputStream inputStream = exchange.getRequestBody();
		
		try {
			System.out.print("DataFromClient: "); // Debuging
			
			String dataFromClient = "";
			while(data != -1){
				data = inputStream.read();
				
				if(data == -1) {
					break;
				} else {
					dataFromClient += (char) data;
				}
			}
			
			String[] recievedFromClient = GsonConverter.gsonToStringArray(dataFromClient);
			
			System.out.println(recievedFromClient);// Debugging
			
			data = 0;
			
			System.out.println("--Data has been read--"); // Debugging
			
			String getPath = exchange.getHttpContext().getPath();
			System.out.println("getPath: "+ getPath); // Debugging
			this.response = update(getPath);
			
			System.out.println("DataToClient:"+this.response); // Debugging
			
			exchange.sendResponseHeaders(200, response.length());
			
			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response.getBytes());
			outputStream.close();
			
		} catch (IOException e) { e.printStackTrace(); }
	}
}