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
    
    
    //Checks if player still has a move available
    public boolean playerCanPlay(final Player player){
    	Piece piece;
    	if(player.score == 89)
    		return true;
    	for(int i = 0; i < player.NUM_PIECES; ++i){
    		piece = player.pieces[i];
    		if(!piece.placed){
    			for(int x = 0; x < BOARD_SIZE; ++x){
    				for(int y = 0; y < BOARD_SIZE; ++y){
    					if(validPlace(x, y, piece, false))
    						return true;
    				}
    			}
    		}
    	}
    	return false;
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
    					out.print(". ");
    					break;
    				default:
    					out.print(map[i][j] + " ");
    					break;
    			}
    		}
    		out.print("\n");
    	}
    	out.print("\n");
    }
}
