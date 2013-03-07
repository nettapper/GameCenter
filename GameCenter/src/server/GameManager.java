package server;

import client.GsonConverter;
import game.*;

public class GameManager {
	// Class variables
	
	private ServerControl controller;
	private Game game;
	
	
	// Constructors
	
	public GameManager(ServerControl controller) {
		this.controller = controller;
		this.game = new Game();
	}
	
	// Methods
	
	public String callFunction(String name, String args) {
		if(game.findFunction(name) != null) {
			return GsonConverter.objectArrayToGson(game.runFunction(name, GsonConverter.gsonToObjectArray(args)));
		}
		return GsonConverter.stringArrayToGson(new String[] {"ERROR"});
	}
	
	protected String[] getPaths() {
		String[] paths = new String[2 + game.functions.size()];
		
		paths[0] = "/ping";
		paths[1] = "/help";
		
		for(int i = 0; i < game.functions.size(); i++) {
			paths[2 + i] = "/" + game.functions.get(i).name;
		}
		
		// DEBUGING //
		
		for(int i = 0; i < paths.length; i++) {
			//System.out.println(paths[i]);
		}
		
		//System.out.println("Game functions detected: " + paths.length);
		
		// END //
		
		return paths;
	}
	
	public void startGame() {
		game.startGame();
	}
}
