package edu.co.icesi.main;

import edu.co.icesi.db.MySQLConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;



public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application.fxml"));
            Pane root = (Pane) loader.load();
            //root.getStylesheets().add("/application/application.css");
            //root.getStyleClass().add("pane");
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        MySQLConnection connection = MySQLConnection.getInstance();
        connection.closeDB();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

} //end of c
