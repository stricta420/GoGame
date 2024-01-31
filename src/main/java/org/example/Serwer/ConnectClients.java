package org.example.Serwer;

import org.example.Bot.BotClient;
import org.example.Bot.BotCreation;
import org.example.ClientSerwerComunication.StringTranslator;
import org.example.GameLogic.GameInfo;

import java.io.IOException;

public class ConnectClients {

    private SerwerConnection serwerConnection;

    private GameInfo gameInfo = new GameInfo();

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public ConnectClients(SerwerConnection serwerConnection) {
        this.serwerConnection = serwerConnection;
    }
    private final StringTranslator stringTranslator = new StringTranslator();
    public void connectClients() throws IOException, ClassNotFoundException {
        connectFirstClient();
        if (stringTranslator.isGameModeGuiPlayer(gameInfo.getGameMode())) {
            connectSecondClient();
        }
        else {
            connectBotClient();
        }

    }

    private void connectFirstClient() throws IOException, ClassNotFoundException {
        serwerConnection.connectNewPlayer();

        serwerConnection.sendObjectToClient(true);

        String mapSize = (String) serwerConnection.getObjectFromClient();
        String gameMode = (String) serwerConnection.getObjectFromClient();
        gameInfo.setMapSize(mapSize);
        gameInfo.setGameMode(gameMode);

    }

    private void connectSecondClient() throws IOException {
        serwerConnection.connectNewPlayer();
        serwerConnection.sendObjectToClient(false);
        //serwerConnection.sendObjectToClient(stringTranslator.isGameModeGuiPlayer(gameInfo.getGameMode()));
        serwerConnection.sendObjectToClient(gameInfo.getMapSize());
    }

    private void connectBotClient() throws IOException {
        BotCreation botCreation = new BotCreation();
        botCreation.start();
        serwerConnection.connectNewPlayer();
        serwerConnection.sendObjectToClient(gameInfo.getMapSize());

    }

}
