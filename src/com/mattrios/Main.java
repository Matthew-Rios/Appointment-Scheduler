package com.mattrios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Matt Rios
 */
public class Main extends Application {

    /**
     * Displays the loginForm
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login/loginForm.fxml"));
        primaryStage.setTitle("Global Consulting");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * Displays the loginForm and calls the initialize method which will create the tables if they don't already exist.
     */
    public static void main(String[] args) {
        Database.initialize();
        launch(args);
    }
}
