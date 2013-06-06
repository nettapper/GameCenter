package server;

import game.Game;

import java.util.ArrayList;

import client.Pack;
import client.Packager;

/*
 * Handles players in or out of games
 * It contains the up to the max number of players in a current game
 * 
 */
public class Lobby {
	
	private Game game;
	
	private ArrayList<String> availableGameIDs;
	protected ArrayList<Player> players;
	
	protected Lobby(Game game) {
		
		this.game = game;
		
		this.availableGameIDs = game.getGameIDs();
		this.players = new ArrayList<Player>();
	}
	
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
