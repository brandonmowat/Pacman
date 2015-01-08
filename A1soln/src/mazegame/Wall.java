package mazegame;

/**
 * Wall is a child of Sprite.
 */
public class Wall extends Sprite {
	
	/**
	 * @param row The row location of Wall.
	 * @param column The column location of Wall.
	 */
	public Wall(char symbol, int row, int col) {
			// Use parent constructor.
		    super(MazeConstants.WALL, row, col);
		  }
	
}
