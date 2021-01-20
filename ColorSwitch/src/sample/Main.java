package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.primaryStage = primaryStage;
        Controller controller = new Controller();
        controller.mainMenu();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
