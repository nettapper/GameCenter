package game;

public class Game extends Plugin {
	
	// Variables
	
	private boolean hasWon;
	private String winner;
	
	private int randomNumber;
	
	// Constructors
	
	public Game() {
		super();
		
		Function guess = new Function("guess") {
			@Override
			public String run(Object[] args) {
				if(args[0] instanceof Integer) {
					
				} else if(args[0] instanceof String) {
					try {
						args[0] = new Integer(Integer.parseInt((String) args[0]));
					} catch(Exception ex) { ex.printStackTrace(); }
				}
				return null;
			}
		};
		addFunction(guess);
	}
	
	public void startGame() {
		hasWon = false;
		winner = "";
		
		generateRandNum();
		
		System.out.println("winner:" + winner);
	}
	
	private void generateRandNum() {
		this.randomNumber = (int) (Math.random() * 10);
	}
}
