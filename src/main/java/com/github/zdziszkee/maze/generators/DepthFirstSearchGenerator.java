package com.github.zdziszkee.maze.generators;

import com.github.zdziszkee.maze.model.Maze;
import com.github.zdziszkee.maze.model.MazeCell;
import com.github.zdziszkee.maze.model.MazeCellType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class DepthFirstSearchGenerator implements Generator {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public void generate(Maze maze) {
        final MazeCell[][] cells = maze.getCells();
        final int rows = maze.getRows();
        final int columns = maze.getColumns();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col].setMazeCellType(MazeCellType.WALL);
            }
        }
        final Set<MazeCell> visited = new HashSet<>();

        final MazeCell start = cells[random.nextInt(rows)][random.nextInt(columns)];
        start.setMazeCellType(MazeCellType.ENTRANCE);
        Stack<MazeCell> stack = new Stack<>();
        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            MazeCell cell = stack.peek();
            List<MazeCell> neighbours = getNeighbours(cell.getRow(), cell.getColumn(), cells).stream()
                    .filter(arg -> arg.getMazeCellType() == MazeCellType.WALL && !visited.contains(arg))
                    .toList();

            if (!neighbours.isEmpty()) {
                MazeCell neighbour = neighbours.get(random.nextInt(neighbours.size()));
                connectCells(cell, neighbour, cells);
                neighbour.setMazeCellType(MazeCellType.PATH);
                visited.add(neighbour);
                stack.push(neighbour);
            } else {
                stack.pop();
            }
        }
        cells[random.nextInt(rows)][random.nextInt(columns)].setMazeCellType(MazeCellType.EXIT);
    }

    private List<MazeCell> getNeighbours(int row, int column, MazeCell[][] cells) {
        List<MazeCell> neighbours = new ArrayList<>();
        if (row > 1) neighbours.add(cells[row - 2][column]); // Up
        if (row < cells.length - 2) neighbours.add(cells[row + 2][column]); // Down
        if (column > 1) neighbours.add(cells[row][column - 2]); // Left
        if (column < cells[0].length - 2) neighbours.add(cells[row][column + 2]); // Right
        return neighbours;
    }

    private void connectCells(MazeCell current, MazeCell neighbour, MazeCell[][] cells) {
        int rowDiff = neighbour.getRow() - current.getRow();
        int colDiff = neighbour.getColumn() - current.getColumn();

        if (rowDiff == 2) { // Down
            cells[current.getRow() + 1][current.getColumn()].setMazeCellType(MazeCellType.PATH);
        } else if (rowDiff == -2) { // Up
            cells[current.getRow() - 1][current.getColumn()].setMazeCellType(MazeCellType.PATH);
        } else if (colDiff == 2) { // Right
            cells[current.getRow()][current.getColumn() + 1].setMazeCellType(MazeCellType.PATH);
        } else if (colDiff == -2) { // Left
            cells[current.getRow()][current.getColumn() - 1].setMazeCellType(MazeCellType.PATH);
        }
    }
}
