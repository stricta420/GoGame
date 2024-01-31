package org.example.Client.Gui.Logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Client.ClientConnection;
import org.example.Client.Gui.Controler.HelloController;
import org.example.HelloApplication;

public class ClientGui extends Application {


    private HelloController helloController;

    private ClientConnection clientConnection;


    private int playerId;

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;

    }
    public void startMainMenu() throws Exception {
        start(new Stage());

    }

    private Stage newStage;

    public void startMap() throws Exception {

        start(new Stage());
        helloController.startMap(this.newStage);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view2.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        this.helloController = fxmlLoader.getController();
        helloController.setPlayerId(playerId);
        helloController.setClientConnection(clientConnection);
        this.newStage = stage;
    }


}
