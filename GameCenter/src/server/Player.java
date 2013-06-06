package server;

public class Player {
	
	protected String userSessionID;
	protected String userGameID;
	
	public Player(String userSessionID, String userGameID) {
		
		this.userSessionID = userSessionID;
		this.userGameID = userGameID;
	}
}
