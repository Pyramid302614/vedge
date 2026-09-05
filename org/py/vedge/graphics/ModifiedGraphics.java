package org.py.vedge.graphics;

public class ModifiedGraphics extends Graphics {

    private double xOffset = 0;
    private double yOffset = 0;
    private double xScale = 0;
    private double yScale = 0;
    private double transparency = 100;

    public ModifiedGraphics(Graphics graphics) {
        super(graphics.gc);
    }

    public ModifiedGraphics(Graphics graphics, double xOffset, double yOffset, double xScale, double yScale) {
        super(graphics.gc);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public ModifiedGraphics setOffset(double x, double y) {
        xOffset = x;
        yOffset = y;
        return this;
    }

    public ModifiedGraphics setScale(double x, double y) {
        xScale = x;
        yScale = y;
        return this;
    }

    public ModifiedGraphics setTransparency(double t) {
        transparency = t;
        fill(fill);
        stroke(stroke);
        return this;
    }

    private double tx(double x) {
        return x*xScale+xOffset;
    }
    private double ty(double y) {
        return y*yScale+yOffset;
    }
    private double tw(double w) {
        return w*xScale;
    }
    private double th(double h) {
        return h*yScale;
    }

    @Override
    public void fill(Color color) {
        super.fill(new Color(color.red(),color.green(),color.blue(),(int)(color.alpha()*transparency/100)));
    }
    @Override
    public void stroke(Color color) {
        super.stroke(new Color(color.red(),color.green(),color.blue(),(int)(color.alpha()*transparency/100)));
    }
    @Override
    public void rect(double x, double y, double w, double h) {
        super.rect(tx(x),ty(y),tw(w),th(h));
    }
    @Override
    public void ellipse(double x, double y, double w, double h) {
        super.ellipse(tx(x),ty(y),tw(w),th(h));
    }
    @Override
    public void polygon(double[] allX, double[] allY) {
        double[] translatedX = new double[allX.length];
        double[] translatedY = new double[allY.length];
        for(int i = 0; i < translatedX.length; i++) {
            translatedX[i] = tx(allX[i]);
            translatedY[i] = tx(allY[i]);
        }
        super.polygon(translatedX,translatedY);
    }

}
