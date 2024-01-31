package org.example.Player;

import org.example.Client.ClientConnection;
import org.example.GameLogic.GameBoard;

import java.io.IOException;

public class PlayerGame {
    private GameBoard gameBoard;

    private int playerID;

    private ClientConnection clientConnection;



    public void setclientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    private boolean continueGame = true;

    public boolean isContinueGame() {
        return continueGame;
    }

    private boolean moveMaked = false;

    public boolean isMoveMaked() {
        return moveMaked;
    }

    public boolean tryToMakeMove(int x, int y) throws IOException, ClassNotFoundException {
        if (!allMovmentsButContinueGameAndEndGameAreBlocked) {
            if (!moveMaked && gameBoard != null) {
                if (gameBoard.tryToMakeMove(x, y, playerID)) {
                    moveMaked = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean playerSurrender = false;
    public void surrender() {
        if (!allMovmentsButContinueGameAndEndGameAreBlocked) {
            System.out.println("Player has surrender");
            playerSurrender = true;
            moveMaked = true;
            forceGameEnd();
        }
    }

    public void skipMove() {
        if (!allMovmentsButContinueGameAndEndGameAreBlocked) {
            System.out.println("This button works - but its just dosnt do anything");
            moveMaked = true;
            didISkpiedMove = true;
        }
    }

    public void continueButtonClicked() {
        if (allMovmentsButContinueGameAndEndGameAreBlocked) {
            moveMaked = true;
            allMovmentsButContinueGameAndEndGameAreBlocked = false;
        }
    }

    private boolean theOtherPlayerPressedEndGameButton = false;

    private boolean didIPressedEndGameAfterBothSkips = false;
    public void endGameButtonClicked() {
        System.out.println("button clicked");
        if (allMovmentsButContinueGameAndEndGameAreBlocked) {
            System.out.println("button run");
            didIPressedEndGameAfterBothSkips = true;
            moveMaked = true;
            //first we would need to check if other player also clicked it but
            if (theOtherPlayerPressedEndGameButton) {
                System.out.println("ForceGameEnd started wtf");
                forceGameEnd();
            }
        }
    }


    public void forceGameEnd() {
        this.doIWantToContinuePlaying = false;
    }
    private boolean bothPlayersWantToContinuePlaying = true;
    private boolean doIWantToContinuePlaying = true;

    private boolean didISkpiedMove = false;

    private boolean didOtherPlayerSkpiedMove = false;


    private boolean allMovmentsButContinueGameAndEndGameAreBlocked = false;
    private void checkIfWeBothSkipedMove() {
        if (this.didISkpiedMove && this.didOtherPlayerSkpiedMove) {
            System.out.println("BOTH SKIPED PAPAPA");
            this.allMovmentsButContinueGameAndEndGameAreBlocked = true;
        }
    }
    public void startRound() throws IOException, ClassNotFoundException {

        this.bothPlayersWantToContinuePlaying = (boolean) clientConnection.getObjectFromSerwer();
        this.didOtherPlayerSkpiedMove = (boolean) clientConnection.getObjectFromSerwer();
        this.theOtherPlayerPressedEndGameButton = (boolean) clientConnection.getObjectFromSerwer();
        if (this.theOtherPlayerPressedEndGameButton) {
            this.allMovmentsButContinueGameAndEndGameAreBlocked = true;
        }
        checkIfWeBothSkipedMove();
        this.didISkpiedMove = false;
        if (bothPlayersWantToContinuePlaying) {
            this.gameBoard = (GameBoard) clientConnection.getObjectFromSerwer();
            moveMaked = false;
            gameBoard.setPlayerid(playerID);
        } else {
            moveMaked = true;
        }
    }

    public void endRound() throws IOException, ClassNotFoundException {

        if (bothPlayersWantToContinuePlaying) {
            clientConnection.sendObjectToSerwer(gameBoard);
            clientConnection.sendObjectToSerwer(doIWantToContinuePlaying);
            clientConnection.sendObjectToSerwer(didISkpiedMove);
            clientConnection.sendObjectToSerwer(didIPressedEndGameAfterBothSkips);

        } else {
            endGame();
        }
    }

    private boolean didIWon;

    public boolean isDidIWon() {
        System.out.println("didIwon: ");
        System.out.println(didIWon);
        return didIWon;
    }

    public void endGame() throws IOException, ClassNotFoundException {
        clientConnection.sendObjectToSerwer(this.playerSurrender);
        System.out.println("W8 for serwer obj");
        this.didIWon = (boolean) clientConnection.getObjectFromSerwer();
        System.out.println("Got first boolean object");
        System.out.println("got serwer obj");
        this.continueGame = false;
    }

    public void endMyLife() throws IOException {
        clientConnection.sendObjectToSerwer(null);
        System.out.println("Goodbye cruel world!");
    }



}
