package server;

import client.GsonConverter;
import client.Packager;
import game.*;

public class GameManager {
	// Class variables
	
	private ServerControl controller;
	private Game game;
	
	
	// Constructors
	
	protected GameManager(ServerControl controller) {
		this.controller = controller;
		this.game = new Game();
	}
	
	// Methods
	
	public String callFunction(String path, String args) {
		Object[] packRecieve = Packager.toStandardForm(path, GsonConverter.gsonToObjectArray(args));
		
		Object returnVal = game.runFunction(Packager.getPath(packRecieve), Packager.getArgs(packRecieve));
		
		Object[] packSend = Packager.toStandardForm(Packager.getPath(packRecieve), game.findFunction(Packager.getPath(packRecieve)).desc, returnVal, null);
		
		return GsonConverter.objectArrayToGson(packSend);
	}
	
	public String pingMe(String args) {
		double time = (Double) GsonConverter.gsonToObjectArray(args)[0];
		return GsonConverter.stringArrayToGson(new String[] {"pong", "" + (System.currentTimeMillis() - time)});
	}
	
	public String getGsonPaths() {
		return GsonConverter.stringArrayToGson(getPaths());
	}
	
	public String[] getPaths() {
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
