/**
 * ServerControl.java
 * 
 * The main class that ties the server, database, and game together
 * 
 * @author Calvin Bochulak, Conner Dunn
 * @version 0.1
 */

package server;

public class ServerControl {
	
	protected static final int PORT = 65534;
	
	protected DatabaseManager databasemanager;
	protected ClientManager clientmanager;
	protected GameManager gamemanager;
	
	protected ServerControl() {
		
		this.gamemanager = new GameManager(this);
		this.databasemanager = new DatabaseManager(this);
		this.clientmanager = new ClientManager(this, gamemanager.getPaths());
		
		this.gamemanager.startGame();
	}
	
	/**
	 * Currently does nothing
	 * Will generate an ID that is unique to the connected clients
	 * 
	 * @return int Unique client ID
	 */
	protected synchronized int generateClientID() {
		
		return 0;
	}
	
	public static void main(String[] args){
		
		new ServerControl();
	}
}
