/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Basically just go back and trace the parents of each cell since it can only have one
        // and add them to the beginning of arraylist that is eventually returned.
        ArrayList<MazeCell> path = new ArrayList<MazeCell>();
        path.add(0, maze.getEndCell());
        while (path.get(0) != maze.getStartCell()) {
            path.add(0 , path.get(0).getParent());
        }
        return path;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        Stack<MazeCell> path = new Stack<MazeCell>();
        // Add startCell to the path
        path.add(maze.getStartCell());
        int currentRow;
        int currentCol;
        boolean validNeighbors;
        // While the current cell isn't the end of the maze, continue looping
        while (path.peek() != maze.getEndCell()) {
            validNeighbors = false;
            currentRow = path.peek().getRow();
            currentCol = path.peek().getCol();
            // Go through all the neighbor cells and add the ones that are valid (returned)
            for (MazeCell cell : getNeighbors(currentRow, currentCol, true)) {
                if (cell != null) {
                    // Add the valid cells to path in reverse order so that north is checked first
                    path.push(cell);
                    // Set parent and set explored for each cell used
                    cell.setParent(maze.getCell(currentRow, currentCol));
                    cell.setExplored(true);
                    validNeighbors = true;
                }
            }
            // If there are no surrounding neighbors that are valid, pop so that you can backtrack
            if (!validNeighbors) {
                path.pop();
            }
        }
        // Once the endcell is found and while loop ends, return the solution.
        return getSolution();
    }

    public ArrayList<MazeCell> getNeighbors(int row, int col, boolean reverse) {
        // Goes through and finds all the neighbors and based on whether or not you want
        // them in reverse it returns the neighbors in regular or reverse order.
        ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
        int temp;
        if (!reverse) {
            temp = 1;
            for (int i = 0; i < 2; i++) {
                if (maze.isValidCell(row + temp, col)) {
                    neighbors.add(maze.getCell(row + temp, col));
                }
                if (maze.isValidCell(row, col + temp)) {
                    neighbors.add(maze.getCell(row, col + temp));
                }
                temp -= 2;
            }
        }
        else {
            temp = -1;
            for (int i = 0; i < 2; i++) {
                if (maze.isValidCell(row, col + temp)) {
                    neighbors.add(maze.getCell(row, col + temp));
                }
                if (maze.isValidCell(row + temp, col)) {
                    neighbors.add(maze.getCell(row + temp, col));
                }
                temp += 2;
            }
        }
        return neighbors;
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        Queue<MazeCell> q = new LinkedList<MazeCell>();
        // Add start cell
        q.add(maze.getStartCell());
        int currentRow;
        int currentCol;
        boolean validNeighbors;
        // While the queue isn't empty and the current cell isn't the endcell, continue.
        while (q.peek() != maze.getEndCell() && !q.isEmpty()) {
            validNeighbors = false;
            currentRow = q.peek().getRow();
            currentCol = q.peek().getCol();
            // Get each of the neighbors and add them in north, east, south, west so they check
            // in that order
            for (MazeCell cell : getNeighbors(currentRow, currentCol, false)) {
                if (cell != null) {
                    // Add each valid neighbor to the queue and set parent and explored status
                    q.add(cell);
                    cell.setParent(maze.getCell(currentRow, currentCol));
                    cell.setExplored(true);
                    validNeighbors = true;
                }
            }
            // If there are no neighbors, remove the cell from the queue
            if (!validNeighbors) {
                q.remove();
            }
        }
        // Return the solution
        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
