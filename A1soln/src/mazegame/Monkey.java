package mazegame;

/**
 * @author Brandon
 * Monkey is a child of Sprite.
 */

public class Monkey extends Sprite {

	/**
	 * initialize score and numMoves.
	 * Both are private ints.
	 */
	
	private int score = 0;
	private int numMoves = 0;

	/**
	 * 
	 * @param symbol
	 * @param row
	 * @param col
	 */
	public Monkey(char symbol, int row, int col) {
		super(symbol, row, col);
		this.score = 0;
		this.numMoves  = 0;
	}
	
	/**
	 * 
	 * @param score
	 */
	public void eatBanana(int score) {
		this.score += score;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumMoves() {
		return this.numMoves;
	}
	
	/**
	 * 
	 * @param row row
	 * @param col
	 */
	public void move(int row, int col) {
		this.row = this.row + row;
		this.column = this.column + col;
		numMoves += 1;
	}

}
