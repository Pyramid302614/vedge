package org.py.vedge;

import java.util.HashMap;

public class Entity2D extends Token {


    // Cache and all that

    public static HashMap<Integer,Entity2D> entities = new HashMap<>(); // HashMap<ID,Entity>

    public enum State {
        Stained, // Loaded forever
        Loaded, // Loaded, ticked, all that good stuff
        Offloaded, // Not loaded, but still in cache
        Unloaded, // Marked for removal from cache
        Deleted, // Completely gone, prevented from being loaded ever again
    }
    public boolean loaded() {
        return switch(state) {
            case Stained, Loaded -> true;
            default -> false;
        };
    }
    public void remove() {
        entities.remove(id);
    }



    // Rendering

    public static void renderAll() {

        Graphics g = Window.graphics;
        if(g == null) return;

        entities.values().forEach(i -> {
            if(i.loaded()) i.render(g);
        });

        if(Debug.RENDER_TOKEN_HITBOXES) {
            g.noFill();
            g.stroke(Debug.RENDER_TOKEN_HITBOXES_Color);
            g.strokeWeight(2);
            entities.values().forEach(e -> {
                double[] X = new double[e.hitbox.length];
                double[] Y = new double[e.hitbox.length];
                for(int i = 0; i < e.hitbox.length; i++) {
                    X[i] = e.TCX(e.hitbox.get(i).x());
                    Y[i] = e.TCY(e.hitbox.get(i).y());
                }
                g.polygon(X,Y);
            });
        }

    }




    // Ticking

    public static void tickAll() {
        entities.forEach((id,entity) -> {
            switch(entity.state) {
                case Loaded, Stained:
                    entity.tick(); break;
                case Offloaded, Deleted:
                    break;
                case Unloaded:
                    entities.remove(id); break;
            }
            entity.tick();
        });
    }
    public void entityTick() {}
    @Override
    public void tokenTick() {

    }




    // Properties and stuff

    public final int id;
    public State state;

    public Entity2D() {
        id = entities.size();
        state = State.Loaded;
        entities.put(id,this);
        dynamicTokens.add(this);
    }





}
