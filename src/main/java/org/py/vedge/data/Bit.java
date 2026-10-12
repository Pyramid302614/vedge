package org.py.vedge.data;

public record Bit(boolean v) {
    public static Bit I = new Bit(true);
    public static Bit O = new Bit(false);
    public static Bit make(boolean v) {
        return v ? Bit.I : Bit.O;
    }
}