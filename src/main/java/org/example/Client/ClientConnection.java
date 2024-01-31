package org.example.Client;

import org.example.ClientSerwerComunication.ComunicateWithoOtherParty;
import org.example.ClientSerwerComunication.ServerClientSockets;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection {

    private final ServerClientSockets serverClientSockets = new ServerClientSockets();

    private final ComunicateWithoOtherParty comunicateWithSerwer = new ComunicateWithoOtherParty();



    public void connectToSerwer(String SERVER_ADDRESS, int PORT) throws IOException {
        Socket getingSocket = new Socket(SERVER_ADDRESS, PORT);
        Socket sendingSocket = new Socket(SERVER_ADDRESS, PORT);
        serverClientSockets.SocketGetting = getingSocket;
        serverClientSockets.SocketSending = sendingSocket;
    }

    public void sendObjectToSerwer(Object object) throws IOException {
        comunicateWithSerwer.setServerClientSockets(serverClientSockets);
        comunicateWithSerwer.sendObjectToClient(object);
    }

    public Object getObjectFromSerwer() throws IOException, ClassNotFoundException {
        comunicateWithSerwer.setServerClientSockets(serverClientSockets);
        return comunicateWithSerwer.getObjectFromClient();
    }



}
