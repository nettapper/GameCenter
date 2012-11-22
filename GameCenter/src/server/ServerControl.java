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
		TestGame game = new TestGame();
		//game.Game();
		String[] array = {"test", "fifteen", "help"};
		GsonConverter g = new GsonConverter();
		String tester = g.StringArrayToGson(array);
		String[] testerArray = g.GsonToStringArray(tester);
		System.out.println("------------");
		for(int i = 0; i < array.length; i++)
			System.out.println(array[i]);
		System.out.println("---");
		for(int i = 0; i < testerArray.length; i++)
			System.out.println(testerArray[i]);
		//new ServerControl();
	}
}
