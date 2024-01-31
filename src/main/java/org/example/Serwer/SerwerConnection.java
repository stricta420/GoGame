package org.example.Serwer;

import org.example.ClientSerwerComunication.ComunicateWithoOtherParty;
import org.example.ClientSerwerComunication.ServerClientSockets;

import java.io.IOException;
import java.net.ServerSocket;

public class SerwerConnection {


    private final ServerClientSockets clientOneSockets = new ServerClientSockets();
    private final ServerClientSockets clientTwoSockets = new ServerClientSockets();

    private ServerSocket serverSocket;
    private int number_of_players = 0;

    private ServerClientSockets mainSockets = new ServerClientSockets();

    private final ComunicateWithoOtherParty comunicateWithoOtherParty = new ComunicateWithoOtherParty();

    public void startSerwer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void connectNewPlayer() throws IOException {
        if (number_of_players == 0){
            clientOneSockets.SocketSending = serverSocket.accept();
            clientOneSockets.SocketGetting = serverSocket.accept();
            setMainSockets(clientOneSockets);
        }
        else if (number_of_players == 1) {
            clientTwoSockets.SocketSending = serverSocket.accept();
            clientTwoSockets.SocketGetting = serverSocket.accept();
            setMainSockets(clientTwoSockets);
        }
        else {
            System.out.println("ERROR zbyt duża liczba graczy");
        }
        number_of_players += 1;
    }

    public ServerClientSockets getClientOneSockets() {
        return clientOneSockets;
    }

    public ServerClientSockets getClientTwoSockets() {
        return clientTwoSockets;
    }

    public void setMainSockets(ServerClientSockets serverClientSockets) {
        mainSockets = serverClientSockets;
    }

    public void sendObjectToClient(Object object) throws IOException {
        comunicateWithoOtherParty.setServerClientSockets(mainSockets);
        comunicateWithoOtherParty.sendObjectToClient(object);
    }

    public Object getObjectFromClient() throws IOException, ClassNotFoundException {
        comunicateWithoOtherParty.setServerClientSockets(mainSockets);
        return comunicateWithoOtherParty.getObjectFromClient();
    }


}
