package org.py.vedge;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Observer<T> {

    public static HashMap<Integer,Observer> observers = new HashMap<>(); // HashMap<ID,Observer>

    private T previousValue;
    private final Supplier<T> get;
    private final BiConsumer<T,T> onChange;
    public boolean changedLastTick = false;
    private int id;

    private void add() {
        id = observers.size();
        observers.put(id,this);
    }
    public Observer<T> remove() {
        observers.remove(id);
        return this;
    }

    public Observer(Supplier<T> get, BiConsumer<T,T> onChange) { // BiConsumer<Old,New>
        this.get = get;
        this.onChange = onChange;
        add();
    }

    public void tick() {
        T newValue = get.get();
        if(previousValue == null) { previousValue = newValue; return; } // Prevents first-tick firing
        if(!Objects.equals(previousValue,newValue)) {
            onChange.accept(previousValue,newValue);
            previousValue = newValue;
            changedLastTick = true;
        } else changedLastTick = false;
    }
    public static void tickAll() {
        observers.values().forEach(Observer::tick);
    }

}
