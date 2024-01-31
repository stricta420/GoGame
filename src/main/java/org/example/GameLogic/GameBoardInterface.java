package org.example.GameLogic;

import java.util.ArrayList;

public interface GameBoardInterface {
    ArrayList<SingleFiled> getKnockedRocks();
    void setPlayerid(int playerid);

    void createMap(int height, int width);

    ArrayList<ArrayList<SingleFiled>> getGameMap();

    ArrayList<SingleFiled> getSurroundings(int x, int y);

    void addStone(int x, int y, int playerid);

    boolean tryToMakeMove(int x, int y, int playerId);

    int whoWon();
}
