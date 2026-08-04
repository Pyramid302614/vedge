package org.py.vedge;

import java.util.function.Consumer;

public class Token {

    public Position2D position = new Position2D(0,0);
    public Polygon2D hitbox;

    public void render(Graphics g) {}
    public void collision(Token[] tokens) {}
    public void tick() {}

    public static Consumer<Token[]> COLLISION_SOLID = g -> {

    };

}
