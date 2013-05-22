package server;

public class Player {
	
	protected String sessionID;
	protected String gameID;
	
	public Player(String sessionID, String gameID) {
		
		this.sessionID = sessionID;
		this.gameID = gameID;
	}
}
