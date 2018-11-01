package com.desire;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) {
        try {
            //BorderPane root = new BorderPane();
            GuiFX MainPanel = new GuiFX();
            MainPanel.setVisible(true);
            Scene scene = new Scene(MainPanel,700,500);
            //scene.getStylesheets().add(getClass().getResource("application.css"));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Concordance Generator");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
