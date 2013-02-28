package server;

public class ServerControl {
	// Class variables
	protected static final int port = 65534;
	
	protected DatabaseManager databasemanager;
	protected ClientManager clientmanager;
	protected GameManager gamemanager;
	
	//constructors	protected Player[] players;
	
	public ServerControl() {
		gamemanager = new GameManager(this);
		databasemanager = new DatabaseManager(this);
		clientmanager = new ClientManager(this, gamemanager.getPaths());
		
		gamemanager.startGame();
	}
	
	// Main Method
	
	public static void main(String[] args){
		new ServerControl();
	}
}
