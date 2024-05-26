package com.github.zdziszkee.maze.renderers;

import com.github.zdziszkee.maze.model.Maze;
import com.github.zdziszkee.maze.model.MazeCell;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DefaultRenderer {
    private final Maze maze;
    private final Rectangle[][] rectangles;

    public DefaultRenderer(Maze maze) {
        this.maze = maze;
        this.rectangles = new Rectangle[(maze.getRows())][maze.getColumns()];
    }

    public Scene render() {

        GridPane root = new GridPane();
        Scene scene = new Scene(root, 1000, 1000);
        root.setBackground(Background.fill(Color.BLACK));

        // Create an array to hold all the rectangles
        MazeCell[][] cells = maze.getCells();

        for (int y = 0; y < maze.getRows(); y++) {
            for (int x = 0; x < maze.getColumns(); x++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setStroke(Color.BLACK);
                switch (cells[y][x].getMazeCellType()) {
                    case PATH -> rectangle.setFill(Color.WHITE);
                    case EXIT -> rectangle.setFill(Color.RED);
                    case WALL -> rectangle.setFill(Color.BLACK);
                    case ENTRANCE -> rectangle.setFill(Color.LIME);
                }
                rectangle.widthProperty().bind(root.widthProperty().divide(maze.getColumns()));
                rectangle.heightProperty().bind(root.heightProperty().divide(maze.getRows()));
                root.add(rectangle, x, y);
                rectangles[y][x] = rectangle; // Add rectangle to the array
            }
        }
        return scene;
    }

    public Rectangle[][] getRectangles() {
        return rectangles;
    }
}
