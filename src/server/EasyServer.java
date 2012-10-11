package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class EasyServer implements HttpHandler {
    public String update(String Path) {
	return "";
    }
    
    @Override
    public void handle(HttpExchange Connection) throws IOException {
	String Path = Connection.getHttpContext().getPath();
	System.out.println("Server attempting to handle a connection to \""+Path+".\"");
	
	String Response = update(Path);
	
        InputStream Input = Connection.getRequestBody();
	OutputStream Output = Connection.getResponseBody();
	
        Connection.sendResponseHeaders(200, Response.length());
        Output.write(Response.getBytes());
        Output.close();
	
	System.out.println("Server succesfully handled a connection to \""+Path+".\"");
    }
    
    public EasyServer(int Port, String[] Paths) {
	System.out.println("Server attempting to open a port to \""+Port+".\"");
	
	try {
	    HttpServer Server = HttpServer.create();
	    Server.bind(new InetSocketAddress(Port), 16);
	    
	    for (int index = 0; index < Paths.length; index++) {
		Server.createContext(Paths[index], this);
	    }
	    
	    Server.setExecutor(null);    
	    Server.start();
	} catch(Exception Error) {}
	
	System.out.println("Server succesfully opened a port to \""+Port+".\"");
    }
}
