package org.py.vedge.graphics.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.GraphicsAdapter;
import org.py.vedge.ii.Dimension;
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
        ShapeRender(() -> shapeRenderer.rect((float)x,(float)(HEIGHT-y-h),(float)w,(float)h),
            () -> {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.rectLine((float)(x-strokeWeight/2),(float)(HEIGHT-y-h),(float)(x+w+strokeWeight/2),(float)(HEIGHT-y-h),(float)strokeWeight);
                shapeRenderer.rectLine((float)(x+w),(float)(HEIGHT-y-h),(float)(x+w),(float)(HEIGHT-y),(float)strokeWeight);
                shapeRenderer.rectLine((float)(x+w+strokeWeight/2),(float)(HEIGHT-y),(float)(x-strokeWeight/2),(float)(HEIGHT-y),(float)strokeWeight);
                shapeRenderer.rectLine((float)x,(float)(HEIGHT-y),(float)x,(float)(HEIGHT-y-h),(float)strokeWeight);
            });
    }

    @Override
    public void ellipse(double x, double y, double w, double h) {
        ShapeRender(() -> shapeRenderer.ellipse((float)x,(float)(HEIGHT-y-h),(float)w,(float)h));
    }

    @Override
    public void polygon(double[] allX, double[] allY) {
        if(allX.length != allY.length) {
            ErrorHandler.silent("Invalid polygon render: allX.length doesn't match allY.length");
            return;
        }
        if(allX.length == 0) return;
        float[] vertices = new float[allX.length*2];
        for(int i = 0; i < allX.length; i++) {
            vertices[i*2] = (float)allX[i];
            vertices[i*2+1] = HEIGHT-(float)allY[i];
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
    public void polygon(Polygon polygon) {
        if(polygon.isElliptic) {
            ellipse(polygon.isElliptic__x,polygon.isElliptic__y,polygon.isElliptic__w,polygon.isElliptic__h);
            return;
        }
        if(polygon.length == 0) return;
        float[] vertices = new float[polygon.length*2];
        for(int i = 0; i < polygon.length; i++) {
            vertices[i*2] = (float)polygon.get(i).x();
            vertices[i*2+1] = HEIGHT-(float)polygon.get(i).y();
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
