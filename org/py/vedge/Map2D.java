package org.py.vedge;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Map2D<T> {

    public Sparry<Sparry<T>> map = new Sparry<>();

    public Map2D() {}

    public T get(int x, int y) {
        if(map.get(y) == null) return null;
        return map.get(y).get(x);
    }
    public void set(int x, int y, T value) {
        while(map.length-1 < y) map.add(new Sparry<>());
        while(map.get(y).length-1 < x) map.get(y).add(null);
        map.get(y).set(x,value);
    }

    public void forEach(Consumer<T> consumer) {
        map.forEach(i -> { if(i != null) i.forEach(consumer); });
    }
    public void forEach(BiConsumer<T,Point> consumer) {
        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map.get(y).length; x++) {
                consumer.accept(get(x,y),new Point(x,y));
            }
        }
    }

    public Sparry<T> flatten() {
        Sparry<T> result = new Sparry<>();
        map.forEach(i -> i.forEach(result::add));
        return result;
    }

}
