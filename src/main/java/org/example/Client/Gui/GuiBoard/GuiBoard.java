package org.example.Client.Gui.GuiBoard;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.example.Client.ClientConnection;
import org.example.Client.Gui.Controler.SceneController;
import org.example.Player.PlayerGame;
import org.example.GameLogic.GameBoard;
import org.example.GameLogic.SingleFiled;

import java.io.IOException;
import java.util.ArrayList;


/**
 * this class connects GuiField with an actuall GUI and provides connection between gui and logic (Gamebord)
 * this is ultimate class for game bord
 */
public class GuiBoard extends HBox {

    private int size_x;
    private int size_y;

    private ClientConnection clientConnection;

    private GameBoard gameBoard = new GameBoard();

    private ArrayList<ArrayList<GuiField>> listOfGuiFields = new ArrayList<>();

    private GuiBoardView guiBoardView;

    private PlayerGame playerGame = new PlayerGame();

    private int playerId;

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    private SceneController sceneController;

    public GuiBoard(int size, int size_of_square, SceneController sceneController) {
        GuiBoardView guiBoardView = new GuiBoardView();
        guiBoardView.crateGuiBoardView(size,size_of_square,this);
        this.guiBoardView = guiBoardView;
        this.size_x = guiBoardView.getSize_x();
        this.size_y = guiBoardView.getSize_y();
        this.listOfGuiFields = guiBoardView.getListOfGuiFields();
        createGuiBord();
        setOnClicks();
        this.sceneController = sceneController;

    }

    public void startSteringGame() {
        playerGame.setPlayerID(playerId);
        playerGame.setclientConnection(clientConnection);

        Thread threadPlayerGame = new Thread(() -> {
            try {
                starePlayerGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        threadPlayerGame.start();
    }
    private void createGuiBord() {
        getChildren().add(guiBoardView.getGridPane());
        getChildren().add(guiBoardView.getMiniLayout());
    }

    private void setOnClicks() {
        this.guiBoardView.getButtonSkipMove().setOnAction(e -> handleButtonSkipMove());
        this.guiBoardView.getButtonSurrender().setOnAction(e -> handleButtonSurender());
        this.guiBoardView.getEndGameButton().setOnAction(e -> {
            try {
                hendleButtonEndGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        this.guiBoardView.getContinueGameButton().setOnAction(e -> hendleButtonContinueGame());
    }



    public void recreateGameBoardOnGUI() {
        ArrayList<ArrayList<SingleFiled>> gameMap = gameBoard.getGameMap();
        for (int y = 0; y < gameMap.size(); y++) {
            ArrayList<SingleFiled> gameMapLine = gameMap.get(y);
            for (int x = 0; x < gameMapLine.size(); x++) {
                SingleFiled singleFiled = gameMapLine.get(x);
                GuiField guiField = listOfGuiFields.get(y).get(x);
                if (singleFiled.get_player_id() == 0) {
                    guiField.deleteStoneFromMe();
                    continue;
                }

                Color color = Color.BLACK;
                if (singleFiled.get_player_id() == 2) {
                    color = Color.WHITE;
                }
                guiField.addStoneToMe(color);

            }
        }
    }

    private void handleButtonSurender() {
        playerGame.surrender();
    }

    private void handleButtonSkipMove() {
        playerGame.skipMove();

    }

    private void hendleButtonContinueGame() {
        playerGame.continueButtonClicked();
    }

    private void hendleButtonEndGame() throws IOException {
        playerGame.endGameButtonClicked();
    }


    public synchronized boolean clickOnGuiField(int x, int y) {

        try {
            playerGame.tryToMakeMove(x,y);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public int getSizeOfGui_x() {
        return this.size_x;
    }
    public int getSizeOfGui_y() {
        return this.size_y;
    }

    public void setclientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    private void wholeRecreation() {
        if (playerGame.getGameBoard() != null) {
            this.gameBoard = playerGame.getGameBoard();
            recreateGameBoardOnGUI();
        }
    }

    private void starePlayerGame() throws IOException, ClassNotFoundException, InterruptedException {
        while (playerGame.isContinueGame()) {
            Platform.runLater(this::wholeRecreation);
            playerGame.startRound();
            Platform.runLater(this::wholeRecreation);
            while (!playerGame.isMoveMaked()) {
                Thread.sleep(1000);
                System.out.println("waiting");
            }
            playerGame.endRound();
        }
        Platform.runLater(this::endGame);
    }

    /**
     * Problem występuje tutaj bo sceneController nie jest wywoływany przez główny wątek javy
     * starePlayerGame jest wywoływany przez startSteringGame a ta metoda tworzy nowy wątek
     * @throws IOException
     */
    private void endGame()  {
        if (playerGame.isDidIWon()) {
            System.out.println("Player won");
        } else {
            System.out.println("Player lost");
        }

        try {
            sceneController.switchToEndGameCard(playerGame.isDidIWon());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
