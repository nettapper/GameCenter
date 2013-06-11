package game;

import java.util.ArrayList;

import client.Pack;

public class Function {
	public String name;
	public String desc;
	
	public Function(String name, Plugin plugin) {
		
		this(name, "", plugin);
	}
	
	public Function(String name, String desc, Plugin plugin) {
		
		this.name = name;
		this.desc = desc;
		plugin.addFunction(this);
	}
	
	/**
	*Runs the function
	*/
	public Object run(Pack pack) {
		
		return null;
	}
}
