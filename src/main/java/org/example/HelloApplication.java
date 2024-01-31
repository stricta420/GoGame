package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Client.Gui.Controler.HelloController;

import java.io.IOException;

public class HelloApplication extends Application {

    private HelloController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view2.fxml"));
        Parent root = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public HelloController getController() {
        return this.controller;
    }

    public static void main(String[] args) {
        launch();
    }

    public void startApplication() {
        Platform.runLater(() -> {
            try {
                start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}