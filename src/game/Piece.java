package game;

public class Piece {
	//Local Variables
	protected boolean currentShape[][];
	protected boolean defaultShape[][];
	protected char color;
	protected boolean placed;
	protected int type;
	protected int state;
	protected int val;
	protected int width;
	protected int height;
	protected int defWidth;
	protected int defHeight;
	final int PIECE_SIZE = 5;
	
	//This is our constructor
	public Piece(int type, char c){
		color = c;
		placed = false;
		this.type = type;
		state = 0;
		currentShape = new boolean[PIECE_SIZE][PIECE_SIZE];
		defaultShape = new boolean[PIECE_SIZE][PIECE_SIZE];
		switch(type){
			case 0:
				defaultShape[0][0] = true;
				width = 1;
				height = 1;
				val = 1;
				break;
			case 1:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				width = 2;
				height = 1;
				val = 2;
				break;
			case 2:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				width = 3;
				height = 1;
				val = 3;
				break;
			case 3:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[1][1] = true;
				width = 2;
				height = 2;
				val = 3;
				break;
			case 4:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[0][3] = true;
				width = 4;
				height = 1;
				val = 4;
				break;
			case 5:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				width = 2;
				height = 2;
				val = 4;
				break;
			case 6:
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				width = 3;
				height = 2;
				val = 4;
				break;
			case 7:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				width = 3;
				height = 2;
				val = 4;
				break;
			case 8:
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				width = 3;
				height = 2;
				val = 4;
				break;
			case 9:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[0][3] = true;
				defaultShape[0][4] = true;
				width = 5;
				height = 1;
				val = 5;
				break;
			case 10:
				defaultShape[0][0] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[1][3] = true;
				width = 4;
				height = 2;
				val = 5;
				break;
			case 11:
				defaultShape[0][0] = true;
				defaultShape[1][0] = true;
				defaultShape[2][0] = true;
				defaultShape[2][1] = true;
				defaultShape[2][2] = true;
				width = 3;
				height = 3;
				val = 5;
				break;
			case 12:
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[2][1] = true;
				width = 3;
				height = 3;
				val = 5;
				break;
			case 13:
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[2][0] = true;
				width = 3;
				height = 3;
				val = 5;
				break;
			case 14:
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[1][3] = true;
				width = 4;
				height = 2;
				val = 5;
				break;
			case 15:
				defaultShape[0][0] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[2][0] = true;
				defaultShape[2][1] = true;
				width = 2;
				height = 3;
				val = 5;
				break;
			case 16:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[2][0] = true;
				defaultShape[2][1] = true;
				width = 2;
				height = 3;
				val = 5;
				break;
			case 17:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[2][0] = true;
				width = 3;
				height = 3;
				val = 5;
				break;
			case 18:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[0][3] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				width = 4;
				height = 2;
				val = 5;
				break;
			case 19:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][1] = true;
				defaultShape[2][1] = true;
				width = 3;
				height = 3;
				val = 5;
				break;
			case 20:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[2][1] = true;
				width = 3;
				height = 3;
				val = 5;
				break;
		}
		defWidth = width;
		defHeight = height;
		setOriginalState();
	}
	
	//Check if spot is part of piece
	public boolean isPart(int x, int y){
		return currentShape[x][y];
	}
	
	//Get Piece color
	public char getColor(){
		return color;
	}
	
	//Get Piece type
	public int getType(){
		return type;
	}
	
	//Get Piece state
	public int getState(){
		return state;
	}
	
	//Get Piece value
	public int getValue(){
		return val;
	}
	
	//Get Piece width
	public int getWidth(){
		return width;
	}
	
	//Get Piece height
	public int getHeight(){
		return height;
	}
	
	//Set placed
	public void setPlaced(){
		placed = true;
	}
	
	//Get placed
	public boolean isPlaced(){
		return placed;
	}
	
	//This function copies defaultShape into currentShape
	public void setOriginalState(){
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				currentShape[i][j] = defaultShape[i][j];
			}
		}
		state = 0;
		width = defWidth;
		height = defHeight;
	}

	//This function removes any empty rows in image
	public boolean shiftUp(){
		boolean empty = true;
		for(int j = 0; j < PIECE_SIZE; ++j){
			if(currentShape[0][j])
				empty = false;
		}
		if(empty){
			for(int i = 1; i < PIECE_SIZE; ++i){
				for(int j = 0; j < PIECE_SIZE; ++j){
					currentShape[i-1][j] = currentShape[i][j];
				}
			}
			for(int j = 0; j < PIECE_SIZE; ++j){
				currentShape[PIECE_SIZE-1][j] = false;
			}
		}
		return empty;
	}
	
	//This function removes any empty columns in image
	public boolean shiftLeft(){
		boolean empty = true;
		for(int i = 0; i < PIECE_SIZE; ++i){
			if(currentShape[i][0])
				empty = false;
		}
		if(empty){
			for(int i = 0; i < PIECE_SIZE; ++i){
				for(int j = 1; j < PIECE_SIZE; ++j){
					currentShape[i][j-1] = currentShape[i][j];
				}
			}
			for(int i = 0; i < PIECE_SIZE; ++i){
				currentShape[i][PIECE_SIZE-1] = false;
			}
		}
		return empty;
	}
	
	//This function rotates currentShape counterclockwise
	public void rotateCounterClockwise(){
		boolean temp[][] = new boolean[PIECE_SIZE][PIECE_SIZE];
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				temp[i][j] = currentShape[j][PIECE_SIZE-i-1];
			}
		}
		currentShape = temp;
		while(shiftUp());
		int tmp = width;
		width = height;
		height = tmp;
		switch (state){
			case 0: state = 3; break;
			case 1: state = 0; break;
			case 2: state = 1; break;
			case 3: state = 2; break;
			case 4: state = 7; break;
			case 5: state = 4; break;
			case 6: state = 5; break;
			case 7: state = 6; break;
		}
	}
	
	//This function rotates currentShape clockwise
	public void rotateClockwise(){
		boolean temp[][] = new boolean[PIECE_SIZE][PIECE_SIZE];
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				temp[j][PIECE_SIZE-i-1] = currentShape[i][j];
			}
		}
		currentShape = temp;
		while(shiftLeft());
		int tmp = width;
		width = height;
		height = tmp;
		switch (state){
			case 0: state = 1; break;
			case 1: state = 2; break;
			case 2: state = 3; break;
			case 3: state = 0; break;
			case 4: state = 5; break;
			case 5: state = 6; break;
			case 6: state = 7; break;
			case 7: state = 4; break;
		}
	}

	//This function flips currentShape across vertical axis
	/*
	public void flipVerticalAxis(){
		boolean temp;
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE/2; ++j){
				temp = currentShape[i][j];
				currentShape[i][j] = currentShape[i][PIECE_SIZE-j-1];
				currentShape[i][PIECE_SIZE-j-1] = temp;
			}
		}
		while(shiftLeft());
		switch (state){
			case 0: state = 4; break;
			case 1: state = 7; break;
			case 2: state = 6; break;
			case 3: state = 5; break;
			case 4: state = 0; break;
			case 5: state = 3; break;
			case 6: state = 2; break;
			case 7: state = 1; break;
		}
	}
	*/
	
	//This function flips currentShape across horizontal axis
	public void flipHorizontalAxis(){
		boolean temp;
		for(int i = 0; i < PIECE_SIZE/2; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				temp = currentShape[i][j];
				currentShape[i][j] = currentShape[PIECE_SIZE-i-1][j];
				currentShape[PIECE_SIZE-i-1][j] = temp;
			}
		}
		while(shiftUp());
		switch (state){
			case 0: state = 6; break;
			case 1: state = 5; break;
			case 2: state = 4; break;
			case 3: state = 7; break;
			case 4: state = 2; break;
			case 5: state = 1; break;
			case 6: state = 0; break;
			case 7: state = 3; break;
		}
	}

	
	
	//Network stuff on a piece
	//returns string of color, type, (x,y), currentState, stateNum
	public String toString(final int x, final int y){
		String s = "";
		s += color + " ";
		s += Integer.toString(type) + " ";
		s += Integer.toString(x) + " " + Integer.toString(y) + " ";
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				if(currentShape[i][j])
					s += "1";
				else
					s += "0";
			}
		}
		s += " " + state;
		return s;
	}

	//Sets currentShape to state represented by string
	//DO NOT DELETE USED BY SERVER!!!!!
	public void setState(String s){
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				if(s.charAt(i*PIECE_SIZE + j) == '1')
					currentShape[i][j] = true;
				else
					currentShape[i][j] = false;
			}
		}
	}

	
	
	//Debugging stuff
	public void printShape(){
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				if(currentShape[i][j]){
					System.out.print("1 ");
				}
				else{
					System.out.print("0 ");
				}
			}
			System.out.print("\n");
		}
	}
}
