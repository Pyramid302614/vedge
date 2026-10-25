package org.py.vedge.infa.external;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import org.py.vedge.ii.Dimension;
import org.py.vedge.ii.DoublePoint;
import org.py.vedge.ii.Polygon;
import org.py.vedge.ii.Position;
import org.py.vedge.infa.java.ErrorHandler;

public class Pedro {

    public static com.badlogic.gdx.graphics.Color vg_gdx(org.py.vedge.graphics.Color vg) {
        return new com.badlogic.gdx.graphics.Color(
            vg.red()/255f,
            vg.green()/255f,
            vg.blue()/255f,
            vg.alpha()/255f
        );
    }

    public static com.badlogic.gdx.math.Vector2 vg_gdx(org.py.vedge.util.Vector2<Double,Double> vg) {
        return new com.badlogic.gdx.math.Vector2((float)((double)vg.a()),(float)((double)vg.b()));
    }
    public static com.badlogic.gdx.math.Vector2 vg_gdx(DoublePoint vg) {
        return new com.badlogic.gdx.math.Vector2((float)vg.x(),(float)vg.y());
    }

    public static DoublePoint vg_gdx(Position vg) {
        return new DoublePoint(vg.x,vg.y);
    }
    public static DoublePoint gdx_vg(float gdx_x, float gdx_y) {
        return new DoublePoint(gdx_x,gdx_y);
    }
    public static DoublePoint gdx_vg(com.badlogic.gdx.math.Vector2 gdx) {
        return gdx_vg(gdx.x,gdx.y);
    }

    public static CircleShape vg_gdx(Polygon polygon, CircleShape circleShape) { // Gives exact circle shape
        circleShape.setRadius((float)polygon.isElliptic__w);
        return circleShape;
    }
    public static PolygonShape vg_gdx(Polygon ellipse, PolygonShape polygonShape, int vertexCount) { // Approximates ellipse shape
        com.badlogic.gdx.math.Vector2[] vertices = new com.badlogic.gdx.math.Vector2[ellipse.length];
        float rx = (float)ellipse.isElliptic__w/2f;
        float ry = (float)ellipse.isElliptic__h/2f;
        for(int i = 0; i < vertexCount; i++) {
            float angle = (i/((float)vertexCount))*360f;
            vertices[i] = new com.badlogic.gdx.math.Vector2(
                (float)(rx * Math.cos(angle)),
                (float)(ry * Math.sin(angle))
            );
        }
        polygonShape.set(vertices);
        return polygonShape;
    }
    public static PolygonShape vg_gdx(Polygon polygon, PolygonShape polygonShape) { // Gives exact polygon replica
        if(polygon.length < 3 | polygon.length > 8) {
            ErrorHandler.silent("Attempt to make a polygon with invalid number of vertices for Box2D: " + polygon.length);
            return vg_gdx(Polygon.rect(0,0,50,50),polygonShape);
        }
        com.badlogic.gdx.math.Vector2[] vertices = new com.badlogic.gdx.math.Vector2[polygon.length];
        for(int i = 0; i < polygon.length; i++) {
            vertices[i] = vg_gdx(polygon.get(i));
        }
        polygonShape.set(vertices);
        return polygonShape;
    }

    public static float txGdx(double x) {
        return (float) x;
    }
    public static float txGdx(double x, Dimension size) {
        return (float) x;
    }
    public static float tyGdx(double y) {
        return (float) (Window.vg.size.h - y);
    }
    public static float tyGdx(double y, Dimension size) {
        return (float) (Window.vg.size.h - y - size.h);
    }
    public static DoublePoint txyGdx(DoublePoint dp) {
        return new DoublePoint(txGdx(dp.x()),tyGdx(dp.y()));
    }
    public static DoublePoint txyGdx(Position position, Dimension size) {
        return new DoublePoint(txGdx(position.x,size),tyGdx(position.y,size));
    }
    public static com.badlogic.gdx.math.Vector2 tdGdx(double x, double y) {
        return new com.badlogic.gdx.math.Vector2(
            (float) x,
            (float) -y
        );
    }
    public static com.badlogic.gdx.math.Vector2 tdGdx(DoublePoint direction) { // Force directions
        return tdGdx(direction.x(),direction.y());
    }

    public static float txVg(double x) {
        return (float) x;
    }
    public static float txVg(double x, Dimension size) {
        return (float) x;
    }
    public static float tyVg(double y) {
        return (float) (Window.vg.size.h - y);
    }
    public static float tyVg(double y, Dimension size) {
        return (float) (Window.vg.size.h - y - size.h);
    }
    public static DoublePoint txyVg(DoublePoint dp) {
        return new DoublePoint(txVg(dp.x()),tyVg(dp.y()));
    }
    public static DoublePoint txyVg(Position position, Dimension size) {
        return new DoublePoint(txVg(position.x,size),tyVg(position.y,size));
    }
    public static DoublePoint tdVg(float x, float y) {
        return new DoublePoint(
            x,
            -y
        );
    }
    public static DoublePoint tdVg(com.badlogic.gdx.math.Vector2 direction) { // Force directions
        return tdVg(direction.x,direction.y);
    }

}
