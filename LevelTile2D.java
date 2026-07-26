import javafx.scene.canvas.GraphicsContext;

import java.util.function.Consumer;

public final class LevelTile2D {

    public Consumer<GraphicsContext> render;
    private Consumer<String> collision; // Future: Consumer<Player>
    private Consumer<String> tick;
    public final int key;

    public LevelTile2D(int key) {
        this.key = key;
    }

    public LevelTile2D render(Consumer<GraphicsContext> render) {
        this.render = render;
        return this;
    }
    public LevelTile2D collision(Consumer<String> collision) {
        this.collision = collision;
        return this;
    }
    public LevelTile2D tick(Consumer<String> tick) {
        this.tick = tick;
        return this;
    }


    public void runCollision() {
        collision.accept("");
    }
    public void runTick() {
        tick.accept("");
    }



}