package org.example.ClientSerwerComunication;

public class StringTranslator {

    public boolean isGameModeGuiPlayer(String gameMode){
        return gameMode.equals("Player vs Player");
    }

    public static int getMapSize(String mapSize) {
        if (mapSize.equals("19 X 19")) {
            return 19;
        } else if (mapSize.equals("13 X 13")) {
            return 13;
        } else {
            return 9;
        }
    }
}
