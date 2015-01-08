/**
 * 
 */
package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import mazegame.MazeGame;

/**
 * @author Brandon
 *
 */
public class TextUI implements UI {

	private MazeGame game;
	
	/**
	 * 
	 */
	public TextUI(MazeGame game) {
		this.game = game;
	}

	@Override
	public void launchGame() {
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));

		try {
		    String s = br.readLine(); // Read input here
		    
		    int numRows = game.getNumRows();
		    int numCols = game.getNumCols();
		    
		    for (int i = 0; i < numRows; i++) {
		    	String line = "";
	            for (int j = 0; j < numCols; j++) {
	            	line += game.getMaze().getCell(i, j).getSymbol();
	                System.out.println(line);
	            }
		    }
		}
		catch (IOException e) {
	        }

		
	}

	@Override
	public void displayWinner() {
		int won = game.hasWon();        
        int moves = 0;
        String message;
        
        if (game.isBlocked()) { // no winners
            message = "Game over! Both players are stuck.";
        } else {
            if (won == 0) { // game is still on
                return;
            } else if (won == 1) {
                message = "Congratulations Player 1! You won the maze in " + 
                          game.getPlayerOne().getNumMoves() + " moves.";
            } else if (won == 2) { 
                message = "Congratulations Player 2! You won the maze in " + 
                          game.getPlayerTwo().getNumMoves() + " moves.";
            } else { // it's a tie
                message = "It's a tie!";
            }
        }
        System.out.println(message);
	}

}
