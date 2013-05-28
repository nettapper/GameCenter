package game;

public class GameOld extends Plugin {
	
	// Variables
	
	private boolean hasWon;
	private String winner;
	
	private double randomNumber;
	
	// Constructors
	
	public GameOld() {
		super();
		
		Function guess = new Function("guess", "Call this to guess a number. Arguments: [int guess]", this) {
			@Override
			public Object run(Object args) {
				double g = 0;
				try {
					g = (Double) args;
				} catch(Exception e) {e.printStackTrace(); }
				
				return new Boolean(g == randomNumber);
			}
		};
	}
	
	public void startGame() {
		hasWon = false;
		winner = "";
		
		generateRandNum();
	}
	
	private void generateRandNum() {
		this.randomNumber = (int) (Math.random() * 10);
	}
}
