package game;

import java.util.Scanner;

public class TestGame extends Plugin {
	Scanner kb = new Scanner(System.in);
	int randomNum = (int) Math.round(Math.random()*10);
	public String[] gamePaths = {"/playerInput","/requestIfCorrect"};
	public void Game(){
		System.out.println("The super secret number is:"+randomNum);
		while(true){
			System.out.print("Enter your guess:");
			int guess = kb.nextInt();
			if(guess==randomNum){
				System.out.println("you win");
				break;
			}
			else{
				System.out.println("try again");
			}
		}
	}
}
