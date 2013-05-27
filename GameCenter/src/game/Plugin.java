package game;

import java.util.ArrayList;

public abstract class Plugin {
	
	public ArrayList<Function> functions;
	
	public ArrayList<String> gameIDs;
	
	public Plugin() {
		
		this.functions = new ArrayList<Function>();
		this.gameIDs = new ArrayList<String>();
	}
	
	public Function findFunction(String name) {
		
		for(Function f : functions) {
			if(f.name.equalsIgnoreCase(name)) {
				return f;
			}
		}
		return null;
	}
	
	public Object runFunction(String name, Object args) {
		
		for(Function f : functions) {
			if(f.name.equals(name)) {
				return f.run(args);
			}
		}
		return null;
	}
	
	public void addFunction(Function newFunc) {
		
		for(Function f : functions) {
			if(f.name.equals(newFunc.name)) return;
		}
		functions.add(newFunc);
	}
	
	public void removeFunction(Function oldFunc) {
		
		if(functions.contains(oldFunc))	functions.remove(oldFunc);
	}
	
	public void removeFunction(int index) {
		
		if(index >= 0 && index < functions.size()) functions.remove(index);
	}
	
	public ArrayList<String> getGameIDs() {
		ArrayList<String> gids = new ArrayList<String>();
		for(String id : gameIDs) {
			gids.add(id);
		}
		return gids;
	}
}
