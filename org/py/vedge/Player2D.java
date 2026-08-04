package org.py.vedge;

public class Player2D extends Entity2D {

    public static Player2D me;

    public Player2D setAsMe() {
        me = this;
        return this;
    }
    public static void setAsMe(Player2D me) {
        Player2D.me = me;
    }



}
