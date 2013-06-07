package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import client.Pack;

public class Game extends Plugin {
	
	private int playersTurn;
	
	private String[][] board =  {{" ", " ", " "},
								 {" ", " ", " "},
								 {" ", " ", " "}};
	
	// TESTING //
	
	File print = new File("C:/Users/Calvin/Desktop/tictactoe.txt");
	
	// END //
	
	public Game() {
		super();
		
		Function canPlay = new Function("/canPlay", this) {
			@Override
			public Object run(Pack pack) {
				return canPlay(pack.getUserGameID());
			}
		};
		
		Function isAvailable = new Function("/isAvailable", this) {
			@Override
			public Object run(Pack pack) {
				return isAvailable((Double) pack.getArgAt(0), (Double) pack.getArgAt(1));
			}
		};
		
		Function placeAt = new Function("/placeAt", this) {
			@Override
			public Object run(Pack pack) {
				return placeAt(pack.getUserGameID(), (Double) pack.getArgAt(0), (Double) pack.getArgAt(1));
			}
		};
		
		Function hasWinner = new Function("/hasWinner", this) {
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
		if(getTurn().equalsIgnoreCase(gameID)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isAvailable(double x, double y) {
		if(board[(int) y][(int) x].equals(" ")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean placeAt(String gameID, double x, double y) {
		if(canPlay(gameID) && isAvailable(x, y) && !hasWinner()) {
			board[(int) y][(int) x] = gameID;
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
		try {
			FileWriter writer = new FileWriter("C:/Users/Calvin/Desktop/tictactoe.txt");
			PrintWriter printer = new PrintWriter(writer);
			
			for(int y = 0; y < board.length; y++) {
				printer.print("| ");
				for(int x = 0; x < board[0].length; x++) {
					printer.print(board[y][x]);
					if(x < board[0].length - 1) {
						printer.print(" | ");
					}
				}
				printer.print(" |");
				printer.println("");
			}
			
			printer.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
