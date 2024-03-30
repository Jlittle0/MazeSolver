/**
 * Creates a Maze made up of MazeCells
 * @author Ms. Namasivayam
 * @version 03/04/2022
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Maze {
    private MazeCell[][] mazeGrid;
    private MazeCell startCell;
    private MazeCell endCell;
    private int numRows;
    private int numCols;

    public Maze(String filename) {
        createMaze(filename);
        printMaze();
    }

    /**
     * Create the 2D array of MazeCells from the
     * provided file and save it to this.mazeGrid
     * @param filename The file to create the maze from
     */
    private void createMaze(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            // The row and col are specified in the first line of the file
            this.numRows = myReader.nextInt();
            this.numCols = myReader.nextInt();
            myReader.nextLine();

            this.mazeGrid = new MazeCell[this.numRows][this.numCols];

            for (int row=0; row<this.numRows; row++) {
                String line = myReader.nextLine();

                for (int col=0; col<this.numCols; col++) {
                    // Create a new MazeCell for each location
                    this.mazeGrid[row][col] = new MazeCell(row, col);

                    // Set if it is a wall or the start or end cell
                    if (line.charAt(col) == '#') {
                        this.mazeGrid[row][col].setWall(true);
                    } else if (line.charAt(col) == 'A') {
                        this.mazeGrid[row][col].setExplored(true);
                        this.startCell = this.mazeGrid[row][col];
                    } else if (line.charAt(col) == 'B') {
                        this.endCell = this.mazeGrid[row][col];
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Prints the maze and includes periods to indicate visited cells
     */
    public void printMaze() {
        for (int row=0; row<this.numRows; row++) {
            for (int col=0; col<this.numCols; col++) {
                if (this.mazeGrid[row][col].isWall()) {
                    System.out.print("#");
                } else if (this.startCell == this.mazeGrid[row][col]) {
                    System.out.print("A");
                } else if (this.endCell == this.mazeGrid[row][col]) {
                    System.out.print("B");
                } else if (this.mazeGrid[row][col].isExplored()){
                    System.out.print(".");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Prints the maze with the solution indicated by stars *
     * @param sol The arraylist of maze cells that make up the solution
     */
    public void printSolution(ArrayList<MazeCell> sol) {
        for (int row=0; row<this.numRows; row++) {
            for (int col=0; col<this.numCols; col++) {
                if (this.mazeGrid[row][col].isWall()) {
                    System.out.print("#");
                } else if (this.startCell == this.mazeGrid[row][col]) {
                    System.out.print("A");
                } else if (this.endCell == this.mazeGrid[row][col]) {
                    System.out.print("B");
                } else if(sol.contains(this.mazeGrid[row][col])) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Reset the MazeCells to have no parent and be unvisited.
     */
    public void reset() {
        for (int row=0; row<this.numRows; row++) {
            for (int col=0; col<this.numCols; col++) {
                MazeCell mc = this.mazeGrid[row][col];
                mc.setParent(null);

                // Set the start cell to be visited
                if (mc != this.startCell) {
                    mc.setExplored(false);
                }
            }
        }
    }

    /** Getters **/
    public MazeCell getStartCell() {
        return this.startCell;
    }

    public MazeCell getEndCell() {
        return this.endCell;
    }

    public MazeCell getCell(int row, int col) {
        return this.mazeGrid[row][col];
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    /**
     * Determines if the cell is valid to visit.
     * @param row the int row val
     * @param col the int col val
     * @return boolean true/false
     */
    public boolean isValidCell(int row, int col) {
        if (row >= numRows || col >= numCols || row < 0 || col < 0) {
            return false;
        }
        if (!mazeGrid[row][col].isWall() && !mazeGrid[row][col].isExplored()) {
            return true;
        }
        return false;
    }
}
