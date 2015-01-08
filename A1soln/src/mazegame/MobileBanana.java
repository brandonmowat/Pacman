/**
 * 
 */
package mazegame;

/**
 * @author Brandon
 *
 */
public class MobileBanana extends Banana {
	
	
	int value;
	
	/**
	 * Create a neew Mobile Banana.
	 * 
	 * @param symbol
	 * @param row
	 * @param col
	 * @param value
	 */
	public MobileBanana(char symbol, int row, int col, int value) {
		super(symbol, row, col, value);
		this.value = MazeConstants.MOBILE_BANANA_SCORE;
	}
	
	/**
	 * Move Mobile Banana up and down, row and col respectively.
	 * 
	 * @param row
	 * @param col
	 */
	public void move(int row, int col) {
		this.row = this.row + row;
		this.column = this.column + col;
	}

}
