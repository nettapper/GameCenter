package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ClientManagerTemplate implements HttpHandler {
	
	protected ServerControl controller;
	protected String[] paths;
	
	
	//TESTING//
	
	String response = "nope";
	int data = 0;
	
	// END //
	
	public ClientManagerTemplate(ServerControl controller, String[] paths) {
		//Initiate server here!
	}
	
	public String update(String path) {
		
		return null;
	}
	
	@Override
	public void handle(HttpExchange exchange) {
		
		try {
			
		} catch(Exception ex) { ex.printStackTrace(); }
	}
}
