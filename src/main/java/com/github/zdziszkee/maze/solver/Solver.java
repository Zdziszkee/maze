package com.github.zdziszkee.maze.solver;

import com.github.zdziszkee.maze.model.Maze;

public interface Solver {
    Solution solve(Maze maze);
}
