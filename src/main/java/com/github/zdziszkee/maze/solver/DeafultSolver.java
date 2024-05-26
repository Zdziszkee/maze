package com.github.zdziszkee.maze.solver;

import com.github.zdziszkee.maze.model.Maze;
import com.github.zdziszkee.maze.model.MazeCell;
import com.github.zdziszkee.maze.model.MazeCellType;

import java.util.*;

public class DeafultSolver implements Solver {
    @Override
    public Solution solve(Maze maze) {
        MazeCell[][] cells = maze.getCells();

        final Map<MazeCell, Set<MazeCell>> adjacencyList = new HashMap<>();
        MazeCell entrance = null;
        MazeCell exit = null;
        for (MazeCell[] row : cells) {
            for (MazeCell cell : row) {
                adjacencyList.compute(cell, (key, value) -> {
                    if (value == null) {
                        value = new HashSet<>();
                    }
                    value.addAll(getNeighbours(cell.getRow(), cell.getColumn(), cells).stream().filter(cell1 -> cell1.getMazeCellType() != MazeCellType.WALL).toList());

                    return value;
                });
                if (cell.getMazeCellType() == MazeCellType.ENTRANCE) {
                    entrance = cell;
                } else if (cell.getMazeCellType() == MazeCellType.EXIT) {
                    exit = cell;
                }
            }
        }

        Queue<MazeCell> vertices = new ArrayDeque<>();

        vertices.add(entrance);
        Map<MazeCell, MazeCell> parents = new HashMap<>();
        Map<MazeCell, Integer> visited = new LinkedHashMap<>();

        visited.put(entrance, 0);
        LinkedHashSet<MazeCell> shortestPath = new LinkedHashSet<>();

        outer:
        while (!vertices.isEmpty()) {
            MazeCell vertex = vertices.poll();
            Set<MazeCell> neighbours = adjacencyList.get(vertex);

            for (MazeCell neighbour : neighbours) {
                if (!visited.containsKey(neighbour)) {
                    vertices.add(neighbour);
                    visited.put(neighbour, visited.getOrDefault(neighbour, 0));
                    parents.put(neighbour, vertex);
                }
                if (neighbour.equals(exit)) {
                    MazeCell current = neighbour;
                    while (parents.containsKey(current)) {
                        shortestPath.addFirst(current);
                        current = parents.get(current);
                    }
                    shortestPath.addFirst(entrance);
                    break outer;
                }
            }
        }

        return new Solution(visited.keySet(), shortestPath);
    }

    private Set<MazeCell> getNeighbours(int row, int column, MazeCell[][] cellMatrix) {
        Set<MazeCell> neighbours = new HashSet<>();
        if (inInRange(row + 1, column, cellMatrix)) neighbours.add(cellMatrix[row + 1][column]);
        if (inInRange(row - 1, column, cellMatrix)) neighbours.add(cellMatrix[row - 1][column]);
        if (inInRange(row, column + 1, cellMatrix)) neighbours.add(cellMatrix[row][column + 1]);
        if (inInRange(row, column - 1, cellMatrix)) neighbours.add(cellMatrix[row][column - 1]);
        return neighbours;

    }

    boolean inInRange(int row, int column, MazeCell[][] cellMatrix) {
        return row >= 0 && row < cellMatrix.length && column >= 0 && column < cellMatrix[0].length;
    }
}
