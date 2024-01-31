package org.example.Client;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.Bot.BotClient;
import org.example.Client.Gui.Logic.ClientGui;

import java.io.IOException;

public class Client extends Application{

    private static final int PORT = 12345;

    private static final String SERVER_ADDRESS = "localhost";

    private static final ClientConnection clientConnection = new ClientConnection();


    public static void main(String[] args)  {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        clientConnection.connectToSerwer(SERVER_ADDRESS, PORT);

        boolean isPrimary = (boolean) clientConnection.getObjectFromSerwer();
        if (isPrimary) {
            ClientGui clientGui = new ClientGui();
            clientGui.setPlayerId(1);
            clientGui.setClientConnection(clientConnection);
            clientGui.startMainMenu();
        }
        else {

            ClientGui clientGui = new ClientGui();
            clientGui.setPlayerId(2);
            clientGui.setClientConnection(clientConnection);
            clientGui.startMap();

        }
    }

    public void createBotClient() throws IOException, ClassNotFoundException {
        clientConnection.connectToSerwer(SERVER_ADDRESS, PORT);
        String mapSize = (String) clientConnection.getObjectFromSerwer();
        BotClient botClient = new BotClient(clientConnection, mapSize);
        botClient.startBot();
    }

}
