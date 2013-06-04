package server;

import java.util.ArrayList;

import client.GsonConverter;
import client.Packager;
import game.*;

public class GameManager {
	
	private ServerControl controller;
	private Game game;
	
	// TESTING //
	
	private Lobby lobby;
	private static ArrayList<String> knownSessionIDs;
	
	// END //
	
	protected GameManager(ServerControl controller) {
		
		this.controller = controller;
		this.game = new Game();
		
		this.lobby = new Lobby(game);
		knownSessionIDs = new ArrayList<String>();
		
		
		// Add the default functions (aka. Paths) to the game
		
		Function getPaths = new Function("getPaths", "Returns all of the valid paths that the server handles", game) {
			@Override
			public Object run(Object[] args) {
				return getPaths();
			}
		};
		
		Function help = new Function("help", "Returns the descrption of the specified path in arg[0]", game) {
			@Override
			public Object run(Object[] args){
				return help((String) args[0]);
			}
		};
		
		Function ping = new Function("ping", "Takes the Java System.currentTimeMillis() minus the time given in arg[0] and returns it", game) {
			@Override
			public Object run(Object[] args) {
				return ping((Double) args[0]);
			}
		};
		
		Function getSessionID = new Function("getSessionID", "Generates a random ID if the client does not already have one", game) {
			@Override
			public Object run(Object[] args) {
				return generateClientID();
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
	 * @param gsonPack The gson equivalent of the package
	 * 
	 * @return String Returns the packaged Object[] as a gson String
	 */
	protected String callFunction(String gsonPack) { 	//##### Work in progress #####
		
		Object[] packFromClient = GsonConverter.gsonToObjectArray(gsonPack);
		Object[] packToClient = Packager.toStandardForm(Packager.getPath(packFromClient), "Someting Failed, are you in the Lobby / do you hava a SessionID?", null, null);
		
		//if the player is in the lobby (then they have a sessionID)
		for(Player p: lobby.players){
			if((p.sessionID).equals(Packager.getUserSessionID(packFromClient))) {
				packToClient = lobby.callFunction(packFromClient);
				return GsonConverter.objectArrayToGson(packToClient);
			}
		}
		//else if path matches '/joinLobby', add the user to the game lobby (must already have a sessionID)
		if(Packager.getPath(packFromClient).equalsIgnoreCase("joinLobby")){
			for(String s: knownSessionIDs){
				if(s.equals(Packager.getUserSessionID(packFromClient))){ //is a know sessionID
					Object returnVal = game.runFunction(Packager.getPath(packFromClient), Packager.getArgs(packFromClient));
					packToClient = Packager.toStandardForm(Packager.getPath(packFromClient), game.findFunction(Packager.getPath(packFromClient)).desc, returnVal, null);
				}
			}
		}
		//else if path matches '/getSessionID', return a user session id
		if (Packager.getPath(packFromClient).equalsIgnoreCase("getSessionID")){
			Object returnVal = game.runFunction(Packager.getPath(packFromClient), Packager.getArgs(packFromClient));
			packToClient = Packager.toStandardForm(Packager.getPath(packFromClient), game.findFunction(Packager.getPath(packFromClient)).desc, returnVal, null);
		}
		
		//else return, need user session id / need to be in a lobby.. use the path'/genSessionID' and '/joinLobby'
		return GsonConverter.objectArrayToGson(packToClient);
	}
	
	/**
	 * Finds all the paths available in the game's function
	 * 
	 * @return String[] Returns the paths found
	 */
	protected String[] getPaths() {
		
		String[] paths = new String[game.functions.size()];
		
		for(int i = 0; i < game.functions.size(); i++) {
			paths[i] = "/" + game.functions.get(i).name;
		}
		
		return paths;
	}
	
	/**
	 * 
	 * @param path
	 * 
	 * @return
	 */
	protected String help(String path) {
		if(path.substring(0, 1).equalsIgnoreCase("/")){
			path = path.substring(1);
		}
		return (game.findFunction(path)).desc;
	}
	
	/**
	 * 
	 * @param receiveTime
	 * 
	 * @return
	 */
	protected Double ping(Double receiveTime) {
		
		return System.currentTimeMillis() - receiveTime;
	}
	
	/**
	 * Currently does nothing
	 * Will generate an ID that is unique to the connected clients
	 * 
	 * @return String Unique client ID
	 */
	protected static synchronized String generateClientID() {
		
		String id = Integer.toString((int) System.currentTimeMillis(), 16);
		knownSessionIDs.add(id);
		return id;
	}
	
	/**
	 * Starts the game
	 */
	protected void startGame() {
		
		game.start();
	}
}
