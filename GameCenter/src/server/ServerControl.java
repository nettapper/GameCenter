package server;

public class ServerControl {
	// Class variables
	protected static final int port = 65535;
	
	protected DatabaseManager databasemanager;
	protected ClientManager clientmanager;
	protected GameManager gamemanager;
	
	//constructors	protected Player[] players;
	
	public ServerControl() {
		gamemanager = new GameManager(this);
		databasemanager = new DatabaseManager(this);
		clientmanager = new ClientManager(this, gamemanager.getPaths(), port);
	}
	
	// Main Method
	
	public static void main(String[] args){
		new ServerControl();
	}
}
