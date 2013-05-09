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
		
		// Add default methods to the game //
		
		Function ping = new Function("ping", game) {
			@Override
			public Object run(Object args) {
				double time = (Double) ((Object[]) args)[0];
				return new String[] {"pong", "" + (System.currentTimeMillis() - time)};
			}
		};
		
		Function help = new Function("help", game) {
			@Override
			public Object run(Object args) {
				return getPaths();
			}
		};
		
		// END //
	}
	
	// Methods
	
	public String callFunction(String path, String args) {
		Object[] packRecieve = Packager.toStandardForm(path, GsonConverter.gsonToObjectArray(args));
		
		Object returnVal = game.runFunction(Packager.getPath(packRecieve), Packager.getArgs(packRecieve));
		
		Object[] packSend = Packager.toStandardForm(Packager.getPath(packRecieve), game.findFunction(Packager.getPath(packRecieve)).desc, returnVal, null);
		
		return GsonConverter.objectArrayToGson(packSend);
	}
	
	public String getGsonPaths() {
		return GsonConverter.stringArrayToGson(getPaths());
	}
	
	public String[] getPaths() {
		String[] paths = new String[game.functions.size()];
		
		for(int i = 0; i < game.functions.size(); i++) {
			paths[i] = "/" + game.functions.get(i).name;
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
