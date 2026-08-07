package org.py.vedge;

public class Token {

    public Position2D position = new Position2D(0,0);
    public Position2D previousPosition = new Position2D(0,0);

    public Hitbox hitbox = new Hitbox(this);

    public double TCX(double x) {
        return position.x + x;
    }
    public double TCY(double y) {
        return position.y + y;
    }
    public double TRX(double x) {
        return x - position.x;
    }
    public double TRY(double y) {
        return y - position.y;
    }

    public void tokenRender(Graphics g) {}
    public void render(Graphics g) {

        tokenRender(g);

    }


    public static Sparry<Token> staticTokens = new Sparry<>();
    public static Sparry<Token> dynamicTokens = new Sparry<>();
    public static boolean doCollisionForNonMainDynamicTokens = Settings.get("vedge.collision.collide_non_main_tokens").asBoolean();

    public boolean mainCollider = false;

    public void onCollide(Token[] colliders) {}


    public void tick() {

        previousPosition = position.copy();
        position.tick();
        tokenTick();

    }
    public void tokenTick() {}

    public static void collisionAll() {


    }

}
