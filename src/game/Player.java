package game;

public class Player {
	//Local Variables
	protected String name;
	protected char color;
	protected int score;
	protected boolean playable;
	protected Piece pieces[];
	final int NUM_PIECES = 21;
	
	//The Constructor
	public Player(String n, char c){
		name = n;
		color = c;
		score = 89;
		playable = true;
		pieces = new Piece[NUM_PIECES];
		for(int i = 0; i < NUM_PIECES; ++i){
			pieces[i] = new Piece(i, c);
		}
	}
	
	//Get name
	public String getName(){
		return name;
	}
	
	//Get color
	public char getColor(){
		return color;
	}
	
	//Get Score
	public int getScore(){
		return score;
	}
	
	//Set playable variable
	public void setPlayable(boolean val){
		playable = val;
	}
	
	//Get playable variable
	public boolean isPlayable(){
		return playable;
	}
	
	//Get piece number 'i'
	public Piece getPiece(int i){
		return pieces[i];
	}
	
	//Decrease score
	public void updateScore(int val){
		score -= val;
	}
	
	//Check if player's opening move
	public boolean isInit(){
		return score == 89;
	}
}
