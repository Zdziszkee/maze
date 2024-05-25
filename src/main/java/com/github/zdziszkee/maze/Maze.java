package com.github.zdziszkee.maze;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Maze extends Application {


    private Parent createContent() {
        Rectangle box = new Rectangle(100, 50);

        transform(box);

        return new Pane(box);
    }

    private void transform(Rectangle box) {
        box.setRotate(30);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent(), 300, 300, Color.GRAY));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

