package server;

import game.Game;

import java.util.ArrayList;

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
	
	protected Object[] callFunction(Object[] pack) {
		
		Object returnVal = game.runFunction(Packager.getPath(pack), Packager.getArgs(pack));
		
		return Packager.toStandardForm(Packager.getPath(pack), game.findFunction(Packager.getPath(pack)).desc, returnVal, null);
	}
	
	
	protected boolean addPlayer(String sessionID) {
		
		// Check if the game has not exceeded max amount of players
		if(availableGameIDs.size() <= 0) {
			return false;
		}
		
		// Check if the player is in the lobby already
		for(Player p : players) {
			if(p.sessionID.equals(sessionID)) {
				return false;
			}
		}
		
		// Assigns a gameID to the player with a sessionID
		Player p = new Player(sessionID, availableGameIDs.get(0));
		availableGameIDs.remove(0);
		
		// Add player to the game
		players.add(p);
		
		return true;
	}
}
