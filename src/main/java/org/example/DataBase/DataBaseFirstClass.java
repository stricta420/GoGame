package org.example.DataBase;

import org.example.GameLogic.GameInfo;
import org.example.GameLogic.SingleFiled;

import java.util.ArrayList;

/**
 * This class will conect to data base - basicly AFTER game is done serwer will make this class, set everything and
 *
 */
public class DataBaseFirstClass {

    /**
     * Game info - mapsize, gametype
     */
    private GameInfo gameInfo;

    /**
     * List of moves, where and witch player can be access from SingleField
     */
    private ArrayList<SingleFiled> moves;

    private String winer;

    public void setWiner(String winer) {
        this.winer = winer;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public void setMoves(ArrayList<SingleFiled> moves) {
        this.moves = moves;
    }

    /**
     * List of knocked rocks - knocedRocks.get(0) - list of all rocks knocked down in the first move
     */
    public ArrayList<ArrayList<SingleFiled>> knockedRocks;

    public void setKnockedRocks(ArrayList<ArrayList<SingleFiled>> knockedRocks) {
        this.knockedRocks = knockedRocks;
    }

    public void saveToDataBase() {
        System.out.println("Saving game to database");
    }

}
