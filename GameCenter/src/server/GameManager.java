package server;

import client.GsonConverter;
import client.Packager;
import game.*;

public class GameManager {
	
	private ServerControl controller;
	private Game game;
	
	// TESTING //
	
	private Lobby lobby;
	
	// END //
	
	protected GameManager(ServerControl controller) {
		
		this.controller = controller;
		this.game = new Game();
		
		this.lobby = new Lobby(game);
		
		// Add the default functions (aka. Paths) to the game
		
		Function help = new Function("help", "Returns the descrption of the specified path in arg[0]", game) {
			@Override
			public Object run(Object[] args){
				String requestedPath = (String) args[0];
				if(requestedPath.substring(0, 1).equalsIgnoreCase("/")){
					requestedPath = requestedPath.substring(1);
				}
				return (game.findFunction(requestedPath)).desc;
			}
		};
		
		Function ping = new Function("ping", "Takes the Java System.currentTimeMillis() minus the time given in arg[0] and returns it", game) {
			@Override
			public Object run(Object[] args) {
				double time = (Double) args[0];
				return System.currentTimeMillis() - time;
			}
		};
		
		Function getPaths = new Function("getPaths", "Returns all of the valid paths that the server handles", game) {
			@Override
			public Object run(Object[] args) {
				return getPaths();
			}
		};
		
		Function getSessionID = new Function("getSessionID", "Generates a random ID if the client does not already have one", game) {
			@Override
			public Object run(Object[] args) {
				return "" + ServerControl.generateClientID();
			}
		};
		
		// TESTING //
		
		Function joinLobby = new Function("joinLobby", "Trys to join a game lobby", game) {
			@Override
			public Object run(Object[] args) {
				return lobby.addPlayer((String) args[0]);
			}
		};
		
		// END //
	}
	
	/**
	 * Receives the path and calls the correct function 
	 * from the game if the path is correct
	 * 
	 * @param path The path used to find the function
	 * 
	 * @param args The gson equivalent of the packaged arguments
	 * 
	 * @return String Returns the packaged Object[] as a gson String
	 */
	public String callFunction(String gsonPack) { //##### Work in progress #####
		
		Object[] pack = GsonConverter.gsonToObjectArray(gsonPack);
		Object[] packToClient = Packager.toStandardForm(Packager.getPath(pack), "Someting Failed, are you in the Lobby / do you hava a SessionID?", false, null);
		
		for(Player p: lobby.players){
			System.out.println("got to the player loop.");
			if(true) {  //if the player is in the lobby (then they have a sessionID)
				System.out.println("its the truth");
				packToClient = lobby.callFunction(pack);
				break;
			}
		}
		//else if path matches '/joinLobby', add the user to the game lobby (must already have a sessionID)
		//else if path matches '/genSessionID', return a user session id
		//else return, need user session id / need to be in a lobby.. use the path'/genSessionID' and '/joinLobby'
		
		return GsonConverter.objectArrayToGson(packToClient);
	}
	
	/**
	 * Returns the game's paths as a gson String
	 * 
	 * @return String
	 */
	public String getGsonPaths() {
		
		return GsonConverter.stringArrayToGson(getPaths());
	}
	
	/**
	 * Finds all the paths available in the game's function
	 * 
	 * @return String[] Returns the paths found
	 */
	public String[] getPaths() {
		
		String[] paths = new String[game.functions.size()];
		
		for(int i = 0; i < game.functions.size(); i++) {
			paths[i] = "/" + game.functions.get(i).name;
		}
		
		return paths;
	}
	
	/**
	 * Starts the game
	 */
	public void startGame() {
		
		game.start();
	}
}
