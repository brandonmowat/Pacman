package mazegame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that represents the basic functionality of the maze game.
 * This class is responsible for performing the following operations:
 * 1. At creation, it initializes the instance variables used to store the
 *        current state of the game.
 * 2. When a move is specified, it checks if it is a legal move and makes the
 *        move if it is legal.
 * 3. It reports information about the current state of the game when asked.
 */
public class MazeGame {

    /** A random number generator to move the MobileBananas. */
    private Random random;
    
    /** The maze grid. */
    private Grid<Sprite> maze;
    
    /** The first player. */
    private Monkey player1;
    
    /** The second player. */
    private Monkey player2;

    /** The bananas to eat. */
    private List<Banana> bananas;
    
    /**
     * Make a random variable from min to max.
     * 
     * @param min
     * @param max
     * @return A random int between min and max
     */
    public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
    
    /**
     * The MazeGame method initializes our maze. It reads the text file
     * and creates a 2D array containing the different types of Sprites.
     * 
     * @param layoutFileName
     */
    public MazeGame(String layoutFileName) throws IOException {
        random = new Random();
        
        int[] dimensions = getDimensions(layoutFileName);
        maze = new ArrayGrid<Sprite>(dimensions[0], dimensions[1]);
               
        BufferedReader br = new BufferedReader(new FileReader(layoutFileName));
        
        bananas = new ArrayList<Banana>();

	/* INITIALIZE THE GRID HERE */
        String line;
        int i = 0;
        // Loops while there is a line to read and create the string, line
		while ((line = br.readLine()) != null) {
			// loops through the line to create Sprites
			for(int x = 0; x < dimensions[1]; x = x + 1){
				
				// Checks if the current character is either:
				// A Wall, Vacant, A Banana, A Mobile Banana,
				// Player 1 or Player 2. And sets the corresponding
				// position in Array grid to the correct Sprite
				if (line.charAt(x) == MazeConstants.WALL) {
					maze.setCell(i, x, new Wall(MazeConstants.WALL, i, x));
				}
				
				else if (line.charAt(x) == MazeConstants.VACANT) {
					maze.setCell(i, x, 
							new UnvisitedHallway(MazeConstants.VACANT, i, x));
				}
				
				else if (line.charAt(x) == MazeConstants.BANANA) {
					Banana newBanana = new Banana(MazeConstants.BANANA, i, x,
							MazeConstants.BANANA_SCORE);
					maze.setCell(i, x, newBanana);
					bananas.add(newBanana);
				}
				
				else if (line.charAt(x) == MazeConstants.MOBILE_BANANA) {
					MobileBanana newBanana = new MobileBanana(
							MazeConstants.MOBILE_BANANA, i, x, 
							MazeConstants.MOBILE_BANANA_SCORE);
					maze.setCell(i, x, newBanana);
					bananas.add(newBanana);
				}
				
				else if (line.charAt(x) == MazeConstants.P1) {
					player1 = new Monkey(MazeConstants.P1, i, x);
					maze.setCell(i, x, player1);
				}
				
				else if (line.charAt(x) == MazeConstants.P2) {
					player2 = new Monkey(MazeConstants.P2, i, x);
					maze.setCell(i, x, player2);
				}
				
			} 
            i+=1;
        }
	
        br.close();
    }
    
    /**
     * The getDimensions method gets the dimensions of our grid.
     * 
     * @param layoutFileName
     * @return Our array dimensions
     * @throws IOException
     */
	private int[] getDimensions(String layoutFileName) throws IOException {       
        
        BufferedReader br = new BufferedReader(new FileReader(layoutFileName));
    
        // find the number of columns
        String nextLine = br.readLine();
        int numCols = nextLine.length();

        int numRows = 0;
    
        // find the number of rows
        while (nextLine != null && nextLine != "") {
            numRows++;
            nextLine = br.readLine();
        }
        
        br.close();
        return new int[]{numRows, numCols};
    }
    
