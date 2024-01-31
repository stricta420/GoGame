package org.example.Serwer;

import org.example.ClientSerwerComunication.ComunicateWithoOtherParty;
import org.example.ClientSerwerComunication.StringTranslator;
import org.example.DataBase.DataBaseFirstClass;
import org.example.GameLogic.GameBoard;
import org.example.GameLogic.GameInfo;

import java.io.IOException;


public class Server {

    private static final int PORT = 12345;


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        SerwerConnection serwerConnection = new SerwerConnection();
        serwerConnection.startSerwer(PORT);
        System.out.println("serwer set, now connecting clients");
        ConnectClients connectClients = new ConnectClients(serwerConnection);
        connectClients.connectClients();
        System.out.println("clients connected");
        GameInfo gameInfo = connectClients.getGameInfo();
        SerwerGame serwerGame = new SerwerGame(serwerConnection, gameInfo);
        System.out.println("Started serwerGame");
        serwerGame.startGame();
        System.out.println("Ended game");
        DataBaseFirstClass dataBaseFirstClass = new DataBaseFirstClass();
        dataBaseFirstClass.setMoves(serwerGame.getMoves());
        dataBaseFirstClass.setGameInfo(gameInfo);
        dataBaseFirstClass.setKnockedRocks(serwerGame.getKnocedRocks());
        dataBaseFirstClass.saveToDataBase();
        System.out.println("Game saved - Good night");


    }







}

