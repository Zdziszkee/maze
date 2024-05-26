package com.github.zdziszkee.maze.solver;

import com.github.zdziszkee.maze.model.MazeCell;

import java.util.Collections;
import java.util.Set;

public record Solution(Set<MazeCell> visited, Set<MazeCell> solution) {
    public Set<MazeCell> visited() {
        return Collections.unmodifiableSet(visited);
    }

    public Set<MazeCell> solution() {
        return Collections.unmodifiableSet(solution);
    }
}
