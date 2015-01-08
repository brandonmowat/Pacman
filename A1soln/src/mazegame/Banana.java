package mazegame;

public class Banana extends Sprite {

	protected int value;

	public Banana(char symbol, int row, int col, int value) {
		super(symbol, row, col);
		this.value = value;
	}

}
