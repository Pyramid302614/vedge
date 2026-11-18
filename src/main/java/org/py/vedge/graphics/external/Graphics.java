package org.py.vedge.graphics.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.GraphicsAdapter;
import org.py.vedge.ii.Dimension;
import org.py.vedge.ii.DoublePoint;
import org.py.vedge.ii.Meth;
import org.py.vedge.ii.Polygon;
import org.py.vedge.infa.external.Pedro;
import org.py.vedge.infa.java.ErrorHandler;

public class Graphics extends GraphicsAdapter {

    public Graphics() {
        create();
    }

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    public OrthographicCamera camera;
    private FitViewport viewport;

    private final Font font = Font.ARIAL();

    public final float WIDTH = Gdx.graphics.getWidth();
    public final float HEIGHT = Gdx.graphics.getHeight();

    // Graphics() calls this
    private void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.position.set(WIDTH/2f,HEIGHT/2f,0f);
        camera.update();
        viewport = new FitViewport(WIDTH,HEIGHT,camera);
    }
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
    public void resize(int w, int h) {
        viewport.update(w,h);
    }

    @Override
    public void rect(double x, double y, double w, double h) {
        rect(x,y,w,h,0);
    }
    @Override
    public void rect(double x, double y, double w, double h, double angle) {
        ShapeRender(() -> shapeRenderer.rect(Pedro.txGdx(x,y,w,h,angle),Pedro.tyGdx(x,y,w,h,angle),0,0,(float)w,(float)h,1,1,Pedro.tangGdx(angle)),
            () -> {
                // tl, tr, br, bl
                DoublePoint origin = new DoublePoint(x+w/2,y+h/2);
                DoublePoint tl = Meth.rotate(x,y,angle,origin);
                DoublePoint tr = Meth.rotate(x+w,y,angle,origin);
                DoublePoint br = Meth.rotate(x+w,y+h,angle,origin);
                DoublePoint bl = Meth.rotate(x,y+h,angle,origin);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.rectLine(Pedro.txGdx(tl.x()-strokeWeight/2),Pedro.tyGdx(tl.y()),Pedro.txGdx(tr.x()+strokeWeight/2),Pedro.tyGdx(tr.y()),(float)strokeWeight);
                shapeRenderer.rectLine(Pedro.txGdx(tr.x()),Pedro.tyGdx(tr.y()),Pedro.txGdx(br.x()),Pedro.tyGdx(br.y()),(float)strokeWeight);
                shapeRenderer.rectLine(Pedro.txGdx(br.x()+strokeWeight/2),Pedro.tyGdx(br.y()),Pedro.txGdx(bl.x()-strokeWeight/2),Pedro.tyGdx(bl.y()),(float)strokeWeight);
                shapeRenderer.rectLine(Pedro.txGdx(bl.x()),Pedro.tyGdx(bl.y()),Pedro.txGdx(tl.x()),Pedro.tyGdx(tl.y()),(float)strokeWeight);
            });
    }

    @Override
    public void ellipse(double x, double y, double w, double h, double angle) {
        ShapeRender(() -> shapeRenderer.ellipse(Pedro.txGdx(x,y,w,h,0),Pedro.tyGdx(x,y,w,h,0),(float)w,(float)h,Pedro.tangGdx(angle)));
    }

    @Override
    public void polygon(double[] allX, double[] allY, double angle) {
        if(allX.length != allY.length) {
            ErrorHandler.silent("Invalid polygon render: allX.length doesn't match allY.length");
            return;
        }
        if(allX.length == 0) return;
        DoublePoint origin = new Polygon(allX,allY).center(); // Origin of rotation
        float[] vertices = new float[allX.length*2];
        for(int i = 0; i < allX.length; i++) {
            DoublePoint v = Meth.rotate(new DoublePoint(allX[i],allY[i]),Math.toRadians(angle),origin);
            vertices[i*2] = Pedro.txGdx(v.x());
            vertices[i*2+1] = Pedro.tyGdx(v.y());
        }
        ShapeRender(() -> shapeRenderer.polygon(vertices),
            () -> {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                for(int i = 0; i < allX.length; i++) {
                    shapeRenderer.rectLine(
                        vertices[i*2],
                        vertices[i*2+1],
                        vertices[(i*2+2)%vertices.length],
                        vertices[(i*2+3)%vertices.length],
                        (float)strokeWeight
                    );
                }
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//                shapeRenderer.polyline(vertices);
            });
    }
    @Override
    public void polygon(Polygon polygon, double angle) {
        if(polygon.isElliptic) {
            ellipse(polygon.isElliptic__x,polygon.isElliptic__y,polygon.isElliptic__w,polygon.isElliptic__h);
            return;
        }
        if(polygon.length == 0) return;
        DoublePoint origin = polygon.center(); // Origin of rotation
        float[] vertices = new float[polygon.length*2];
        for(int i = 0; i < polygon.length; i++) {
            DoublePoint v = Meth.rotate(new DoublePoint(polygon.get(i).x(),polygon.get(i).y()),Math.toRadians(angle),origin);
            vertices[i*2] = Pedro.txGdx(v.x());
            vertices[i*2+1] = Pedro.tyGdx(v.y());
        }
        ShapeRender(() -> shapeRenderer.polygon(vertices),
            () -> {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                for(int i = 0; i < polygon.length; i++) {
                    shapeRenderer.rectLine(
                        vertices[i*2],
                        vertices[i*2+1],
                        vertices[(i*2+2)%vertices.length],
                        vertices[(i*2+3)%vertices.length],
                        (float)strokeWeight
                    );
                }
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//                shapeRenderer.polyline(vertices);
            });
    }

    public void setFont(Font font) {
        this.font.conform(font);
    }

    @Override
    public void textSize(int s) {
        this.font.setSize(s);
    }
    @Override
    public void textColor(Color color) {
        this.font.setColor(color);
    }

    @Override
    public void text(String text, double x, double y) {

        font.getRaster().getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        SpriteRender(() -> {
            font.getRaster().setColor(Pedro.vg_gdx(font.getColor()));
            font.getRaster().draw(spriteBatch,text,(float)x,(float)(HEIGHT-y+font.getRaster().getAscent()));
        });

    }

    public Dimension stringBounds(String string) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font.getRaster(),string);
        return new Dimension(
            glyphLayout.width,
            glyphLayout.height
        );
    }

    @Override
    public double textWidth(String s) {
        return stringBounds(s).w;
    }

    @Override
    public double textAscent() {
        return font.getRaster().getAscent();
    }

    @Override
    public double textHeight() {
        return font.getRaster().getCapHeight();
    }

    private void ShapeRender(Runnable action) {
        ShapeRender(action,null);
    }
    private void ShapeRender(Runnable action, Runnable separateStrokeAction) {

        if(!fill.blank()) {
            if(!fill.opaque()) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Pedro.vg_gdx(fill));
            action.run();
            shapeRenderer.end();
        }
        if(!stroke.blank()) {
            if(!fill.opaque()) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
            shapeRenderer.setColor(Pedro.vg_gdx(stroke));
            if(separateStrokeAction != null)
                separateStrokeAction.run();
            else {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                Gdx.gl.glLineWidth((float)strokeWeight);
                action.run();
            }
            shapeRenderer.end();
        }

    }
    private void SpriteRender(Runnable action) {
        spriteBatch.begin();
        action.run();
        spriteBatch.end();
    }

}
