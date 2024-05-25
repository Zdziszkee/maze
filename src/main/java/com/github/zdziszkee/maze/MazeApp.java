package com.github.zdziszkee.maze;

import com.github.zdziszkee.maze.generators.DefaultGenerator;
import com.github.zdziszkee.maze.model.Maze;
import com.github.zdziszkee.maze.model.MazeCell;
import com.github.zdziszkee.maze.model.MazeCellType;
import com.github.zdziszkee.maze.renderers.DefaultRenderer;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class MazeApp extends Application {


    boolean inInRange(int row,int column,MazeCell[][] cellMatrix){
        return row >= 0 && row < cellMatrix.length && column >= 0 && column < cellMatrix[0].length;
    }
    private List<MazeCell> getNeighbours(int row,int column,MazeCell[][] cellMatrix) {
        List<MazeCell> neighbours = new ArrayList<>();
        if (inInRange(row+1,column,cellMatrix))neighbours.add(cellMatrix[row+1][column]);
        if (inInRange(row-1,column,cellMatrix))neighbours.add(cellMatrix[row-1][column]);
        if (inInRange(row,column+1,cellMatrix))neighbours.add(cellMatrix[row][column+1]);
        if (inInRange(row,column-1,cellMatrix))neighbours.add(cellMatrix[row][column-1]);
        if (inInRange(row+1,column+1,cellMatrix))neighbours.add(cellMatrix[row+1][column+1]);
        if (inInRange(row+1,column -1 ,cellMatrix))neighbours.add(cellMatrix[row+1][column-1]);
        if (inInRange(row-1,column+1,cellMatrix)) neighbours.add(cellMatrix[row-1][column+1]);
        if (inInRange(row-1,column-1,cellMatrix))neighbours.add(cellMatrix[row-1][column-1]);
    return neighbours;

    }
    @Override
    public void start(Stage primaryStage) {
        Maze maze = new Maze(20, 20);
        DefaultGenerator generator = new DefaultGenerator();
        generator.generate(maze);
        DefaultRenderer defaultRenderer = new DefaultRenderer(maze);
        Scene scene = defaultRenderer.render();

        MazeCell[][] cells = maze.getCells();

        final Map<MazeCell, List<MazeCell>> adjacencyList = new HashMap<>();

        for (int row = 0; row < maze.getRows(); row++) {
            for (int col = 0; col < maze.getColumns(); col++) {
                MazeCell cell = cells[row][col];

                int finalRow = row;
                int finalCol = col;
                adjacencyList.compute(cell, (key, value) -> {
                    if (value == null) {
                        value = new ArrayList<>();
                    }
                    value.addAll(getNeighbours(finalRow, finalCol,cells).stream().filter(cell1->cell1.getMazeCellType()==MazeCellType.PATH || cell1.getMazeCellType()==MazeCellType.ENTRANCE || cell1.getMazeCellType() == MazeCellType.EXIT).toList());

                    return value;
                });
            }
        }
        Rectangle[][] rectangles = defaultRenderer.getRectangles();

        SequentialTransition sequentialTransition = new SequentialTransition();

        MazeCell entrance = adjacencyList.keySet().stream().filter(cell -> cell.getMazeCellType() == MazeCellType.ENTRANCE).findAny().get();
        MazeCell exit = adjacencyList.keySet().stream().filter(cell -> cell.getMazeCellType() == MazeCellType.EXIT).findAny().get();

        Queue<MazeCell> vertices = new ArrayDeque<>();

        vertices.add(entrance);
        Map<MazeCell,MazeCell> parents = new HashMap<>();
        Map<MazeCell,Integer> visited = new HashMap<>();

        visited.put(entrance,0);
        ArrayList<MazeCell> finalPath;
       outer: while (!vertices.isEmpty()){
            MazeCell vertex = vertices.poll();

            FillTransition fillTransition = new FillTransition(Duration.seconds(0.1), rectangles[vertex.getRow()][vertex.getColumn()]);
            fillTransition.setFromValue((Color) rectangles[vertex.getRow()][vertex.getColumn()].getFill());
            fillTransition.setToValue(Color.BLUE);
            fillTransition.setCycleCount(1);
            fillTransition.setAutoReverse(false);
            sequentialTransition.getChildren().add(fillTransition);
            List<MazeCell> neighbours = adjacencyList.get(vertex);

            for (MazeCell neighbour : neighbours) {
                if(!visited.containsKey(neighbour)){
                    vertices.add(neighbour);
                    visited.put(neighbour,visited.getOrDefault(neighbour,0));

                    parents.put(neighbour,vertex);
                }
                if(neighbour.equals(exit)){
                    ArrayList<MazeCell> shortestPath = new ArrayList<>();
                    MazeCell current = neighbour;
                    while (parents.containsKey(current)){
                        shortestPath.addFirst(current);
                        current = parents.get(current);
                    }
                    shortestPath.addFirst(entrance);
                    finalPath = shortestPath;
                    break outer;
                }
            }
        }



//
//        // Create a SequentialTransition to run animations sequentially
//
//        // Add animations to change rectangle colors sequentially
//        for (int y = 0; y < 20; y++) {
//            for (int x = 0; x < 20; x++) {
//                FillTransition fillTransition = new FillTransition(Duration.seconds(0.5), rectangles[y][x]);
//                fillTransition.setFromValue((Color) rectangles[y][x].getFill());
//                fillTransition.setToValue(Color.RED);
//                fillTransition.setCycleCount(1);
//                fillTransition.setAutoReverse(false);
//                sequentialTransition.getChildren().add(fillTransition);
//            }
//        }

        // Play the SequentialTransition
        sequentialTransition.play();


        primaryStage.setScene(scene); // Set initial scene size
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

