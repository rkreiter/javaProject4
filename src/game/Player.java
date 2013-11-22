package game;

public class Player {
	//Local Variables
	protected String name;
	protected char color;
	protected int score;
	protected boolean playable;
	protected Piece pieces[];
	protected int queuedPiece;
	final int NUM_PIECES = 21;
	
	//The Constructor
	public Player(String n, char c){
		name = n;
		color = c;
		score = 89;
		queuedPiece = -1;
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
	
	//Get Score
	public int getScore(){
		return score;
	}
}
