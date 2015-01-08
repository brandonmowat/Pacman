/**
 * 
 */
package mazegame;

/**
 * @author Brandon
 *
 */
public interface Grid<T> {
	
	public void setCell(int row, int col, T item);
	
	public T getCell(int row, int col);
	
	public int getNumRows();
	
	public int getNumCols();
	
	public boolean equals(Grid<T> other);
	
	public String toString();
	
}
