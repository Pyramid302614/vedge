package org.py.vedge;

import java.util.function.Consumer;

public class Map2D<T> {

    public Sparry<Sparry<T>> map = new Sparry<>();

    public Map2D() {}

    public T get(int x, int y) {
        return map.get(y).get(x);
    }
    public void set(int x, int y, T value) {
        while(map.length-1 < y) map.add(new Sparry<>());
        while(map.get(y).length-1 < x) map.get(y).add(null);
        map.get(y).set(x,value);
    }

    public void forEach(Consumer<T> consumer) {
        map.forEach(i -> i.forEach(consumer));
    }

}
