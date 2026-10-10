package org.py.vedge.i;

import org.py.vedge.ii.Dimension;
import org.py.vedge.infa.java.Time;
import org.py.vedge.util.Observer;

import java.util.function.Supplier;

public class MathematicalEaser {

    public enum Variant {
        Sin
    }
    public enum Type {
        In,
        InOut,
        Out
    }

    private final Variant variant;
    private final Type type;
    private Dimension dims;
    public final double speed;

    private long progress = Time.nowMs();
    private double value = 0;
    private double buffer = 0;

    private final Observer<Double> observer;

    public MathematicalEaser(Variant variant, Type type, double speed, Supplier<Double> get) {
        this(variant,type,speed,get,get.get());
    }
    public MathematicalEaser(Variant variant, Type type, double speed, Supplier<Double> get, double start) {
        this.buffer = start;
        this.observer = new Observer<>(get,(p,d) -> easeTo(d)).remove();
        this.variant = variant;
        this.type = type;
        this.speed = speed;
    }

    public void easeTo(double value) {
        buffer += this.value;
        dims = new Dimension(speed,value-buffer);
        progress = Time.nowMs();
    }

    public double v() {
        return buffer+value;
    }

    public void tick() {
        if(observer != null) observer.tick();
        if(dims != null) {
            value = get(variant,type,(Time.nowMs()-progress)/dims.w*dims.w,dims);
        }
    }

    public static double get(Variant variant, Type type, double progress, Dimension dims) {
        if(dims == null) return 0;
        switch(type) {
            case In: progress /= 2;
            case Out: progress += dims.w/2; progress /= 2; break;
        }
        if(progress >= dims.w) return dims.h;
        if(progress <= 0) return 0;
        switch(variant) {
            case Sin:
                return Math.sin((Math.PI*(progress-dims.w))/(2*dims.w))*dims.h+dims.h;
        }
        return 0;
    }

}
