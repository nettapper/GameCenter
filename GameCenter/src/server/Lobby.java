package server;

import game.Game;

/*
 * Handles players in or out of games
 * It contains the up to the max number of players in a current game
 * 
 */
public class Lobby {
	
	protected Game game;
	
	private int assignIndex;
	
	protected Lobby(Game game) {
		
		this.game = game;
		
		this.assignIndex = 0;
	}
	
	
	protected boolean addPlayer(String sessionID) {
		// Check if the player is in the lobby already
		// Check if the game has not exceeded max amount of players
		// If yes, return false. If no, add player.
		
		// Assigns a gameID to the player with a sessionID
		// Add player to the game
		return false;
	}
}
