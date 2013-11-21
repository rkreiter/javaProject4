package game;

public class Piece {
	//Local Variables
	protected boolean currentShape[][];
	protected boolean defaultShape[][];
	protected char color;
	protected boolean placed;
	protected int type;
	protected int val;
	final int PIECE_SIZE = 5;
	
	//This is our constructor
	public Piece(int type, char c){
		color = c;
		placed = false;
		this.type = type;
		currentShape = new boolean[PIECE_SIZE][PIECE_SIZE];
		defaultShape = new boolean[PIECE_SIZE][PIECE_SIZE];
		switch(type){
			case 0:
				defaultShape[0][0] = true;
				val = 1;
				break;
			case 1:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				val = 2;
				break;
			case 2:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				val = 3;
				break;
			case 3:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[1][1] = true;
				val = 3;
				break;
			case 4:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[0][3] = true;
				val = 4;
				break;
			case 5:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				val = 4;
				break;
			case 6:
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				val = 4;
				break;
			case 7:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				val = 4;
				break;
			case 8:
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				val = 4;
				break;
			case 9:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[0][3] = true;
				defaultShape[0][4] = true;
				val = 5;
				break;
			case 10:
				defaultShape[0][0] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[1][3] = true;
				val = 5;
				break;
			case 11:
				defaultShape[0][0] = true;
				defaultShape[1][0] = true;
				defaultShape[2][0] = true;
				defaultShape[2][1] = true;
				defaultShape[2][2] = true;
				val = 5;
				break;
			case 12:
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[2][1] = true;
				val = 5;
				break;
			case 13:
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[2][0] = true;
				val = 5;
				break;
			case 14:
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[1][2] = true;
				defaultShape[1][3] = true;
				val = 5;
				break;
			case 15:
				defaultShape[0][0] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[2][0] = true;
				defaultShape[2][1] = true;
				val = 5;
				break;
			case 16:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[1][0] = true;
				defaultShape[2][0] = true;
				defaultShape[2][1] = true;
				val = 5;
				break;
			case 17:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[2][0] = true;
				val = 5;
				break;
			case 18:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[0][3] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				val = 5;
				break;
			case 19:
				defaultShape[0][0] = true;
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][1] = true;
				defaultShape[2][1] = true;
				val = 5;
				break;
			case 20:
				defaultShape[0][1] = true;
				defaultShape[0][2] = true;
				defaultShape[1][0] = true;
				defaultShape[1][1] = true;
				defaultShape[2][1] = true;
				val = 5;
				break;
		}
		copyState();
	}
	
	//Check if spot is part of piece
	public boolean isPart(int x, int y){
		return currentShape[x][y];
	}
	
	//Get Piece color
	public char getColor(){
		return color;
	}
	
	//Set placed **should not be public
	public void setPlaced(){
		placed = true;
	}
	
	//This function copies defaultShape into currentShape
	protected void copyState(){
		for(int i = 0; i < PIECE_SIZE; ++i){
			for(int j = 0; j < PIECE_SIZE; ++j){
				currentShape[i][j] = defaultShape[i][j];
			}
		}
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
	}

	//This function flips currentShape vertically
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
	}
	
	//This function flips currentShape horizontally
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
	}
	
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
	
	public static void main(String[] args){
		Piece p = new Piece(20, 'b');
		p.printShape();
		
		System.out.println("");
		p.flipHorizontalAxis();
		p.printShape();
		
		System.out.println("");
		p.flipHorizontalAxis();
		p.printShape();
		
		System.out.println("");
		p.flipVerticalAxis();
		p.printShape();

		System.out.println("");
		p.flipVerticalAxis();
		p.printShape();
		
		System.out.println("");
		p.rotateClockwise();
		p.printShape();
		
		System.out.println("");
		p.rotateCounterClockwise();
		p.printShape();
	}
}
