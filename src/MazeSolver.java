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
        path.add(maze.getStartCell());
        int currentRow;
        int currentCol;
        boolean validNeighbors;
        while (path.peek() != maze.getEndCell()) {
            validNeighbors = false;
            currentRow = path.peek().getRow();
            currentCol = path.peek().getCol();
            for (MazeCell cell : getNeighbors(currentRow, currentCol, true)) {
                if (cell != null) {
                    path.push(cell);
                    cell.setParent(maze.getCell(currentRow, currentCol));
                    cell.setExplored(true);
                    validNeighbors = true;
                }
            }
            if (!validNeighbors) {
                path.pop();
            }
        }
        return getSolution();
    }

    public ArrayList<MazeCell> getNeighbors(int row, int col, boolean reverse) {
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
        q.add(maze.getStartCell());
        int currentRow;
        int currentCol;
        boolean validNeighbors;
        while (q.peek() != maze.getEndCell() && !q.isEmpty()) {
            validNeighbors = false;
            currentRow = q.peek().getRow();
            currentCol = q.peek().getCol();
            for (MazeCell cell : getNeighbors(currentRow, currentCol, false)) {
                if (cell != null) {
                    q.add(cell);
                    cell.setParent(maze.getCell(currentRow, currentCol));
                    cell.setExplored(true);
                    validNeighbors = true;
                }
            }
            if (!validNeighbors) {
                q.remove();
            }
        }
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
