package mazegame;

/**
 * @author Brandon
 * VisitedHallway is a child of Sprite.
 */
public class VisitedHallway extends Sprite {

	/**
	 * @param row The row location of the VisitedHallway.
	 * @param col The column location of the VisitedHallway.
	 */
	public VisitedHallway(char symbol, int row, int col) {
		super(MazeConstants.VISITED, row, col);
	}

}
