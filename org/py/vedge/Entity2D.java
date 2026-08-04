package org.py.vedge;

import java.util.HashMap;
import java.util.function.Consumer;

public class Entity2D extends Token {

    public enum State {
        Loaded, // Loaded, ticked, all that good stuff
        Offloaded, // Not loaded, but still in cache
        Unloaded, // Marked for removal from cache
        Deleted, // Completely gone, prevented from being loaded ever again
    }

    public static HashMap<Integer,Entity2D> entities = new HashMap<>(); // HashMap<ID,Entity>

    public static void tickAll() {
        entities.forEach((id,entity) -> {
            switch(entity.state) {
                case Loaded:
                    entity.tick(); break;
                case Offloaded, Deleted:
                    break;
                case Unloaded:
                    entities.remove(id); break;
            }
            entity.tick();
        });
    }
    public static void renderAll() {
        Graphics g = Window.graphics;
        if(g == null) return;
        entities.values().forEach(i -> {
            if(i.state == State.Loaded) i.render(g);
        });
    }
    public static void collisionAll() {
        entities.values().forEach(i -> {
            if(i.state == State.Loaded) i.processCollision();
        });
    }

    public final int id;
    public State state;
    private Consumer<Token[]> entityCollision;

    public Observer<Position2D> positionObserver = new Observer<>(() -> position, (o,n) -> {}).remove(); // Manual ticking

    public Entity2D() {
        id = entities.size();
        state = State.Loaded;
        entities.put(id,this);
    }

    public void remove() {
        entities.remove(id);
    }



    private void processCollision() {

        // Adds all of them for now
        Sparry<Token> collisionGroup = new Sparry<>();
        if(LevelMap2D.current != null) collisionGroup.addAll(LevelMap2D.current.flatten());
        entities.forEach((id,entity) -> { if(!id.equals(this.id)) collisionGroup.add(entity); });
        collision(collisionGroup.toArray());

    }

    public void setEntityCollision(Consumer<Token[]> entityCollision) {
        this.entityCollision = entityCollision;
    }

    @Override
    public void collision(Token[] collisionGroup) {

        positionObserver.tick();
        if(positionObserver.changedLastTick && entityCollision != null) entityCollision.accept(collisionGroup);

    }





}
