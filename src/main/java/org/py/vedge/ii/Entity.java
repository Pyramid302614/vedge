package org.py.vedge.ii;

public class Entity extends Token {

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

    public void entityTick() {}
    @Override
    public final void tokenTick() {
        entityTick();
    }


    public State state;

    public Entity() {
        state = State.Loaded;
    }


}
