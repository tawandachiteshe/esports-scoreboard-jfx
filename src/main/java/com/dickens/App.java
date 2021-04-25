package com.dickens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;



/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static Stage getStage() {
        return stage;
    }



    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Roboto-Thin.ttf"), 14);
        scene = new Scene(loadFXML("primary"), 1280, 720);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        App.stage = stage;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}

