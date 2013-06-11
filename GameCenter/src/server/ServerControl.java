/**
 * ServerControl.java
 * 
 * The main class that ties the server, database, and game together
 * 
 * @author Calvin Bochulak, Conner Dunn
 * @version 0.3
 */

package server;

public class ServerControl {
	
	protected static final int PORT = 65535;
	
	protected DatabaseManager databasemanager;
	protected ClientManager clientmanager;
	protected GameManager gamemanager;
	
	protected ServerControl() {
		
		this.gamemanager = new GameManager(this);
		this.databasemanager = new DatabaseManager(this);
		this.clientmanager = new ClientManager(this, gamemanager.getPaths());
		
		this.gamemanager.startGame();
	}
	
	public static void main(String[] args){
		
		new ServerControl();
	}
}
