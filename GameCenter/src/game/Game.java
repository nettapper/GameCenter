package game;

public class Game extends Plugin {
	
	private int playersTurn;
	
	private String[][] board;
	
	public Game() {
		super();
		
		Function canPlay = new Function("canPlay", this) {
			@Override
			public Object run(Object[] args) {
				return canPlay((String) args[0]);
			}
		};
		
		Function isAvailable = new Function("isAvailable", this) {
			@Override
			public Object run(Object[] args) {
				return isAvailable((Integer) args[0], (Integer) args[1]);
			}
		};
		
		Function placeAt = new Function("placeAt", this) {
			@Override
			public Object run(Object[] args) {
				return placeAt((String) args[0], (Integer) args[1], (Integer) args[2]);
			}
		};
		
		Function hasWinner = new Function("hasWinner", this) {
			@Override
			public Object run(Object[] args) {
				return hasWinner();
			}
		};
		
		gameIDs.add("X");
		gameIDs.add("O");
	}
	
	@Override
	public void start() {
		this.playersTurn = 0;
		
		this.board = new String[3][3];
	}
	
	private String getTurn() {
		return gameIDs.get(playersTurn);
	}
	
	private boolean canPlay(String gameID) {
		if(getTurn().equalsIgnoreCase(gameID)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isAvailable(int x, int y) {
		if(board[y][x].equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean placeAt(String gameID, int x, int y) {
		if(canPlay(gameID) && isAvailable(x, y)) {
			board[y][x] = gameID;
			nextPlayersTurn();
			return true;
		} else {
			return false;
		}
	}
	
	private String hasWinner() {
		boolean hasWinner = false;
		
		int x = 0;
		int y = 0;
		String comparer = board[y][x];
		
		while(y < board.length) {
			if(board[y][x].equalsIgnoreCase(comparer) && !isAvailable(x, y)) {
				hasWinner = true;
			} else {
				hasWinner = false;
				break;
			}
			x++;
			y++;
		}
		
		if(hasWinner) {
			return comparer;
		} else {
			x = 0;
			y = board.length - 1;
			comparer = board[y][x];
		}
		
		while(y >= 0) {
			if(board[y][x].equalsIgnoreCase(comparer) && !isAvailable(x, y)) {
				hasWinner = true;
			} else {
				hasWinner = false;
				break;
			}
			x++;
			y--;
		}
		
		if(hasWinner) {
			return comparer;
		} else {
			x = 0;
			y = 0;
			comparer = board[y][x];
		}
		
		while(y < board.length) {
			
			if(hasWinner) {
				return comparer;
			} else {
				comparer = board[y][x];
				hasWinner = false;
			}
			
			while(x < board[0].length) {
				if(board[y][x].equalsIgnoreCase(comparer) && !isAvailable(x, y)) {
					hasWinner = true;
				} else {
					hasWinner = false;
					break;
				}
				x++;
			}
			
			x = 0;
			y++;
		}
		
		while(x < board.length) {
			
			if(hasWinner) {
				return comparer;
			} else {
				comparer = board[y][x];
				hasWinner = false;
			}
			
			while(y < board[0].length) {
				if(board[y][x].equalsIgnoreCase(comparer) && !isAvailable(x, y)) {
					hasWinner = true;
				} else {
					hasWinner = false;
					break;
				}
				y++;
			}
			y = 0;
			x++;
		}
		
		return "";
	}
	
	private void nextPlayersTurn() {
		playersTurn++;
		if(playersTurn >= gameIDs.size()) {
			playersTurn = 0;
		}
	}
}
