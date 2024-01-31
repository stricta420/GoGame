package org.example.GameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class GameBoard implements Serializable, GameBoardInterface {
    // 0 - no player, 1 - player1, 2 - player 2
    private ArrayList<ArrayList<SingleFiled>> gameMap = new ArrayList<>();
    private int mapHeight = 0;
    private int mapWidth = 0;

    private int playerId = 1;

    private int enemyPlayerId = 2;

    private SingleFiled lastMove;


    public SingleFiled getLastMove() {
        return lastMove;
    }



    private ArrayList<SingleFiled> knockedRocks = new ArrayList<>();

    private int numberOfSlavesPlayerOne = 0;
    private int numberOfSlavesPlayerTwo = 0;

    public ArrayList<SingleFiled> getKnockedRocks() {

        return knockedRocks;
    }

    public void setPlayerid(int playerid) {
        this.playerId = playerid;
        if (playerid == 1) {
            this.enemyPlayerId = 2;
        } else {
            this.enemyPlayerId = 1;
        }
    }
    public void createMap(int height, int width) {
        mapHeight = height;
        mapWidth = width;
        for (int i = 0; i < height; i++) {
            ArrayList<SingleFiled> map_line = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                SingleFiled field = new SingleFiled(i, j);
                map_line.add(field);
            }
            gameMap.add(map_line);
        }
    }

    public ArrayList<ArrayList<SingleFiled>> getGameMap() {
        return gameMap;
    }

    public ArrayList<SingleFiled> getSurroundings(int x, int y) {
        ArrayList<SingleFiled> surroundings = new ArrayList<>();

        if (x != 0) {
            surroundings.add(gameMap.get(x-1).get(y));
        }
        if (x != mapHeight - 1) {
            surroundings.add(gameMap.get(x+1).get(y));
        }
        if (y != 0) {
            surroundings.add(gameMap.get(x).get(y-1));
        }
        if (y != mapWidth - 1) {
            surroundings.add(gameMap.get(x).get(y + 1));
        }
        return surroundings;
    }

    private ArrayList<SingleFiled> findConnectedStructure(int x, int y,ArrayList<SingleFiled> connectedStructure, ArrayList<SingleFiled> allreadyChecked, int playerId) {
        SingleFiled thisField = gameMap.get(x).get(y);
        if (!allreadyChecked.contains(thisField)) {
            allreadyChecked.add(thisField);
            connectedStructure.add(thisField);
        }
        ArrayList<SingleFiled> surrandings = getSurroundings(x,y);
        for (SingleFiled surrand : surrandings) {
            if (allreadyChecked.contains(surrand)) {
                continue;
            }
            allreadyChecked.add(surrand);
            if (surrand.get_player_id() == playerId) {
                connectedStructure.add(surrand);
                connectedStructure = findConnectedStructure(surrand.getX(), surrand.getY(), connectedStructure, allreadyChecked, playerId);
            }
        }
        return connectedStructure;
    }

    public ArrayList<SingleFiled> getConnectedStructure(int x, int y) {
        int playerid = gameMap.get(x).get(y).get_player_id();
        ArrayList<SingleFiled> connectedStructure = new ArrayList<>();
        ArrayList<SingleFiled> allreadyChecked = new ArrayList<>();
        return findConnectedStructure(x,y, connectedStructure, allreadyChecked, playerid);
    }

    public ArrayList<SingleFiled> findRocksThatMoveWillStrungle(int x, int y) {
        SingleFiled oldField = gameMap.get(x).get(y);
        SingleFiled newField = new SingleFiled(x, y);
        newField.set_player_id(this.playerId);
        gameMap.get(x).set(y, newField);
        ArrayList<SingleFiled> allRocksThatHaveZeroBreaths = new ArrayList<>();
        ArrayList<SingleFiled> surrandings = getSurroundings(x, y);
        for (SingleFiled surrander : surrandings) {
            if (getNumberOfBreaths(surrander.getX(), surrander.getY(), this.enemyPlayerId) == 0 && surrander.get_player_id() == this.enemyPlayerId) {
                allRocksThatHaveZeroBreaths.addAll(getConnectedStructure(surrander.getX(), surrander.getY()));
            }
        }
        gameMap.get(x).set(y, oldField);
        return allRocksThatHaveZeroBreaths;
    }


    private int findNumberOfBreathsOfWholeStructure(int x, int y, ArrayList<SingleFiled> allreadyCounted, int counter, int playerId) {
        ArrayList<SingleFiled> surrandigs = getSurroundings(x,y);
        allreadyCounted.add(gameMap.get(x).get(y));
        for (SingleFiled surrander : surrandigs) {
            if (!allreadyCounted.contains(surrander)) {
                allreadyCounted.add(surrander);
                if (surrander.get_player_id() != playerId && surrander.get_player_id() != 0) {
                    continue;
                }
                if (surrander.get_player_id() == 0) {
                    counter++;
                } else {
                    counter = findNumberOfBreathsOfWholeStructure(surrander.getX(), surrander.getY(), allreadyCounted, counter, playerId);
                }
            }

        }
        return counter;
    }




    public void addStone(int x, int y, int playerid) {
        this.gameMap.get(x).get(y).set_player_id(playerid);
        lastMove = this.gameMap.get(x).get(y);
    }

    public boolean checkIfMoveIsValid(int x, int y, ArrayList<SingleFiled> rocksThatMoveWillStrungle) {
        if (this.gameMap.get(x).get(y).get_player_id() != 0) {
            return false;
        }
        for (SingleFiled knocedRock : knockedRocks) {
            if (knocedRock.getX() == x && knocedRock.getY() == y) {
                return false;
            }
        }
        if (getNumberOfBreaths(x, y, this.playerId) == 0) {
            if (rocksThatMoveWillStrungle.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        return true;
    }


    private void stragleAll(ArrayList<SingleFiled> rocksToStangle) {
        knockedRocks.clear();
        for (SingleFiled rock : rocksToStangle) {
            rock.set_player_id(0);
            knockedRocks.add(rock);
        }
        if (playerId == 1) {
            numberOfSlavesPlayerOne += knockedRocks.size();
        } else {
            numberOfSlavesPlayerTwo += knockedRocks.size();
        }
    }

    public int getNumberOfBreaths(int x, int y, int playerId) {
        ArrayList<SingleFiled> allreadyCounted = new ArrayList<>();
        return findNumberOfBreathsOfWholeStructure(x,y, allreadyCounted, 0, playerId);
    }

    public boolean tryToMakeMove(int x, int y, int playerId) {
        this.playerId = playerId;
        ArrayList<SingleFiled> rocksThatMoveWillStrungle = findRocksThatMoveWillStrungle(x,y);
        if (!checkIfMoveIsValid(x,y,rocksThatMoveWillStrungle)) {
            return false;
        }
        addStone(x,y,playerId);
        stragleAll(rocksThatMoveWillStrungle);
        return true;
    }

    private int firstPlayerPoints = 0;
    private int secondPlayerPoints = 0;

    private void countPoints() {
        int firstPlayerPoints = 0;
        int secondPlayerPoints = 0;
        for (int i = 0; i < gameMap.size(); i++) {
            for (int j = 0; j < gameMap.size(); j++) {
                SingleFiled singleFiled = gameMap.get(i).get(j);
                if (singleFiled.get_player_id() != 0) {
                    continue;
                }
                ArrayList<SingleFiled> surrandingStones = findFirstSurrandingsPlayerStones(singleFiled);

                int stonesOfPlayer1 = 0;
                int stonesOfPlayer2 = 0;
                for (SingleFiled stone : surrandingStones) {
                    if (stone.get_player_id() == 1) {
                        stonesOfPlayer1 += 1;
                    } else {
                        stonesOfPlayer2 += 1;
                    }
                }

                if (stonesOfPlayer1 == surrandingStones.size()) {
                    firstPlayerPoints += 1;
                }
                if (stonesOfPlayer2 == surrandingStones.size()){
                    secondPlayerPoints += 1;
                }
            }
        }
        firstPlayerPoints += numberOfSlavesPlayerOne;
        secondPlayerPoints += numberOfSlavesPlayerTwo;
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
    }

    private ArrayList<SingleFiled> findFirstSurrandingsPlayerStones(SingleFiled singleFiled) {
        ArrayList<SingleFiled> surrandingPlayerStones = new ArrayList<>();

        surrandingPlayerStones.add(findFirstPlayerStoneInSomeDirection(1, 0, singleFiled));
        surrandingPlayerStones.add(findFirstPlayerStoneInSomeDirection(-1,0, singleFiled));
        surrandingPlayerStones.add(findFirstPlayerStoneInSomeDirection(0,1, singleFiled));
        surrandingPlayerStones.add(findFirstPlayerStoneInSomeDirection(0,-1, singleFiled));

        surrandingPlayerStones.removeAll(Collections.singleton(null));
        return surrandingPlayerStones;
    }

    private SingleFiled findFirstPlayerStoneInSomeDirection(int addToX, int addToY, SingleFiled singleFiled) {
        int currentXposition = singleFiled.getX() + addToX;
        int currentYposition = singleFiled.getY() + addToY;

        while (currentXposition >= 0 && currentYposition >= 0 && currentXposition < gameMap.size() && currentYposition < gameMap.size()) {
            SingleFiled currentField = gameMap.get(currentXposition).get(currentYposition);
            if (currentField.get_player_id() != 0) {
                return currentField;
            }
            currentXposition += addToX;
            currentYposition += addToY;
        }
        return null;
    }

    public int whoWon() {
        countPoints();
        if (this.firstPlayerPoints > this.secondPlayerPoints) {
            return 1;
        } else {
            return 2;
        }
    }
}
