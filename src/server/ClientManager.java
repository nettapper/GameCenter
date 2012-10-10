package server;

public class ClientManager {
	//class variables
	protected ServerControl controller;
	
	protected String[] paths;
	protected int port;
	
	//class methods
	
	// Constructors
	public ClientManager(ServerControl controller, String[] paths, int port) {
		this.controller = controller;
		this.paths = paths;
		this.port = port;
	}
}
