package org.py.vedge.infa.external;

import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.ShortArray;
import org.py.vedge.ii.*;
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
        circleShape.setRadius((float)polygon.isElliptic__w/2);
        return circleShape;
    }
    public static PolygonShape[] vg_gdx(Polygon ellipse, int vertexCount) { // Approximates ellipse shape
        float[] vertices = new float[vertexCount*2];
        float rx = (float)ellipse.isElliptic__w/2f;
        float ry = (float)ellipse.isElliptic__h/2f;
        for(int i = 0; i < vertexCount; i++) {
            float angle = (i/((float)vertexCount))*360f;
            vertices[i*2] = (float)(rx * Math.cos(angle));
            vertices[i*2+1] = (float)(ry * Math.sin(angle));
        }
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        ShortArray indices = triangulator.computeTriangles(vertices);
        PolygonShape[] polys = new PolygonShape[indices.size/3];
        for(int i = 0; i < indices.size; i += 3) {
            float[] triangleVertices = new float[6];
            for(int j = 0; j < 3; j++) {
                int vertexIndex = indices.get(i + j);
                triangleVertices[j*2] = vertices[vertexIndex*2];     // x
                triangleVertices[j*2+1] = vertices[vertexIndex*2+1]; // y
            }
            PolygonShape triangleShape = new PolygonShape();
            triangleShape.set(triangleVertices);
            polys[i/3] = triangleShape;
        }
        return polys;
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
    public static float txGdx(double x, double y, double w, double h, double rotation) {
        if(rotation == 0) return txGdx(x);
        else return txGdx(Meth.rotate(x+w*0.5-Math.hypot(w,h)/2,y+h*0.5,rotation-Math.toDegrees(Math.atan2(h,w)),new DoublePoint(x+w*0.5,y+h*0.5)).x());
    }
    public static float tyGdx(double y) {
        return (float) (Window.vg.size.h - y);
    }
    public static float tyGdx(double x, double y, double w, double h, double rotation) {
        if(rotation == 0) return tyGdx(y+h);
        else return tyGdx(Meth.rotate(x+w*0.5-Math.hypot(w,h)/2,y+h*0.5,rotation-Math.toDegrees(Math.atan2(h,w)),new DoublePoint(x+w*0.5,y+h*0.5)).y());
    }
    public static DoublePoint txyGdx(DoublePoint dp) {
        return new DoublePoint(txGdx(dp.x()),tyGdx(dp.y()));
    }
    public static DoublePoint txyGdx(Position position, Dimension size, double rotation) {
        return new DoublePoint(txGdx(position.x,position.y,size.w,size.h,rotation),tyGdx(position.x,position.y,size.w,size.h,rotation));
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
    public static float tangGdx(double ang) { // Degs or rads
        return (float)-ang;
    }

    public static double txVg(double x) {
        return (float) x;
    }
    public static double txVg(double x, double y, double w, double h, double vgRotation) {
        if(vgRotation == 0) return txVg(x);
        else return Meth.rotate(txVg(x)+Math.hypot(w,h)/2,tyVg(y),vgRotation-Math.toDegrees(Math.atan2(h,w)),new DoublePoint(txVg(x),tyVg(y))).x()-w/2;
    }
    public static double tyVg(double y) {
        return Window.vg.size.h - y;
    }
    public static double tyVg(double x, double y, double w, double h, double vgRotation) {
        if(vgRotation == 0) return tyVg( y+h);
        else return Meth.rotate(txVg(x)+Math.hypot(w,h)/2,tyVg(y),vgRotation-Math.toDegrees(Math.atan2(h,w)),new DoublePoint(txVg(x),tyVg(y))).y()-h/2;
    }
    public static DoublePoint txyVg(DoublePoint dp) {
        return new DoublePoint(txVg(dp.x()),tyVg(dp.y()));
    }
    public static DoublePoint txyVg(Position position, Dimension size, double vgRotation) {
        return new DoublePoint(txVg(position.x,position.y,size.w,size.h,vgRotation),tyVg(position.x,position.y,size.w,size.h,vgRotation));
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
    public static double tangVg(float ang) { // Degs or rads
        return -ang;
    }

}
