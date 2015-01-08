package mazegame;

/**
 * @author Brandon
 * VisitedHallway is a child of Sprite.
 */

public class UnvisitedHallway extends Sprite {
	
	/**
	 * @param row The row location of the VisitedHallway.
	 * @param col The column location of the VisitedHallway.
	 */
	public UnvisitedHallway(char symbol, int row, int col) {
		super(MazeConstants.VACANT, row, col);
	}

}
