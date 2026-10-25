package org.py.vedge.physics;

import org.py.vedge.data.JSONObject;
import org.py.vedge.data.JSONValue;
import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.ii.DoublePoint;
import org.py.vedge.ii.Token;
import org.py.vedge.physics.external.PhysicsObject;
import org.py.vedge.util.Sparry;

public abstract class PhysicsAdapter {

    public static DoublePoint scale = new DoublePoint(1,1);

    private static final JSONObject config_default = new JSONObject();
    static {
        config_default.set("gravity_y",1);
        config_default.set("gravity_x",0);
    }
    public static JSONObject config_default() {
        return config_default.copy();
    }

    public JSONObject config;
    public final Sparry<PhysicsObject> objects;

    public PhysicsAdapter(JSONObject config, PhysicsObject... objects) {
        this.config = config;
        this.objects = new Sparry<>(objects);
    }
    public PhysicsAdapter(PhysicsObject... objects) {
        this.objects = new Sparry<>(objects);
    }
    public PhysicsAdapter(Token... tokens) {
        this.objects = new Sparry<>();
        for(Token token : tokens) objects.add(new PhysicsObject(token));
    }

    public JSONValue config(String key) {
        if(config != null) return config.get(key);
        else return config_default.get(key);
    }

    public final void init() {
        init_override();
        this.objects.forEach(PhysicsObject::init);
    }
    public abstract void init_override();
    public abstract void dispose();
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void postTick();


}
