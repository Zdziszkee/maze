package com.github.zdziszkee.maze.generators;

import com.github.zdziszkee.maze.model.Maze;
import com.github.zdziszkee.maze.model.MazeCell;
import com.github.zdziszkee.maze.model.MazeCellType;

import java.util.concurrent.ThreadLocalRandom;

public class DefaultGenerator implements Generator {

    @Override
    public void generate(Maze maze) {
        int rows = maze.getRows();
        int columns = maze.getColumns();
        MazeCell[][] cells = maze.getCells();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int i = ThreadLocalRandom.current().nextInt(0, 3);
                if (i == 0) {
                    if (row + 1 >= rows) continue;
                    cells[row + 1][column].setMazeCellType(MazeCellType.PATH);
                } else if (i == 1) {
                    if (column - 1 < 0) continue;
                    cells[row][column - 1].setMazeCellType(MazeCellType.PATH);
                }
            }
        }
        final int row = ThreadLocalRandom.current().nextInt(0, rows);
        final int column = ThreadLocalRandom.current().nextInt(0, columns);

        cells[row][column].setMazeCellType(MazeCellType.EXIT);

        final int row2 = ThreadLocalRandom.current().nextInt(0, rows);
        final int column2 = ThreadLocalRandom.current().nextInt(0, columns);

        cells[row2][column2].setMazeCellType(MazeCellType.ENTRANCE);
    }
}
