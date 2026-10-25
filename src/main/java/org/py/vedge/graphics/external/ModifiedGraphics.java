package org.py.vedge.graphics.external;

import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.GraphicsAdapter;
import org.py.vedge.ii.Polygon;

public class ModifiedGraphics extends GraphicsAdapter {

    private Graphics g;

    private double xOffset = 0;
    private double yOffset = 0;
    private double xScale = 0;
    private double yScale = 0;
    private double transparency = 100;

    public ModifiedGraphics(Graphics graphics) {
        g = graphics;
    }

    public ModifiedGraphics(Graphics graphics, double xOffset, double yOffset, double xScale, double yScale) {
        g = graphics;
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

    // 0 - 100
    public ModifiedGraphics setTransparency(double t) {
        transparency = t;
        g.fill(g.fill);
        g.stroke(g.stroke);
        return this;
    }

    private double tx(double x) {
        return Math.round(x*xScale+xOffset);
    } // Translate X
    private double ty(double y) {
        return Math.round(y*yScale+yOffset);
    } // Translate Y
    private double tw(double w) {
        return Math.round(w*xScale);
    } // Translate Width
    private double th(double h) {
        return Math.round(h*yScale);
    } // Translate Height
    private double td(double d) {
        return Math.round(d*((xScale+yScale)/2));
    } // Translate Dimension
    private double rx(double x) {
        return Math.round((x-xOffset)/xScale);
    }
    private double ry(double y) {
        return Math.round((y-yOffset)/yScale);
    }
    private double rw(double w) {
        return Math.round(w/xScale);
    }
    private double rh(double h) {
        return Math.round(h/yScale);
    }
    private double rd(double d) {
        return Math.round(d/((xScale+yScale)/2));
    }

    @Override
    public void fill(Color color) {
        g.fill(new Color(color.red(),color.green(),color.blue(),(int)(color.alpha()*transparency/100)));
    }
    @Override
    public void stroke(Color color) {
        g.stroke(new Color(color.red(),color.green(),color.blue(),(int)(color.alpha()*transparency/100)));
    }

    @Override
    public void rect(double x, double y, double w, double h) {
        g.rect(tx(x),ty(y),tw(w),th(h));
    }
    public void rect(double x, double y, double w, double h, double ntx, double nty, double ntw, double nth) {
        g.rect(tx(x)+ntx,ty(y)+nty,tw(w)+ntw,th(h)+nth);
    }
    @Override
    public void ellipse(double x, double y, double w, double h) {
        g.ellipse(tx(x),ty(y),tw(w),th(h));
    }
    public void ellipse(double x, double y, double w, double h, double ntx, double nty, double ntw, double nth) {
        g.ellipse(tx(x)+ntx,ty(y)+nty,tw(w)+ntw,th(h)+nth);
    }
    @Override
    public void polygon(double[] allX, double[] allY) {
        double[] translatedX = new double[allX.length];
        double[] translatedY = new double[allY.length];
        for(int i = 0; i < translatedX.length; i++) {
            translatedX[i] = tx(allX[i]);
            translatedY[i] = tx(allY[i]);
        }
        g.polygon(translatedX,translatedY);
    }
    @Override
    public void polygon(Polygon polygon) {
        double[] translatedX = new double[polygon.length];
        double[] translatedY = new double[polygon.length];
        for(int i = 0; i < translatedX.length; i++) {
            translatedX[i] = tx(polygon.get(i).x());
            translatedY[i] = tx(polygon.get(i).y());
        }
        g.polygon(translatedX,translatedY);
    }

    @Override
    public void text(String text, double x, double y) {
        g.text(text,tx(x),ty(y));
    }
    public void text(String text, double x, double y, double ntx, double nty) {
        g.text(text,tx(x)+ntx,ty(y)+nty);
    }

    @Override
    public void textSize(int size) {
        g.textSize((int)Math.round(td(size)));
    }
    @Override
    public void textColor(Color color) {
        g.textColor(new Color(color.red(),color.green(),color.blue(),(int)(color.alpha()*transparency/100)));
    }
    @Override
    public double textWidth(String s) {
        return rw(g.textWidth(s));
    }
    @Override
    public double textAscent() {
        return rh(g.textAscent());
    }
    @Override
    public double textHeight() {
        return rh(g.textHeight());
    }


    public void setFont(Font font) {
        g.setFont(font);
    }


    @Override
    public String toString() {
        return "ModifiedGraphics : x" + xOffset + " : y" + yOffset + " : w" + xScale + " : h" + yScale;
    }

}
