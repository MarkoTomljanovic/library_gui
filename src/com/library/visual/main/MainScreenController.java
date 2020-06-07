package com.library.visual.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    //goto member screen
    @FXML
    public void membersScreen(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../members/membersscreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Members Management");
            stage.setScene(new Scene(root, 1500, 900));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();

//            stage.setOnHiding(new EventHandler<WindowEvent>() {
//                @Override
//                public void handle(WindowEvent event) {
//                    Platform.runLater(new Runnable() {
//
//                        @Override
//                        public void run() {
////                            Factory.closeFactory();
//                            System.out.println("Closing Session Factory");
//                            System.exit(0);
//                        }
//                    });
//                }
//            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //goto books screen
    @FXML
    public void bookScreen(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../books/booksscreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Books Management");
            stage.setScene(new Scene(root, 1500, 900));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //goto loans screen
    @FXML
    public void loansScreen(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../loans/loansscreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Loans Management");
            stage.setScene(new Scene(root, 1500, 900));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //exit application
    @FXML
    public void exit(){
        Platform.exit();
    }

}


