package org.example.Client.Gui.Controler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Client.Gui.GuiBoard.GuiBoard;
import org.example.Client.Gui.WinLouseCards.EndGameCard;
import org.example.HelloApplication;

import java.io.IOException;

public class SceneController {

    private Stage stage;
    private Scene scene;

    private int window_size = 700;


    public GuiBoard switchToSceneBord9(Stage stage)  {
        GuiBoard guiBoard = new GuiBoard(9, window_size / 9, this);
        scene = new Scene(guiBoard, guiBoard.getSizeOfGui_x(),guiBoard.getSizeOfGui_y());
        stage.setScene(scene);
        stage.show();
        return guiBoard;
    }

    public GuiBoard switchToSceneBord13(Stage stage)  {
        GuiBoard guiBoard = new GuiBoard(13, window_size / 13, this);
        scene = new Scene(guiBoard, guiBoard.getSizeOfGui_x(),guiBoard.getSizeOfGui_y());
        stage.setScene(scene);
        stage.show();
        return guiBoard;
    }

    public GuiBoard switchToSceneBord19(Stage stage)  {
        GuiBoard guiBoard = new GuiBoard(19, window_size / 19, this);
        scene = new Scene(guiBoard, guiBoard.getSizeOfGui_x(),guiBoard.getSizeOfGui_y());
        stage.setScene(scene);
        stage.show();
        return guiBoard;
    }

    public GuiBoard switchToSceneBaseOnString(String mapSize) {
        if (mapSize.equals("19 X 19")) {
            return switchToSceneBord19(stage);
        } else if (mapSize.equals("13 X 13")) {
            return switchToSceneBord13(stage);
        } else {
            return switchToSceneBord9(stage);
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void switchToEndGameCard(boolean didPlayerWon) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Win_screen.fxml"));
        Parent root = fxmlLoader.load();
        EndGameCard endGameCard = fxmlLoader.getController();
        endGameCard.createRightCard(didPlayerWon);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
