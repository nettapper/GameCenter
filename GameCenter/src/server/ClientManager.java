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

	public ClientManager(ServerControl controller, String[] paths) {
		System.out.println("Server attempting to open port: " + controller.port); // Debugging

		this.paths = paths;

		// Try to initiate server here!
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(controller.port), 0);

			for (int i = 0; i < paths.length; i++) {
				server.createContext(paths[i], this);
			}

			server.setExecutor(null);
			server.start();

			System.out.println("Server succesfully opened port: " + controller.port); // Debugging
			System.out.println("---------------------------------------"); // Debugging

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String update(String path, String args) {
		System.out.println("Path From Client: " + path); // Debugging

		if (path.equals("/ping")) {
			double time = 0;
			try {
				time = (Double) GsonConverter.gsonToObjectArray(args)[0];
			} catch(Exception e) {e.printStackTrace();}
			return GsonConverter.stringArrayToGson(new String[] {"pong", "" + (System.currentTimeMillis() - time)});
		} else if (path.equals("/help")) {
			return GsonConverter.stringArrayToGson(paths);
		}

		for (int i = 0; i < paths.length; i++) {
			if (path.equals(paths[i])) {
				return paths[i];
			}
		}

		return path + " is not proper.";
	}

	@Override
	public void handle(HttpExchange exchange) {
		InputStream inputStream = exchange.getRequestBody();

		try {
			System.out.print("Data From Client: "); // Debugging
			
			int curData = inputStream.read();
			String totalData = "";
			while (curData != -1) {
				totalData += (char) curData;
				curData = inputStream.read();
			}

			System.out.println(totalData);// Debugging

			String path = exchange.getHttpContext().getPath();
			String response = update(path, totalData);

			exchange.sendResponseHeaders(200, response.length());

			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response.getBytes());
			outputStream.close();

			System.out.println("---------------------------------------"); // Debugging
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}