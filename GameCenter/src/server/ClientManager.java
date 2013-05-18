package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ClientManager implements HttpHandler {

	protected ServerControl controller;
	protected String[] paths;

	protected ClientManager(ServerControl controller, String[] paths) {
		
		this.controller = controller;
		this.paths = paths;
		
		System.out.println("Server attempting to open port: " + controller.PORT); // Debugging
		
		// Attempt to initiate a server
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(controller.PORT), 0);

			for (int i = 0; i < paths.length; i++) {
				server.createContext(paths[i], this);
			}

			server.setExecutor(null);
			server.start();
			
			// DEBUGGING //

			System.out.println("Server succesfully opened port: " + controller.PORT);
			System.out.println("---------------------------------------");
			
			// END //

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Runs the function that corresponds with the path
	 * 
	 * @param path The path used to find and run the function
	 * 
	 * @param args The arguments passed through to the function
	 * 
	 * @return String The packaged Object[] that has been converted to gson
	 */
	public String update(String path, String args) {
		
		for (int i = 0; i < paths.length; i++) {
			if (path.equalsIgnoreCase(paths[i])) {
				//if Packager.getUserSessionID == known UserSessionID continue
				return controller.gamemanager.callFunction(path, args);
				//else is path matches '/genSessionID', return a user session id
				//else return, need user session id.. use the path'/genSessionID'
			}
		}

		return path + " is not proper.";
	}
	
	/**
	 * Handles the request from the client
	 * 
	 * @param exchange Holds the stream in which the server
	 * 		  can read and send data to the client
	 */
	public void handle(HttpExchange exchange) {
		
		InputStream inputStream = exchange.getRequestBody();

		try {
			int curData = inputStream.read();
			String args = "";
			while (curData != -1) {
				args += (char) curData;
				curData = inputStream.read();
			}

			String path = exchange.getHttpContext().getPath();
			String response = update(path, args);
			
			// DEGUGGING //
			
			System.out.println("Path From Client: " + path);
			System.out.println("Data From Client: " + args);
			System.out.println("Data To Client: " + response);
			System.out.println("---------------------------------------");
			
			// END //

			exchange.sendResponseHeaders(200, response.length());

			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response.getBytes());
			exchange.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}