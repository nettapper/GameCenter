package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class SimpleServer implements HttpHandler {
	protected String response = "No Path Yet!";
	int data = 0;
	
	public SimpleServer(){
		String[] paths = {"/hi","/help"};
		int port = 9999;
		init(paths,port);
	}
	public SimpleServer(String[] paths, int port){
		init(paths,port);
	}
	public String update(String Path) {
		return "";
	    }
	@Override
	public void handle(HttpExchange exchange){
		InputStream inputStream = exchange.getRequestBody();
		try {
			System.out.print("readData:");
			while(data!=-1){
				data = inputStream.read();
				System.out.print((char)data);
			}
			data = 0;
			System.out.println("--Data has been read--");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String getPath = exchange.getHttpContext().getPath();
		this.response = getPath;
		//System.out.println(response);
		try {
			exchange.sendResponseHeaders(200, response.length());
			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response.getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void init(String[] paths, int port){
		System.out.println("Server attempting to open port: \""+port+"\"");
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			for (int i = 0; i < paths.length; i++) {
				server.createContext(paths[i], this);
		    }
			server.setExecutor(null);    
			server.start();
			System.out.println("Server succesfully opened port: \""+port+"\"");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}