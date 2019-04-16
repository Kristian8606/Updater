package sample;


import javafx.application.Application;

import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Update");
        primaryStage.setScene(new Scene(root, 503, 206));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

 */














        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setScene(new Scene(Controller.createContent()));
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }







}
