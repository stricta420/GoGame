package org.example.GameLogic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    @Test
    void createMapTesting() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createMap(19, 19);
        assertEquals(19, gameBoard.getGameMap().size());
        assertEquals(19, gameBoard.getGameMap().get(0).size());
        assertEquals(0, gameBoard.getGameMap().get(18).get(15).get_player_id());
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 15; j++) {
                assertEquals(0, gameBoard.getGameMap().get(i).get(j).get_player_id());
            }
        }
    }

    @Test
    void testAddStone() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createMap(19, 19);
        gameBoard.addStone(7, 2, 2);
        assertEquals(2, gameBoard.getGameMap().get(7).get(2).get_player_id());
        gameBoard.addStone(7, 2, 1);
        assertEquals(1, gameBoard.getGameMap().get(7).get(2).get_player_id());
    }


    @Test
    void testGetSurrandings() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createMap(13,13);
        gameBoard.addStone(0,0,2);
        gameBoard.addStone(0,1,1);
        gameBoard.addStone(1,0, 2);
        gameBoard.addStone(1,1,1);
        gameBoard.addStone(2,0,1);
        gameBoard.addStone(0,2,1);
        ArrayList<SingleFiled> testArrayOfSurrandings = gameBoard.getSurroundings(0,0);
        SingleFiled singleFiled1 = new SingleFiled(0,1);
        SingleFiled singleFiled2 = new SingleFiled(1,0);
        singleFiled1.set_player_id(1);
        singleFiled2.set_player_id(2);
        assertEquals(2, testArrayOfSurrandings.size());
        assertEquals(2, testArrayOfSurrandings.get(0).get_player_id());
        assertEquals(1, testArrayOfSurrandings.get(1).get_player_id());
    }

    @Test
    void testGetSurrandingsMid() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createMap(13,13);
        gameBoard.addStone(6,3,2);
        gameBoard.addStone(6,2,1);
        gameBoard.addStone(7,3, 2);
        gameBoard.addStone(3,5,1);
        gameBoard.addStone(2,0,1);
        gameBoard.addStone(0,2,1);
        for (SingleFiled singleFiled : gameBoard.getSurroundings(6,3)) {
            System.out.println("start");
            System.out.println(singleFiled.getX());
            System.out.println(singleFiled.getY());
        }
        assertEquals(4, gameBoard.getSurroundings(6,3).size());
    }

    @Test
    //well, it dosnt work, no wonder thb
    void findNumberOfBreathsOfWholeStructure() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createMap(13,13);
        gameBoard.addStone(0,0,1);
        gameBoard.addStone(1,0,1);
        gameBoard.addStone(1,1,1);
        gameBoard.addStone(12, 11, 2);
        gameBoard.addStone(11, 12, 2);
        gameBoard.setPlayerid(1);
        assertEquals(4, gameBoard.getNumberOfBreaths(0,0, 1));
        assertEquals(4, gameBoard.getNumberOfBreaths(1,0, 1));
        assertEquals(4, gameBoard.getNumberOfBreaths(1,1,1 ));
        assertEquals(4, gameBoard.getNumberOfBreaths(10, 10,1 ));
        assertEquals(0, gameBoard.getNumberOfBreaths(12,12,1));
        assertEquals(3 ,gameBoard.getNumberOfBreaths(12,12,2));
    }

    @Test
    void TestGetConnectedStructure() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createMap(13,13);
        gameBoard.addStone(0,0,1);
        gameBoard.addStone(1,1,1);
        gameBoard.addStone(0,1,1);
        gameBoard.addStone(1,0,1);
        gameBoard.addStone(2,0,1);
        gameBoard.addStone(2,1,2);
        gameBoard.addStone(2,2,2);
        gameBoard.addStone(5,2,1);
        gameBoard.addStone(0,10,1);
        assertEquals(5, gameBoard.getConnectedStructure(0,0).size());
        ArrayList<SingleFiled> connectedStructure = gameBoard.getConnectedStructure(1,1);
        ArrayList<SingleFiled> testingStructure = new ArrayList<>();
        testingStructure.add(new SingleFiled(0,0));
        testingStructure.add(new SingleFiled(1,0));
        testingStructure.add(new SingleFiled(0,1));
        testingStructure.add(new SingleFiled(1,1));
        testingStructure.add(new SingleFiled(2,0));
        for (SingleFiled field : connectedStructure) {
            int counter = 0;
            for (SingleFiled testField : testingStructure) {
                testField.set_player_id(1);
                if (field.getX() == testField.getX() && field.getY() == testField.getY()) {
                    counter += 1;
                }
            }
            assertEquals(1, counter);
            counter = 0;
        }
    }


    @Test
    void TestfindRocksThatMoveWillStrungle() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createMap(9, 9);
        gameBoard.addStone(0,0, 1);
        gameBoard.addStone(1,0, 1);
        gameBoard.addStone(0,1, 1);
        gameBoard.addStone(0,2, 2);
        gameBoard.addStone(1,1, 2);
        gameBoard.setPlayerid(2);
        gameBoard.findRocksThatMoveWillStrungle(2,0);
        assertEquals(3, gameBoard.findRocksThatMoveWillStrungle(2,0).size());
        gameBoard.addStone(0,0,2);
        assertEquals(1, gameBoard.findRocksThatMoveWillStrungle(2,0).size());

    }
}