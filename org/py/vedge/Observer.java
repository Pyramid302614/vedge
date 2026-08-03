package org.py.vedge;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Observer<T> {

    public static Sparry<Observer> observers = new Sparry<>();

    private T previousValue;
    private final Supplier<T> get;
    private final BiConsumer<T,T> onChange;
    private int index;

    private void add() {
        index = observers.length;
        observers.add(this);
    }
    private void remove() {
        observers.remove(index);
    }

    public Observer(Supplier<T> get, BiConsumer<T,T> onChange) { // BiConsumer<Old,New>
        this.get = get;
        this.onChange = onChange;
        add();
    }

    public void delete() {
        remove();
    }

    public void tick() {
        T newValue = get.get();
        if(!Objects.equals(previousValue,newValue)) {
            onChange.accept(previousValue,newValue);
            previousValue = newValue;
        }
    }
    public static void tickAll() {
        observers.forEach(Observer::tick);
    }

}
