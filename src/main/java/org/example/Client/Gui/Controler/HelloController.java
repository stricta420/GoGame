package org.example.Client.Gui.Controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.example.Client.ClientConnection;
import org.example.Client.Gui.GuiBoard.GuiBoard;

public class HelloController implements Initializable {

    private String gameMode;
    private String mapSize;

    private SceneController sceneController = new SceneController();
    private ActionEvent event;

    private ClientConnection clientConnection;
    private GuiBoard guiBoard;

    private int playerId;

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @FXML
    protected void onClickStartGame(ActionEvent event) throws IOException {
        this.gameMode = getGameMode();
        this.mapSize = getMapSize();
        this.event = event;
        clientConnection.sendObjectToSerwer(this.mapSize);
        clientConnection.sendObjectToSerwer(this.gameMode);
        startGameBordGUI();
    }

    public void startMap(Stage stage) throws IOException, ClassNotFoundException {
        this.mapSize = (String) clientConnection.getObjectFromSerwer();
        sceneController.setStage(stage);
        this.guiBoard = sceneController.switchToSceneBaseOnString(mapSize);
        this.guiBoard.setclientConnection(clientConnection);
        this.guiBoard.setPlayerId(playerId);
        this.guiBoard.startSteringGame();
    }


    @FXML
    private ChoiceBox<String> ChoiceBoxGameMode;
    @FXML
    private ChoiceBox<String> ChoiceBoxMapSize;
    @FXML
    private Label LabelGameMode;
    @FXML
    private Label LabelMapSize;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] GameModeOptions = {"Player vs Player", "Player vs Bot"};
        String[] MapSizeOptions = {"19 X 19", "13 X 13", "9 X 9"};
        ChoiceBoxGameMode.getItems().addAll(GameModeOptions);
        ChoiceBoxMapSize.getItems().addAll(MapSizeOptions);
        ChoiceBoxGameMode.setOnAction(this::changeLabelGameMode);
        ChoiceBoxMapSize.setOnAction(this::changeLabelMapSize);


    }

    public void changeLabelGameMode(ActionEvent event) {
        LabelGameMode.setText(ChoiceBoxGameMode.getValue());

    }

    public void changeLabelMapSize(ActionEvent event) {
        LabelMapSize.setText(ChoiceBoxMapSize.getValue());

    }



    public String getGameMode() {
        return LabelGameMode.getText();
    }

    public String getMapSize() {
        return LabelMapSize.getText();
    }



    private void startGameBordGUI() {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        sceneController.setStage(stage);
        this.guiBoard = sceneController.switchToSceneBaseOnString(this.mapSize);
        this.guiBoard.setclientConnection(clientConnection);
        this.guiBoard.setPlayerId(playerId);
        this.guiBoard.startSteringGame();
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }
}