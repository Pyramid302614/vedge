package org.py.vedge.graphics;

import javafx.scene.text.Font;

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
    } // Translate X
    private double ty(double y) {
        return y*yScale+yOffset;
    } // Translate Y
    private double tw(double w) {
        return w*xScale;
    } // Translate Width
    private double th(double h) {
        return h*yScale;
    } // Translate Height
    private double td(double d) {
        return d*((xScale+yScale)/2);
    } // Translate Dimension
    private double rx(double x) {
        return (x-xOffset)/xScale;
    }
    private double ry(double y) {
        return (y-yOffset)/yScale;
    }
    private double rw(double w) {
        return w/xScale;
    }
    private double rh(double h) {
        return h/yScale;
    }
    private double rd(double d) {
        return d/((xScale+yScale)/2);
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
    public void rect(double x, double y, double w, double h, double ntx, double nty, double ntw, double nth) {
        super.rect(tx(x)+ntx,ty(y)+nty,tw(w)+ntw,th(h)+nth);
    }
    @Override
    public void ellipse(double x, double y, double w, double h) {
        super.ellipse(tx(x),ty(y),tw(w),th(h));
    }
    public void ellipse(double x, double y, double w, double h, double ntx, double nty, double ntw, double nth) {
        super.ellipse(tx(x)+ntx,ty(y)+nty,tw(w)+ntw,th(h)+nth);
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

    @Override
    public void text(String text, double x, double y) {
        super.text(text,tx(x),ty(y));
    }
    public void text(String text, double x, double y, double ntx, double nty) {
        super.text(text,tx(x)+ntx,ty(y)+nty);
    }

    @Override
    public void updateFont() {
        gc.setFont(Font.font(textFamily,textPosture,td(textSize)));
    }

    public double textWidth(String s) {
        return rw(super.textWidth(s));
    }
    public double textAscent() {
        return rh(super.textAscent());
    }
    public double textHeight() {
        return rh(super.textHeight());
    }

}
