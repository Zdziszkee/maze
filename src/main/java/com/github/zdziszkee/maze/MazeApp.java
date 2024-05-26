package com.github.zdziszkee.maze;

import com.github.zdziszkee.maze.generators.DefaultGenerator;
import com.github.zdziszkee.maze.model.Maze;
import com.github.zdziszkee.maze.model.MazeCell;
import com.github.zdziszkee.maze.model.MazeCellType;
import com.github.zdziszkee.maze.renderers.DefaultRenderer;
import com.github.zdziszkee.maze.solver.DeafultSolver;
import com.github.zdziszkee.maze.solver.Solution;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;



public class MazeApp extends Application {


    @Override
    public void start(Stage primaryStage) {
        Maze maze = new Maze(20, 20);
        DefaultGenerator generator = new DefaultGenerator();
        generator.generate(maze);
        DefaultRenderer defaultRenderer = new DefaultRenderer(maze);
        Scene scene = defaultRenderer.render();

        DeafultSolver deafultSolver = new DeafultSolver();
        Solution solution = deafultSolver.solve(maze);
        Rectangle[][] rectangles = defaultRenderer.getRectangles();
        SequentialTransition sequentialTransition = new SequentialTransition();


        for (MazeCell vertex : solution.visited()) {
            FillTransition fillTransition = new FillTransition(Duration.seconds(0.1), rectangles[vertex.getRow()][vertex.getColumn()]);
            fillTransition.setFromValue((Color) rectangles[vertex.getRow()][vertex.getColumn()].getFill());
            fillTransition.setToValue(Color.BLUE);
            fillTransition.setCycleCount(1);
            fillTransition.setAutoReverse(false);
            sequentialTransition.getChildren().add(fillTransition);
        }


        for (MazeCell vertex : solution.solution()) {
            FillTransition fillTransition = new FillTransition(Duration.seconds(0.1), rectangles[vertex.getRow()][vertex.getColumn()]);
            fillTransition.setFromValue((Color) rectangles[vertex.getRow()][vertex.getColumn()].getFill());
            fillTransition.setToValue(Color.YELLOW);
            fillTransition.setCycleCount(1);
            fillTransition.setAutoReverse(false);
            sequentialTransition.getChildren().add(fillTransition);
        }

        sequentialTransition.play();
        primaryStage.setScene(scene); // Set initial scene size
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

