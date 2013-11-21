package game;

import static java.lang.System.out;
import java.util.Arrays;

public class Board
{
    //Variables
    final int BOARD_SIZE = 20; 
    protected char[][] map;

    
    //This is the constructor
    public Board(){
        map = new char[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; ++i){
        	Arrays.fill(map[i], 'e');
        }
    }
    
       
    //Checks if position (x,y) is within board
    protected boolean validSpot(final int x, final int y){
    	if(x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE)
    		return true;
    	return false;
    }

    
    //Checks if any piece bordered with position (x,y) is of color 'c'
    //Does not check if (x,y) is a valid spot
    protected boolean sharedEdge(final int x, final int y, final char c){
    	if(validSpot(x, y+1) && (c == map[x][y+1]))
    			return true;
    	if(validSpot(x, y-1) && (c == map[x][y-1]))
    			return true;
    	if(validSpot(x+1, y) && (c == map[x+1][y]))
    			return true;
    	if(validSpot(x-1, y) && (c == map[x-1][y]))
    			return true;
    	return false;
    }
    
    
    //Checks if any piece cornered with position (x,y) is of color 'c'
    //Does not check if (x,y) is a valid spot
    protected boolean sharedCorner(final int x, final int y, final char c){
    	if(validSpot(x+1, y+1) && (c == map[x+1][y+1]))
    			return true;
    	if(validSpot(x+1, y-1) && (c == map[x+1][y-1]))
    			return true;
    	if(validSpot(x-1, y+1) && (c == map[x-1][y+1]))
    			return true;
    	if(validSpot(x-1, y-1) && (c == map[x-1][y-1]))
    			return true;
    	return false;
    }
    
    
    //Checks if can place piece 'p' with (x,y) top left corner
    //checks validity of all parts of each piece AND allows piece to be placed
    //on init even if it does NOT share a corner
    public boolean validPlace(final int x, final int y, final Piece p, boolean init){
    	boolean connected = false;
    	if(p.placed)
    		return false;
    	for(int i = 0; i < p.PIECE_SIZE; ++i){
    		for(int j = 0; j < p.PIECE_SIZE; ++j){
    			if(p.isPart(i, j)){
    				if(!validSpot(x+i, y+j) || map[x+i][y+j] != 'e')
    					return false;
    				if(sharedEdge(x+i, y+j, p.getColor()))
    					return false;
    				if(sharedCorner(x+i, y+j, p.getColor()))
    					connected = true;
    			}
    		}
    	}
    	if(init)
    		return true;
    	return connected;
    }
    
    
    
     //Places piece 'p' with (x,y) top left corner
     //does not check if this is a validPlace
     //This function should only be called when server sends status update
    public void placePiece(final int x, final int y, final Piece p){
    	for(int i = 0; i < p.PIECE_SIZE; ++i){
    		for(int j = 0; j < p.PIECE_SIZE; ++j){
    			if(p.isPart(i, j))
    				map[x+i][y+j] = p.getColor();
    		}
    	}
    }

    
    //Checks that a piece is in the corner represented by color
    public boolean validInit(final int x, final int y, final Piece p){
    	if(!validPlace(x, y, p, true))
    		return false;
    	switch (p.getColor()){
    		case 'b':
    			if(x != 0 || y != 0 || !p.isPart(0, 0))
    				return false;
    			break;
    		case 'g':
    			if(y != 0 || x < BOARD_SIZE-p.PIECE_SIZE || 
    					!p.isPart(BOARD_SIZE-x-1, 0))
    				return false;
    			break;
    		case 'r':
    			if(x != 0 || y < BOARD_SIZE-p.PIECE_SIZE || 
    					!p.isPart(0, BOARD_SIZE-y-1))
    				return false;
    			break;
    		case 'y':
    			if( x < BOARD_SIZE-p.PIECE_SIZE || y < BOARD_SIZE-p.PIECE_SIZE ||
    					!p.isPart(BOARD_SIZE-x-1, BOARD_SIZE-y-1))
    				return false;
    			break;
    	}
    	return true;
    }
    
    
    
    
    
    //For debugging purposes prints map to screen
    public void printBoard(){
    	for(int i = 0; i < BOARD_SIZE; ++i){
    		for(int j = 0; j < BOARD_SIZE; ++j){
    			switch(map[i][j]){
    				case 'b':
    					out.print("B ");
    					break;
    				case 'g':
    					out.print("G ");
    					break;
    				case 'r':
    					out.print("R ");
    					break;
    				case 'y':
    					out.print("Y ");
    					break;
    				case 'e':
    					out.print("0 ");
    					break;
    				default:
    					out.print(map[i][j] + " ");
    					break;
    			}
    		}
    		out.print("\n");
    	}
    }
    
    //For debugging purposes allows us to run without gui
    public static void main(String[] args){
    	Board b = new Board();
    
    	Piece pB = new Piece(11, 'b');
    	Piece pG = new Piece(18, 'g');
    	Piece pR = new Piece(20, 'r');
    	Piece pY = new Piece(16, 'y');
    	
    	if(b.validInit(0, 0, pB))
    		b.placePiece(0, 0, pB);
    	
    	if(b.validInit(18, 0, pG))
    		b.placePiece(18, 0, pG);
    	
    	if(b.validInit(0, 17, pR))
    		b.placePiece(0, 17, pR);
    	
    	if(b.validInit(17, 18, pY))
    		b.placePiece(17, 18, pY);
    	
    	
    	Piece p = new Piece(0, 'r');
    	b.placePiece(16, 17, p);
    	
    	
    	p = new Piece(1, 'y');
    	if(b.validPlace(16, 16, p, false))
    		b.placePiece(16, 16, p);

    	b.printBoard();
    }
}
