package game;

import java.util.ArrayList;

import client.Pack;

public abstract class Plugin {
	
	public ArrayList<Function> functions;
	
	public ArrayList<String> gameIDs;
	
	public Plugin() {
		
		this.functions = new ArrayList<Function>();
		this.gameIDs = new ArrayList<String>();
	}
	
	public void start() {
		
	}
	
	public Function findFunction(String name) {
		
		for(Function f : functions) {
			if(f.name.equalsIgnoreCase(name)) {
				return f;
			}
		}
		return null;
	}
	
	public Object runFunction(Pack pack) {
		
		Function func = findFunction(pack.getPath());
		if(func != null) {
			return func.run(pack);
		}
		return null;
	}
	
	public void addFunction(Function newFunc) {
		
		if(findFunction(newFunc.name) == null) {
			functions.add(newFunc);
		}
	}
	
	public void removeFunction(Function oldFunc) {
		
		if(functions.contains(oldFunc))	{
			functions.remove(oldFunc);
		}
	}
	
	public void removeFunction(int index) {
		
		if(index >= 0 && index < functions.size()) {
			functions.remove(index);
		}
	}
	
	public ArrayList<String> getGameIDs() {
		ArrayList<String> gids = new ArrayList<String>();
		for(String id : gameIDs) {
			gids.add(id);
		}
		return gids;
	}
}
