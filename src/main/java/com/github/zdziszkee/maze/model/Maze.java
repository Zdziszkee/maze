package com.github.zdziszkee.maze.model;

public class Maze {
    private final MazeCell[][] cells;
    private final int rows;
    private final int columns;

    public Maze(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException("rows and columns must be greater than 0");
        }
        this.cells = new MazeCell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = new MazeCell(row, column);
            }
        }
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public MazeCell[][] getCells() {
        return cells;
    }
}
