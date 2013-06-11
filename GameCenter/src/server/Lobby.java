package server;

import game.Game;

import java.util.ArrayList;

import client.Pack;
import client.Packager;

public class Lobby {
	
	private Game game;
	
	private ArrayList<String> availableGameIDs;
	protected ArrayList<Player> players;
	
	protected Lobby(Game game) {
		
		this.game = game;
		
		this.availableGameIDs = game.getGameIDs();
		this.players = new ArrayList<Player>();
	}
	/**
	*Runs the function after adding the the clients GameID
	*
	*@param Pack The pack that the client has sent to the server
	*
	*@return Object The return value
	*/
	protected Object callFunction(Pack pack) {
		
		String userSessionID = pack.getUserSessionID();
		String userGameID = "";
		
		for(Player p : players) {
			if(userSessionID.equalsIgnoreCase(p.userSessionID)) {
				userGameID = p.userGameID;
			}
		}
		
		pack.setUserGameID(userGameID);
		
		return game.runFunction(pack);
	}
	
	/**
	*Adds the player to the Lobby
	*
	*@param String The user's session ID from the pack object
	*
	*@return boolean Returns if it was successful or not
	*/
	protected boolean addPlayer(String userSessionID) {
		
		// Check if the game has not exceeded max amount of players
		if(availableGameIDs.size() <= 0) {
			return false;
		}
		
		// Check if the player is in the lobby already
		for(Player p : players) {
			if(userSessionID.equals(p.userSessionID)) {
				return false;
			}
		}
		
		// Assigns a gameID to the player with a sessionID
		Player p = new Player(userSessionID, availableGameIDs.get(0));
		availableGameIDs.remove(0);
		
		// Add player to the game
		players.add(p);
		
		return true;
	}
}
