package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid extends JPanel implements ActionListener {
    private int rows;
    private int cols;
    private int cellSize;
    private int[][] grid;  // 2D array representing the grid state

    public Grid(int rows, int cols, int cellSize, boolean randomStart, boolean customStart) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.grid = new int[rows][cols];  // Initialize the grid with default state 0 (empty)
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        if(customStart){
         this.grid[1][1] = 1;
        this.grid[1][2] = 1;
        this.grid[2][1] = 1;
        this.grid[2][2] = 1;     
        }else if(randomStart){
            //initialize grid to random values:
            for(int i = 0; i < rows;i++){
                for(int j = 0; j < cols;j++){
                    this.grid[i][j] = (int)(Math.random()*2);
                }
            }
        } else {
            //create a glider for testing
            this.grid[2][0] = 1;
            this.grid[3][1] = 1;
            this.grid[1][2] = 1;
            this.grid[2][2] = 1;
            this.grid[3][2] = 1;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Loop through each cell and paint based on alive or dead
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                
                if (grid[row][col] == 0) {//dead
                    g.setColor(Color.BLACK);
                } else if (grid[row][col] == 1) {
                    // Wall (brown)
                    g.setColor(Color.WHITE); // alive
                } else {
                    throw new Error("Not a valid grid value");
                }
                

                // Draw the cell
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);

                // Draw grid lines (optional)
                g.setColor(Color.BLACK);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
    
    private int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;

        // Loop through the neighbors (including diagonals)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself

                int newRow = row + i;
                int newCol = col + j;

                // Check if the neighbor is within the grid bounds
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    liveNeighbors += grid[newRow][newCol];
                }
            }
        }

        return liveNeighbors;
    }

    //ALL YOUR CODE GOES HERE
    public void nextGeneration() {
        int[][] newGrid = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(row, col); 
                
                if (grid[row][col] == 1) { 
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newGrid[row][col] = 0; 
                    } else {
                        newGrid[row][col] = 1;
                    }
                } else { // If the cell is dead
                    if (liveNeighbors == 3) {
                        newGrid[row][col] = 1; 
                    } else {
                        newGrid[row][col] = 0;
                    }
                }
            }
        }
    
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = newGrid[row][col];
            }
        }
    
        // don't mess with this part
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //don't put code here
    }
}