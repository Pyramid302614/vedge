package org.py.vedge.physics.external;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.py.vedge.Vedge;
import org.py.vedge.data.JSONObject;
import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.ii.DoublePoint;
import org.py.vedge.ii.LevelMap;
import org.py.vedge.ii.Token;
import org.py.vedge.infa.Debug;
import org.py.vedge.infa.Settings;
import org.py.vedge.infa.external.Pedro;
import org.py.vedge.infa.external.Window;
import org.py.vedge.physics.PhysicsAdapter;

public class Physics extends PhysicsAdapter {

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private double stepSpeed = 4;

    static {
        // Only ever apply scale onto VG stuff after it's been converted from GDX
        // THESE TWO NUMBERS SHOULD ALWAYS BE THE SAME
//        scale = new DoublePoint(0.000265,0.000265); // (SCALE OF B2D) Used to accurately represent meters, so Box2D doesn't treat objects as huge masses
        scale = new DoublePoint(0.02,0.02);
        // Uses 3,779.53 meters -> 1 pixel
    }

    public Physics(JSONObject config, PhysicsObject... objects) {
        super(config, objects);
    }
    public Physics(PhysicsObject... objects) {
        super(objects);
    }

    @Override
    public void init_override() { // Like libgdx "create()"
        world = new World(Pedro.tdGdx(config("gravity_x").asDouble(),config("gravity_y").asDouble()),Settings.get("vedge.gdx.box2d.sleep_objects").asBoolean());
        objects.forEach(i -> i.world = world);
        if(Debug.BOX2D_DEBUG_RENDERER) {
            debugRenderer = new Box2DDebugRenderer();
        }
    }

    @Override
    public void dispose() {
        if(world != null) world.dispose();
    }

    @Override
    public void tick() {
        objects.forEach(PhysicsObject::sync);
    }

    @Override
    public void render(Graphics g) {
        debugRender(g.camera);
    }

    @Override
    public void postTick() {
        // Because this method is called every frame, we want to reference FPS (FRAMES per second) rather than tick rate,
        // because tick rate is only a number used to calculate how slow everything should be, rather than how much times
        // things are actually ticked.
        world.step((float)(((1.0/Vedge.targetFps)*Vedge.tickSpeed)*stepSpeed),6,2);
    }

    public void debugRender(OrthographicCamera camera) {
        if(debugRenderer != null) {
            final float original_zoom = camera.zoom;
            final Vector3 original_pos = camera.position.cpy();
            camera.zoom = (float)(scale.x());
            Vector2 gdxCamPos = Pedro.vg_gdx(Pedro.txyGdx(new DoublePoint(Window.vg.graphics.WIDTH/2*scale.x(),Window.vg.graphics.HEIGHT/2*scale.x())));
            camera.position.set(gdxCamPos.x,gdxCamPos.y,camera.position.z); // Uses x() on both intentionally
            camera.update();
            debugRenderer.render(world,camera.combined);
            camera.zoom = original_zoom;
            camera.position.set(original_pos);
            camera.update();
        }
    }

}
