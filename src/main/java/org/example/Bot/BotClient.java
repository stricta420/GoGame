package org.example.Bot;

import org.example.Client.ClientConnection;
import org.example.Player.PlayerGame;
import org.example.ClientSerwerComunication.StringTranslator;
import org.example.GameLogic.GameBoard;

import java.io.IOException;

public class BotClient {

    private ClientConnection clientConnection;

    private PlayerGame playerGame = new PlayerGame();

    private int mapSize;

    private GameBoard gameBoard;


    public BotClient(ClientConnection clientConnection, String mapSize) {
        this.clientConnection = clientConnection;
        this.mapSize = StringTranslator.getMapSize(mapSize);
    }

    public void startBot() throws IOException, ClassNotFoundException {
        playerGame.setPlayerID(2); //Bot is allways 2nd player
        playerGame.setclientConnection(clientConnection); //playerGame can send info to serwer, thats why its important to set player connection
        retardedBotForNow();
    }

    private void retardedBotForNow() throws IOException, ClassNotFoundException {
        while (true) {
            playerGame.startRound();
            while (!playerGame.isMoveMaked()) {
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        playerGame.tryToMakeMove(i, j);
                    }
                }
            }
            gameBoard = playerGame.getGameBoard(); //gets gameBoard object - thats a bord of whole game (arrayList map with all the info and
            //some methods u might use
            playerGame.endRound();
        }
    }
}
