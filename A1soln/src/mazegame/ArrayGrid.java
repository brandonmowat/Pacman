package mazegame;

public class ArrayGrid<T> implements Grid<T> {

	private int numRows;
	private int numCols;
	private T[][] item;

	public ArrayGrid(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		item = (T[][]) new Object[numRows][numCols];
	}

	@Override
	public void setCell(int row, int col, T item) {
		this.item[row][col] = item;
	}


	@Override
	public T getCell(int row, int col) {
		return item[row][col];	
	}

	@Override
	public int getNumRows() {
		return this.numRows;
	}

	@Override
	public int getNumCols() {
		return this.numCols;
	}

	@Override
	public boolean equals(Grid<T> other) {
		return false;
	}
	
	@Override
	public String toString() {
		String stringGrid = "";
		for (int x=0; x<numRows; x++) {
			
			for (int y=0; y<numCols; y++) {
				stringGrid += item[x][y];
			}
			
			stringGrid+="\n";
			
		}
		
		return stringGrid;
		
	}

}
