package server;

import game.Function;
import game.Game;

import java.util.ArrayList;

import client.GsonConverter;
import client.Pack;
import client.Packager;

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
		
		Function getPaths = new Function("/getPaths", "Returns all of the valid paths that the server handles", game) {
			@Override
			public ArrayList<Object> run(Pack pack) {
				return getPaths();
			}
		};
		
		Function help = new Function("/help", "Returns the descrption of the specified path in arg[0]", game) {
			@Override
			public Object run(Pack pack){
				return help((String) pack.getArgAt(0));
			}
		};
		
		Function ping = new Function("/ping", "Takes the Java System.currentTimeMillis() minus the time given in arg[0] and returns it", game) {
			@Override
			public Object run(Pack pack) {
				return ping((Double) pack.getArgAt(0));
			}
		};
		
		Function getSessionID = new Function("/getSessionID", "Generates a random ID if the client does not already have one", game) {
			@Override
			public Object run(Pack pack) {
				return generateClientID();
			}
		};
		
		// TESTING //
		
		Function joinLobby = new Function("/joinLobby", "Trys to join a game lobby", game) {
			@Override
			public Object run(Pack pack) {
				return lobby.addPlayer(pack.getUserSessionID());
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
		
		Pack pack = GsonConverter.gsonToPack(gsonPack);
		pack.setDesc("Function call failed for some reason.");
		
		String path = pack.getPath();
		String userSessionID = pack.getUserSessionID();
		
		// call the function if the player is in the lobby
		for(Player p: lobby.players){
			if(userSessionID.equals(p.userSessionID)) {
				pack.setReturnValue(lobby.callFunction(pack));
				pack.setDesc((game.findFunction(pack.getPath()).desc));
			}
		}
		
		// Add the user to the game lobby (must have a userSessionID)
		if(path.equalsIgnoreCase("/joinLobby")){
			for(String id: knownSessionIDs){
				if(userSessionID.equals(id)){ //is a know sessionID
					pack.setReturnValue(game.runFunction(pack));
					pack.setDesc((game.findFunction(pack.getPath()).desc));
				}
			}
		}
		// Returns a userSessionID
		if (path.equalsIgnoreCase("/getSessionID")){
			pack.setReturnValue(game.runFunction(pack));
			pack.setDesc((game.findFunction(pack.getPath()).desc));
		}
		
		return GsonConverter.packToGson(pack);
	}
	
	/**
	 * Finds all the paths available in the game's function
	 * 
	 * @return ArrayList Returns the paths found
	 */
	protected ArrayList<Object> getPaths() {
		
		ArrayList<Object> paths = new ArrayList<Object>();
		
		for(Function function : game.functions) {
			paths.add(function.name);
		}
		
		return paths;
	}
	
	/**
	 * 
	 * @param path
	 * 
	 * @return
	 */
	protected ArrayList<Object> help(String path) {
		System.out.println(path);
		
		ArrayList<Object> returnValue = new ArrayList<Object>();
		
		if(!(path.substring(0, 1).equalsIgnoreCase("/"))){
			path = "/" + path;
		}

		returnValue.add(game.findFunction(path).desc);
		
		return returnValue;
	}
	
	/**
	 * 
	 * @param receiveTime
	 * 
	 * @return
	 */
	protected ArrayList<Object> ping(Double receiveTime) {
		ArrayList<Object> returnValue = new ArrayList<Object>();
		returnValue.add(System.currentTimeMillis() - receiveTime);
		returnValue.add("pong");
		
		return returnValue;
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
