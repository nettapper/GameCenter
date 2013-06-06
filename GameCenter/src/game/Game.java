package game;

import client.Pack;
import client.Packager;

public class Game extends Plugin {
	
	private int playersTurn;
	
	private String[][] board =  {{" ", " ", " "},
								 {" ", " ", " "},
								 {" ", " ", " "}};
	
	public Game() {
		super();
		
		Function canPlay = new Function("canPlay", this) {
			@Override
			public Object run(Pack pack) {
				return canPlay(pack.getUserGameID());
			}
		};
		
		Function isAvailable = new Function("isAvailable", this) {
			@Override
			public Object run(Pack pack) {
				return isAvailable((Integer) pack.getArgAt(0), (Integer) pack.getArgAt(1));
			}
		};
		
		Function placeAt = new Function("placeAt", this) {
			@Override
			public Object run(Pack pack) {
				return placeAt((String) pack.getArgAt(0), (Integer) pack.getArgAt(1), (Integer) pack.getArgAt(2));
			}
		};
		
		Function hasWinner = new Function("hasWinner", this) {
			@Override
			public Object run(Pack pack) {
				return hasWinner();
			}
		};
		
		gameIDs.add("X");
		gameIDs.add("O");
	}
	
	@Override
	public void start() {
		this.playersTurn = 0;
	}
	
	private String getTurn() {
		return gameIDs.get(playersTurn);
	}
	
	private boolean canPlay(String gameID) {
		System.out.println(gameID);
		if(getTurn().equalsIgnoreCase(gameID)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isAvailable(int x, int y) {
		if(board[y][x].equals(" ")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean placeAt(String gameID, int x, int y) {
		if(canPlay(gameID) && isAvailable(x, y)) {
			board[y][x] = gameID;
			nextPlayersTurn();
			
			toString();
			
			return true;
		} else {
			return false;
		}
	}
	
	private boolean hasWinner() {
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
			return true;
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
			return true;
		} else {
			x = 0;
			y = 0;
			comparer = board[y][x];
		}
		
		while(y < board.length) {
			
			if(hasWinner) {
				return true;
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
		
		x = 0;
		y = 0;
		comparer = board[y][x];
		
		while(x < board[0].length) {
			if(hasWinner) {
				return true;
			} else {
				comparer = board[y][x];
				hasWinner = false;
			}
			
			while(y < board.length) {
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
		
		return false;
	}
	
	private void nextPlayersTurn() {
		playersTurn++;
		if(playersTurn >= gameIDs.size()) {
			playersTurn = 0;
		}
	}
	
	public String toString() {
		String ret = "{";
		for(int y = 0; y < board.length; y++) {
			ret += " {";
			for(int x = 0; x < board[0].length; x++) {
				ret += board[y][x] + ",";
			}
			ret += "}, ";
		}
		ret += "}";
		return ret;
	}
}
