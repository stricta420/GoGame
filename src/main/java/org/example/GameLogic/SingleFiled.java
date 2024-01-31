package org.example.GameLogic;

import java.io.Serializable;

public class SingleFiled implements Serializable {
    private int x;
    private int y;

    private int player_id = 0;


    public SingleFiled(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set_player_id(int id) {
        this.player_id = id;
    }

    public int get_player_id() {
        return player_id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
