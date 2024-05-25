module com.github.zdziszkee.maze.maze {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.github.zdziszkee.maze to javafx.fxml;
    exports com.github.zdziszkee.maze;
}