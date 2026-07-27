package org.py.vedge;

public class Wrapper<T> {

    private T value;

    public Wrapper(T value) {
        this.value = value;
    }

    public void s(T value) {
        this.value = value;
    }
    public T g() {
        return value;
    }

    public int primInt() {
        return (int) value;
    }
    public double primDouble() {
        return (double) value;
    }
    public boolean primBoolean() {
        return (boolean) value;
    }

}
