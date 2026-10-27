package org.py.vedge.graphics.external;

import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.GraphicsAdapter;
import org.py.vedge.ii.Meth;
import org.py.vedge.ii.Polygon;

public class ModifiedGraphics extends GraphicsAdapter {

    private Graphics g;

    private double xOffset = 0;
    private double yOffset = 0;
    private double xScale = 0;
    private double yScale = 0;
    private double transparency = 100;
    private double rotation = 0; // Around 0,0 , in degrees

    public ModifiedGraphics(Graphics graphics) {
        g = graphics;
    }

    public ModifiedGraphics(Graphics graphics, double xOffset, double yOffset, double xScale, double yScale, double rotation) {
        g = graphics;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xScale = xScale;
        this.yScale = yScale;
        this.rotation = rotation;
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

    private double tx(double x,double y) {
        if(rotation == 0 || (x == y && x == 0)) return Math.round(x*xScale+xOffset);
        return Math.round(Meth.rotate(x,y,rotation).x()*xScale+xOffset);
    } // Translate X
    private double ty(double x, double y) {
        if(rotation == 0 || (x == y && x == 0)) return Math.round(y*yScale+yOffset);
        return Math.round(Meth.rotate(x,y,rotation).y()*yScale+yOffset);
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
    private double tang(double a) {
        return a+rotation;
    }

    private double rw(double w) {
        return Math.round(w/xScale);
    } // Reverse Translate Width
    private double rh(double h) {
        return Math.round(h/yScale);
    } // Reverse Translate Height
    private double rd(double d) {
        return Math.round(d/((xScale+yScale)/2));
    } // Reverse Translate Dimension
    private double rang(double a) {
        return a-rotation;
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
    public void rect(double x, double y, double w, double h, double angle) {
        g.rect(tx(x,y),ty(x,y),tw(w),th(h),tang(angle));
    }

    // Used in pigeon X but ended up in Vedge Graphics class because it was copied over initially
    public void rect(double x, double y, double w, double h, double ntx, double nty, double ntw, double nth, double angle) {
        g.rect(tx(x,y)+ntx,ty(x,y)+nty,tw(w)+ntw,th(h)+nth,tang(angle));
    }

    public void ellipse(double x, double y, double w, double h, double angle) {
        g.ellipse(tx(x,y),ty(x,y),tw(w),th(h),tang(angle));
    }

    public void ellipse(double x, double y, double w, double h, double ntx, double nty, double ntw, double nth, double angle) {
        g.ellipse(tx(x,y)+ntx,ty(x,y)+nty,tw(w)+ntw,th(h)+nth,tang(angle));
    }

    @Override
    public void polygon(double[] allX, double[] allY) {
        double[] translatedX = new double[allX.length];
        double[] translatedY = new double[allY.length];
        for(int i = 0; i < translatedX.length; i++) {
            translatedX[i] = tx(allX[i],allY[i]);
            translatedY[i] = tx(allX[i],allY[i]);
        }
        g.polygon(translatedX,translatedY);
    }
    @Override
    public void polygon(Polygon polygon) {
        double[] translatedX = new double[polygon.length];
        double[] translatedY = new double[polygon.length];
        for(int i = 0; i < translatedX.length; i++) {
            translatedX[i] = tx(polygon.get(i).x(),polygon.get(i).y());
            translatedY[i] = tx(polygon.get(i).x(),polygon.get(i).y());
        }
        g.polygon(translatedX,translatedY);
    }

    @Override
    public void text(String text, double x, double y) {
        g.text(text,tx(x,y),ty(x,y));
    }
    public void text(String text, double x, double y, double ntx, double nty) {
        g.text(text,tx(x,y)+ntx,ty(x,y)+nty);
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
