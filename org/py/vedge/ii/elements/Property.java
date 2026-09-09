package org.py.vedge.ii.elements;

import org.py.vedge.graphics.Color;
import org.py.vedge.i.Easer;
import org.py.vedge.util.Sparry;

public class Property {

    public enum PropertyType {
        Static,
        Easable
    }
    public enum DataType {
        Color,
        Number,
        Alignment
    }

    public enum P_Alignment {
        Left,
        Right,
        Top,
        Bottom,
        Center,
        TopCenter,
        BottomCenter,
        LeftCenter,
        RightCenter,
        None
    }

    private PropertyType pt;
    private DataType dt;
    private Color color;
    private double number;
    private P_Alignment alignment;

    private final Sparry<Easer> easers = new Sparry<>();

    public void set(Color color) {
        dt = DataType.Color;
        this.color = color;
    }
    public void set(double number) {
        dt = DataType.Number;
        this.number = number;
    }
    public void set(P_Alignment alignment) {
        dt = DataType.Alignment;
        this.alignment = alignment;
    }
    public void set(Property property) {
        switch(property.dt) {
            case Number -> set(property.asNumber());
            case Color -> set(property.asColor());
            case Alignment -> set(property.asAlignment());
        }
    }

    public Property(Color color) {
        set(color);
    }
    public Property(double number) {
        set(number);
    }
    public Property(P_Alignment alignment) {
        set(alignment);
    }
    public Property(Property property) {
        set(property);
    }


    public void tick() {
        easers.forEach(Easer::tick);
    }

    public Color asColor() {
        if(easers.length == 4) return new Color((int)easers.get(0).v(),(int)easers.get(1).v(),(int)easers.get(2).v(),(int)easers.get(3).v());
        else return color;
    }
    public double asNumber() {
        if(easers.length == 1) return easers.get(0).v();
        else return number;
    }
    public P_Alignment asAlignment() {
        return alignment;
    }

    public Property ease(Easer.Variant variant, Easer.Type type, double speedMS) {
        switch(dt) {
            case Number -> { easers.clear(); easers.add(new Easer(variant,type,speedMS,() -> number)); }
            case Color -> { easers.clear(); easers.addAll(new Easer(variant,type,speedMS,() -> (double)color.red()),new Easer(variant,type,speedMS,() -> (double)color.green()),new Easer(variant,type,speedMS,() -> (double)color.blue()),new Easer(variant,type,speedMS,() -> (double)color.blue()),new Easer(variant,type,speedMS,() -> (double)color.alpha())); }
            default -> { easers.clear(); }
        }
        return this;
    }

    @Override
    public String toString() {
        return (switch(dt) {
            case Number -> number;
            case Color -> color;
            case Alignment -> alignment;
        }).toString();
    }

}
