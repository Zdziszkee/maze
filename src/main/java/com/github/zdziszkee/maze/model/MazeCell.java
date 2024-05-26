package com.github.zdziszkee.maze.model;

public class MazeCell {
    private final int row;
    private final int column;
    private MazeCellType mazeCellType;

    public MazeCell(MazeCellType mazeCellType, int row, int column) {
        this.mazeCellType = mazeCellType;
        this.row = row;
        this.column = column;
    }

    public MazeCell(int row, int column) {
        this.mazeCellType = MazeCellType.WALL;
        this.row = row;
        this.column = column;
    }

    public MazeCellType getMazeCellType() {
        return mazeCellType;
    }

    public void setMazeCellType(MazeCellType mazeCellType) {
        this.mazeCellType = mazeCellType;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
