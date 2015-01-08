package mazegame;

public abstract class Sprite {

	/** A protected char that holds the sprite character. */
	protected char symbol;
	
	/** A protected int that holds the sprite row. */
	protected int row;
	
	/** A protected int that holds the sprite column. */
	protected int column;
	
	
	/**
	 * Creates a Sprite object that has a symbol to represent the type
	 * and both a column and row value to identify it's location.
	 * 
	 * @param symbol The sprites symbol
	 * @param row	The sprites row location
	 * @param column The sprites column location
	 */
	public Sprite(char symbol, int row, int col) {
		this.symbol = symbol;
		this.row = row;
		this.column = col;
	}
	
	/** Get the symbol of the Sprite */
	public char getSymbol() {
		return symbol;
	}
	
	/** Get the row of the Sprite */
	public int getRow() {
		return row;
	}
	
	/** Get the column of the Sprite */
	public int getCol() {
		return column;
	}

	@Override
	public String toString() {
		return Character.toString(symbol);
		// return "" + symbol;
	}
	
}

