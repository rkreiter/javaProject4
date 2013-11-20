package game;

import java.util.Scanner;

public class Tester {
	public static void main(String[] args){
    	Board b = new Board();
    	Player asher = new Player('b');
    	Player kyle = new Player('r');
    	
    	Player current;
    	int turn = 1;
    	int piece, x, y;
    	
    	Scanner in = new Scanner(System.in);
    	while(true){
    		if(turn == 0){
    			current = asher;
    			turn = 1;
    		}
    		else{
    			current = kyle;
    			turn = 0;
    		}
    		do{
    			System.out.println("Give a piece number, x, y");
    			piece = in.nextInt();
    			x = in.nextInt();
    			y = in.nextInt();
    			if(current.score == 89 && b.validInit(x, y, current.pieces[piece]))
    				break;
    		}while(!b.validPlace(x, y, current.pieces[piece], false));
    		
    		b.placePiece(x, y, current.pieces[piece]);
    		current.score -= 5;
    		b.printBoard();
    	}
	}
}
