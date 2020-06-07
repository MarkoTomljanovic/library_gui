package com.library;

import com.library.entity.Factory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("visual/main/mainscreen.fxml"));
        primaryStage.setTitle("Library Management");
        primaryStage.setScene(new Scene(root, 1500, 900));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        Factory.getSessionFactory();
    }

    @Override
    public void stop() throws Exception {
        Factory.closeFactory();
    }
}