	/**
	 * The move method is called when the user presses a key. 
	 * 
	 * @param nextMove
	 */
    public void move(char nextMove) {
    	// Gets the row and column of the two players.
    	// Also, initializes a variable to check if a move is valid.
    	int p1row = player1.getRow();
    	int p1col = player1.getCol();
    	int p2row = player2.getRow();
    	int p2col = player2.getCol();
    	boolean valid = true;
    	
    	/* Checks the next move and performs the correct series of commands
    	 such as setting cells and eating Bananas. */
    	
    	// is the next move for p1 to move left?
    	if (nextMove == MazeConstants.P1_LEFT) { 
    		// Is the next space a Banana? 
    		if ((maze.getCell(p1row, p1col - 1)).getSymbol() 
    				== MazeConstants.BANANA) {
    			
    			player1.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p1row, p1col - 1)));
    		}
    		
    		// Is the next space a Mobile Banana?
    		if ((maze.getCell(p1row, p1col - 1)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p1row, p1col - 1)));
    		}
    		
    		// If the move is Valid, Move the player.
			if (valid = isValidMove(player1, nextMove)) {
				
	    		player1.move(0, -1);
	    		maze.setCell(p1row, p1col - 1, player1);
	    		maze.setCell(p1row, p1col, 
	    				new VisitedHallway('.', p1row, p1col));
			}
    	}
    	
    	// Is the next move for player 1 to move right?
    	else if (nextMove == MazeConstants.P1_RIGHT) {
    		if ((maze.getCell(p1row, p1col + 1)).getSymbol() 
    				== MazeConstants.BANANA) {
    			
    			player1.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p1row, p1col + 1)));
    		}
    		
    		if ((maze.getCell(p1row, p1col + 1)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p1row, p1col + 1)));
    		}
    		
    		if (valid = isValidMove(player1, nextMove)) {
    			
	    		player1.move(0, 1);
	    		maze.setCell(p1row, p1col + 1, player1);
	    		maze.setCell(p1row, p1col, 
	    				new VisitedHallway('.', p1row, p1col));
    		}
		}
		
    	// Is the next move for player 1 to move up?
    	else if (nextMove == MazeConstants.P1_UP) {
    		if ((maze.getCell(p1row - 1, p1col)).getSymbol() 
    				== MazeConstants.BANANA) {
    			
    			player1.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p1row - 1, p1col)));
    		}
    		
    		if ((maze.getCell(p1row - 1, p1col)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p1row - 1, p1col)));	
    		}
    		
    		if (valid = isValidMove(player1, nextMove)) {
    			
	    		player1.move(-1, 0);
	    		maze.setCell(p1row - 1, p1col, player1);
	    		maze.setCell(p1row, p1col, 
	    				new VisitedHallway('.', p1row, p1col));	
    		}
		}
		
    	// Is the next move for player 1 to move down?
    	else if (nextMove == MazeConstants.P1_DOWN) {
    		if ((maze.getCell(p1row + 1, p1col)).getSymbol() 
    				== MazeConstants.BANANA) {
    			
    			player1.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p1row + 1, p1col)));
    		}
    		if ((maze.getCell(p1row + 1, p1col)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf
    					(maze.getCell(p1row + 1, p1col)));
    		}
    		if (valid = isValidMove(player1, nextMove)) {
	    		player1.move(1, 0);
	    		maze.setCell(p1row + 1, p1col, player1);
	    		maze.setCell(p1row, p1col, 
	    				new VisitedHallway('.', p1row, p1col));
    		}
		}
    	
    	// Is the next move for player 2 to move left?
    	else if (nextMove == MazeConstants.P2_LEFT) {
    		if ((maze.getCell(p2row, p2col - 1)).getSymbol() 
    				== MazeConstants.BANANA) {
    			
    			player2.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row, p2col - 1)));
    		}
    		if ((maze.getCell(p2row, p2col - 1)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row, p2col - 1)));
    		}
    		if (valid = isValidMove(player2, nextMove)) {
	    		player2.move(0, -1);
	    		maze.setCell(p2row, p2col - 1, player2);
	    		maze.setCell(p2row, p2col, 
	    				new VisitedHallway('.', p2row, p2col));
    		}
    	}
    	
    	// Is the next move for player 2 to move right?
    	else if (nextMove == MazeConstants.P2_RIGHT) {
    		if ((maze.getCell(p2row, p2col + 1)).getSymbol() 
    				== MazeConstants.BANANA) {
    			
    			player2.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row, p2col + 1)));
    		}
    		if ((maze.getCell(p2row, p2col + 1)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row, p2col + 1)));
    		}
    		if (valid = isValidMove(player2, nextMove)) {
	    		player2.move(0, 1);
	    		maze.setCell(p2row, p2col + 1, player2);
	    		maze.setCell(p2row, p2col, 
	    				new VisitedHallway('.', p2row, p2col));
    		}
		}
		
    	// Is the next move for player 2 to move up?
    	else if (nextMove == MazeConstants.P2_UP) {
    		if ((maze.getCell(p2row - 1, p2col)).getSymbol()
    				== MazeConstants.BANANA) {
    			
    			player2.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row - 1, p2col)));
    		}
    		if ((maze.getCell(p2row - 1, p2col)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row - 1, p2col)));
    		}
    		if (valid = isValidMove(player2, nextMove)) {
	    		player2.move(-1, 0);
	    		maze.setCell(p2row - 1, p2col, player2);
	    		maze.setCell(p2row, p2col, 
	    				new VisitedHallway('.', p2row, p2col));
    		}
		}
		
    	// Is the next move for player 2 to move down?
    	else if (nextMove == MazeConstants.P2_DOWN) {
    		if ((maze.getCell(p2row + 1, p2col)).getSymbol() 
    				== MazeConstants.BANANA) {
    			
    			player2.eatBanana(MazeConstants.BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row + 1, p2col)));
    		}
    		if ((maze.getCell(p2row + 1, p2col)).getSymbol() 
    				== MazeConstants.MOBILE_BANANA) {
    			
    			player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
    			bananas.remove(bananas.indexOf(
    					maze.getCell(p2row + 1, p2col)));
    		}
    		if (valid = isValidMove(player2, nextMove)) {
	    		player2.move(1, 0);
	    		maze.setCell(p2row + 1, p2col, player2);
	    		maze.setCell(p2row, p2col, 
	    				new VisitedHallway('.', p2row, p2col));
    		}
		}
    	
    	// Move Mobile Banana Here
    	//
	    int i = 0; // makes an indexing variable
	    while (i != bananas.size()) { // Loops through List of Bananas
	    	int randNum = randInt(0,4); // Makes Random Number
	    	
	    	//  Checks if the element of the bananas list is a MobileBanana
	    	if ((bananas.get(i)).getSymbol() == MazeConstants.MOBILE_BANANA) {
	    		//get the row and column of the mobile banana
	    		int mRow = bananas.get(i).getRow();
	    		int mCol = bananas.get(i).getCol();
	    		
	    		//makes a local variable of Mobile Banana for cleaner code
	    		MobileBanana myBanana = (MobileBanana) bananas.get(i);
	    		
	    		// Checks what the random number is and makes magic.
	    		// Moves down on 0.
	    		if (randNum == 0) {
	    			
	    			// If the next space is Vacant, move.
	    			if (maze.getCell(mRow + 1, mCol).getSymbol() 
	    					== MazeConstants.VACANT) {

		    			myBanana.move(1, 0);
			    		maze.setCell(mRow + 1, mCol, myBanana);
			    		maze.setCell(mRow, mCol, 
			    				new UnvisitedHallway(' ', mRow, mCol));
	    			}
	    		}
	    		
	    		// Moves up on 1.
	    		else if (randNum == 1) {
	    			// If the next space is Vacant, move.
	    			if (maze.getCell(mRow - 1, mCol).getSymbol() 
	    					== MazeConstants.VACANT) {
		    			myBanana.move(-1, 0);
			    		maze.setCell(mRow - 1, mCol, myBanana);
			    		maze.setCell(mRow, mCol, 
			    				new UnvisitedHallway(' ', mRow, mCol));
	    			}
	    		}
	    		
	    		// Moves right on 2.
	    		else if (randNum == 2) {
	    			// If the next space is Vacant, move.
	    			if (maze.getCell(mRow, mCol + 1).getSymbol() 
	    					== MazeConstants.VACANT) {
						myBanana.move(0, 1);
			    		maze.setCell(mRow, mCol + 1, myBanana);
			    		maze.setCell(mRow, mCol,
			    				new UnvisitedHallway(' ', mRow, mCol));
	    			}
	    		}
	    		
	    		// Moves left on 3.
	    		else if (randNum == 3) {
	    			// If the next space is Vacant, move.
	    			if (maze.getCell(mRow, mCol - 1).getSymbol() 
	    					== MazeConstants.VACANT) {
						myBanana.move(0, -1);
						maze.setCell(mRow, mCol - 1, myBanana);
						maze.setCell(mRow, mCol, 
								new UnvisitedHallway(' ', mRow, mCol));
	    			}
				}
	    	}
	    	i++;
	    }
    }
    	
    
    /**
     * Return true if and only if the next move is valid. That being if
     * the player wants to eat a banana, move to an empty space or eat a 
     * Mobile Banana
     * 
     * @param player A Monkey player
     * @param nextMove takes a keyboard character and move in that direction.
     * @return if the next move is vaid
     */
    public boolean isValidMove(Monkey player, char nextMove) {
    	int pRow = player.getRow();
    	int pCol = player.getCol();
    	
    	switch (nextMove) {
    	case MazeConstants.P1_UP: // Does the player want to move up?
    		return !((maze.getCell(pRow - 1, pCol).getSymbol() 
    				== MazeConstants.WALL)
    				|| (maze.getCell(pRow - 1, pCol).getSymbol() 
    						== MazeConstants.VISITED)
    				|| (maze.getCell(pRow - 1, pCol).getSymbol() 
    						== MazeConstants.P2));
    		
	    case MazeConstants.P1_DOWN: // Does the player want to move down?
	    	return !((maze.getCell(pRow + 1, pCol).getSymbol() 
	    			== MazeConstants.WALL)
					|| (maze.getCell(pRow + 1, pCol).getSymbol() 
							== MazeConstants.VISITED)
					|| (maze.getCell(pRow + 1, pCol).getSymbol() 
							== MazeConstants.P2));
	    	
	    case MazeConstants.P1_RIGHT: // Does the player want to move right?
	    	return !((maze.getCell(pRow, pCol + 1).getSymbol() 
	    			== MazeConstants.WALL)
					|| (maze.getCell(pRow, pCol + 1).getSymbol() 
							== MazeConstants.VISITED)
					|| (maze.getCell(pRow, pCol + 1).getSymbol() 
							== MazeConstants.P2));
			
	    case MazeConstants.P1_LEFT: // Does the player want to move left?
			return !((maze.getCell(pRow, pCol - 1).getSymbol() 
					== MazeConstants.WALL)
					|| (maze.getCell(pRow, pCol - 1).getSymbol() 
							== MazeConstants.VISITED)
					|| (maze.getCell(pRow, pCol - 1).getSymbol() 
							== MazeConstants.P2));
			
	    case MazeConstants.P2_UP: // Does the player want to move up?
    		return !((maze.getCell(pRow - 1, pCol).getSymbol() 
    				== MazeConstants.WALL)
    				|| (maze.getCell(pRow - 1, pCol).getSymbol() 
    						== MazeConstants.VISITED)
    				|| (maze.getCell(pRow - 1, pCol).getSymbol() 
    						== MazeConstants.P1));
    		
	    case MazeConstants.P2_DOWN: // Does the player want to move down?
			return !((maze.getCell(pRow + 1, pCol).getSymbol() 
					== MazeConstants.WALL)
					|| (maze.getCell(pRow + 1, pCol).getSymbol() 
							== MazeConstants.VISITED)
					|| (maze.getCell(pRow + 1, pCol).getSymbol()
							== MazeConstants.P1));
			
	    case MazeConstants.P2_RIGHT: // Does the player want to move right?
			return !((maze.getCell(pRow, pCol + 1).getSymbol() 
					== MazeConstants.WALL)
					|| (maze.getCell(pRow, pCol + 1).getSymbol() 
							== MazeConstants.VISITED)
					|| (maze.getCell(pRow, pCol + 1).getSymbol() 
							== MazeConstants.P1));
			
	    case MazeConstants.P2_LEFT: // Does the player want to move left?
			return !((maze.getCell(pRow, pCol - 1).getSymbol() 
					== MazeConstants.WALL)
					|| (maze.getCell(pRow, pCol - 1).getSymbol() 
							== MazeConstants.VISITED)
					|| (maze.getCell(pRow, pCol - 1).getSymbol() 
							== MazeConstants.P1));
			
    	}
    	
    	return true;
    }
    
    /**
     * 
     * @return maze
     */
    public Grid<Sprite> getMaze() {
    	return maze;
    }
    
    /**
     * gets player1
     * 
     * @return player1
     */
    public Monkey getPlayerOne() {
    	return player1;
    }
    
    /**
     * gets player 2
     * 
     * @return player2
     */
    public Monkey getPlayerTwo() {
    	return player2;
    }
    
    /**
     * Gets the number of rows in our maze.
     * 
     * @return Number of rows in our maze.
     */
    public int getNumRows() {
    	return maze.getNumRows();
    }
    
    /**
     * gets the number of columns in our maze
     * 
     * @return number of columns in maze
     */
    public int getNumCols() {
    	return maze.getNumCols();
    }
    
    /**
     * Gets the object at index (i, j) in our maze.
     * 
     * @param i index i (A row)
     * @param j index j (A column)
     * @return the int of the player who has one. If its a tie,
     * return 3. Return 0 if the game isn't over.
     */
    public Sprite get(int i, int j){
		return maze.getCell(i, j);
	}
    
    public int hasWon() {
    	if (bananas.isEmpty()) {
    		// if player 1 has more points
    		if (player1.getScore() > player2.getScore()) {
    			return 1;
    		}
    		// if player 2 has more points
    		else if (player1.getScore() < player2.getScore()) {
    			return 2;
    		}
    		// They are equal
    		else return 3;
    	}
    	
    	else {
    		// Game is still running
    		return 0;
    	}
    }
    
    /**
     * This method returns true if no player can move at this point.
     * 
     * @return true if and only if both players can no longer move.
     */
    public boolean isBlocked() {
    	// If no player can move, return false
		if (
				((isValidMove(player1, MazeConstants.P1_UP)) || 
				(isValidMove(player1, MazeConstants.P1_DOWN)) ||
				(isValidMove(player1, MazeConstants.P1_LEFT)) ||
				(isValidMove(player1, MazeConstants.P1_RIGHT))) 
				||
				((isValidMove(player2, MazeConstants.P2_UP)) || 
				(isValidMove(player2, MazeConstants.P2_DOWN)) ||
				(isValidMove(player2, MazeConstants.P2_LEFT)) ||
				(isValidMove(player2, MazeConstants.P2_RIGHT)))
				) {
    		return false;
    	}
		// Otherwise, players can stull move, so return true
		else {
			return true;
		}
    }
    
}
