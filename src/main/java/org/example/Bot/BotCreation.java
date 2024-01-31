package org.example.Bot;

import org.example.Client.Client;

import java.io.IOException;

public class BotCreation extends Thread {
    @Override
    public void run() {
        Client client = new Client();
        try {
            client.createBotClient();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
