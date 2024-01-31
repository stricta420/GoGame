package org.example.ClientSerwerComunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ComunicateWithoOtherParty {

    private ServerClientSockets serverClientSockets;

    public void sendObjectToClient(Object object) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverClientSockets.SocketSending.getOutputStream());
        objectOutputStream.writeObject(object);
    }

    public Object getObjectFromClient() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(serverClientSockets.SocketGetting.getInputStream());
        return objectInputStream.readObject();
    }

    public void setServerClientSockets(ServerClientSockets serverClientSockets) {
        this.serverClientSockets = serverClientSockets;
    }
}
