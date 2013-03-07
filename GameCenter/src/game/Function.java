package game;

public class Function {
	public String name;
	public String desc;
	
	public Function(String name, Game game) {
		this.name = name;
		game.addFunction(this);
	}
	
	public Object[] run(Object[] args) {
		
		return null;
	}
}
