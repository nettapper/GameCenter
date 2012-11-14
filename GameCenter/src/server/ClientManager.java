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
		
		//EasyServer server = new EasyServer(this.port, this.paths) {
		SimpleServer server = new SimpleServer(this.paths, this.port) {
			@Override
			public String update(String path) {
				System.out.print("THE PATH FROM THE EASY SERVER:"+path);
				if(path.equals("/hi"))
					return "Your path was hi";
				if(path.equals("/help"))
					return "Your path was help";
				else
					return "I don't know what your path was";
			}
		
		};
	}
}
