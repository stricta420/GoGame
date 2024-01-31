package org.example.Serwer;

import org.example.ClientSerwerComunication.StringTranslator;
import org.example.GameLogic.GameBoard;
import org.example.GameLogic.GameInfo;
import org.example.GameLogic.SingleFiled;

import java.io.IOException;
import java.util.ArrayList;

public class SerwerGame {

    private GameBoard gameBoard = new GameBoard();

    private SerwerConnection serwerConnection;

    private ArrayList<ArrayList<SingleFiled>> knocedRocks = new ArrayList<>();

    public ArrayList<ArrayList<SingleFiled>> getKnocedRocks() {
        return knocedRocks;
    }

    private ArrayList<SingleFiled> moves = new ArrayList<>();

    public ArrayList<SingleFiled> getMoves() {
        return moves;
    }

    public SerwerGame(SerwerConnection serwerConnection, GameInfo gameInfo) {
        gameBoard.createMap(StringTranslator.getMapSize(gameInfo.getMapSize()), StringTranslator.getMapSize(gameInfo.getMapSize()));
        this.serwerConnection = serwerConnection;
    }

    private boolean continueGame = true;
    public void startGame() throws IOException, ClassNotFoundException, InterruptedException {
        while (continueGame) {
            if (!bothPlayersWantToContinuePlaying) {
                continueGame = false;
            }
            playRound();
        }
        gameEnd(decideWhoWon());
    }

    private int decideWhoWon() throws IOException, ClassNotFoundException {
        serwerConnection.setMainSockets(serwerConnection.getClientOneSockets());
        boolean playerOneLost = (boolean) serwerConnection.getObjectFromClient();
        serwerConnection.setMainSockets(serwerConnection.getClientTwoSockets());
        boolean playerTwoLost = (boolean) serwerConnection.getObjectFromClient();
        if (playerOneLost) {
            return 2;
        }
        if (playerTwoLost) {
            return 1;
        }

        return gameBoard.whoWon();
    }


    private void gameEnd(int winner) throws IOException, InterruptedException {

        System.out.println("Game end has been started");
        boolean client1Info;
        boolean client2Info;
        if (winner == 1) {
            System.out.println("Player one won");
            client1Info = true;
            client2Info = false;
        } else {
            System.out.println("Player two won");
            client1Info = false;
            client2Info = true;
        }
        //Send info to clients if thay win or nah
        serwerConnection.setMainSockets(serwerConnection.getClientOneSockets());
        serwerConnection.sendObjectToClient(client1Info);
        serwerConnection.setMainSockets(serwerConnection.getClientTwoSockets());
        serwerConnection.sendObjectToClient(client2Info);
        Thread.sleep(10000);
    }

    private boolean bothPlayersWantToContinuePlaying = true;

    private boolean firstTime = true;

    private boolean didFirstPlayerSkipedMove = false;
    private boolean didSecondPlayerSkipedMove = false;

    private boolean didFirstPlayerPressedEndGameButtonAfterTwoSkips = false;
    private boolean didSecondPlayerPressedEndGameButtonAfterTwoSkips = false;


    private void playRound() throws IOException, ClassNotFoundException {
        serwerConnection.setMainSockets(serwerConnection.getClientOneSockets());
        serwerConnection.sendObjectToClient(bothPlayersWantToContinuePlaying);
        serwerConnection.sendObjectToClient(didSecondPlayerSkipedMove);
        serwerConnection.sendObjectToClient(didSecondPlayerPressedEndGameButtonAfterTwoSkips);
        if (bothPlayersWantToContinuePlaying) {
            playSiglePlayer();
            chceckEndGame();
            this.didFirstPlayerSkipedMove = (boolean) serwerConnection.getObjectFromClient();
            this.didFirstPlayerPressedEndGameButtonAfterTwoSkips = (boolean) serwerConnection.getObjectFromClient();
        }


        serwerConnection.setMainSockets(serwerConnection.getClientTwoSockets());

        if (bothPlayersWantToContinuePlaying) {
            serwerConnection.sendObjectToClient(bothPlayersWantToContinuePlaying);
            serwerConnection.sendObjectToClient(didFirstPlayerSkipedMove);
            serwerConnection.sendObjectToClient(didFirstPlayerPressedEndGameButtonAfterTwoSkips);
            playSiglePlayer();
            chceckEndGame();
            this.didSecondPlayerSkipedMove = (boolean) serwerConnection.getObjectFromClient();
            this.didSecondPlayerPressedEndGameButtonAfterTwoSkips = (boolean) serwerConnection.getObjectFromClient();
        } else if (firstTime)  {
            firstTime = false;
            serwerConnection.sendObjectToClient(bothPlayersWantToContinuePlaying);
            serwerConnection.sendObjectToClient(didFirstPlayerSkipedMove);
            serwerConnection.sendObjectToClient(didFirstPlayerPressedEndGameButtonAfterTwoSkips);
        }
    }

    private void chceckEndGame() throws IOException, ClassNotFoundException {
        this.bothPlayersWantToContinuePlaying = (boolean) serwerConnection.getObjectFromClient();
    }

    private void playSiglePlayer() throws IOException, ClassNotFoundException {
        serwerConnection.sendObjectToClient(gameBoard);
        this.gameBoard = (GameBoard) serwerConnection.getObjectFromClient();

        knocedRocks.add(this.gameBoard.getKnockedRocks());
        moves.add(this.gameBoard.getLastMove());
    }

}
