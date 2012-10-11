package server;

public class ServerControl {
	// Class variables
	
	protected DatabaseManager databasemanager;
	protected ClientManager clientmanager;
	protected GameManager gamemanager;
	
	//constructors	protected Player[] players;
	
	public ServerControl() {
		gamemanager = new GameManager(this);
		databasemanager = new DatabaseManager(this);
		clientmanager = new ClientManager(this, gamemanager.getPaths(), 65535);
		
		EasyClient client = new EasyClient();
		client.Connect("http://127.0.0.1:65535/hi");
	}
	
	// Main Method
	
	public static void main(String[] args){
		new ServerControl();
	}
}
