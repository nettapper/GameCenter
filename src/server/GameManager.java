package server;

public class GameManager {
	// Class variables
	private ServerControl controller;
	
	
	// Constructors
	public GameManager(ServerControl controller) {
		this.controller = controller;
	}
	
	// Methods
	
	protected String[] getPaths() {
		String[] s = {"hi", "ho", "di", "do"};
		return s;
	}
}
