package org.py.vedge;

public class IterativeEaser {

    private static final Sparry<IterativeEaser> easers = new Sparry<>();

    private double current;
    private double target;

    private double easing; // 0-1

    public IterativeEaser(double value, double easing) {
        this.easing = easing;
        c(value); t(value);
        easers.add(this);
    }
    public IterativeEaser(double value, double easing, boolean manual) {
        this.easing = easing;
        c(value); t(value);
        if(!manual) easers.add(this); // Why would you do this
    }
    public void delete() {
        easers.remove(this);
    }


    public double c() {
        return current;
    }
    public double t() {
        return target;
    }
    public void c(double value) {
        current = value;
    }
    public void t(double value) {
        target = value;
    }

    public double ct(double target) {
        if(this.target != target) this.target = target;
        return current;
    }


    public void tick() {
        current += (target - current) * easing;
    }

    public static void tickAll() {
        easers.forEach(IterativeEaser::tick);
    }

}
