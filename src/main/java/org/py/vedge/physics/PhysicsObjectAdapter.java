package org.py.vedge.physics;

import org.py.vedge.data.JSONObject;
import org.py.vedge.data.JSONValue;
import org.py.vedge.ii.DoublePoint;
import org.py.vedge.ii.Position;
import org.py.vedge.ii.Token;
import org.py.vedge.physics.external.Physics;
import org.py.vedge.physics.external.PhysicsObject;

public abstract class PhysicsObjectAdapter {

    private static final JSONObject config_default = new JSONObject();
    static {
        config_default.set("type","dynamic"); // "dynamic", "static", "kinematic" > https://libgdx.com/wiki/extensions/physics/box2d
        config_default.set("density",0.2);
        config_default.set("friction",0.2);
        config_default.set("restitution",0);
    }
    public static JSONObject config_default() {
        return config_default.copy();
    }

    public JSONObject config;
    public Token token;

    public PhysicsObjectAdapter(Token token) {
        this.token = token;
        token.onPositionUpdate(this::setObjectPosition);
    }
    public PhysicsObjectAdapter(Token token, JSONObject config) {
        this.token = token;
        this.config = config;
        token.onPositionUpdate(this::setObjectPosition);
    }

    public JSONValue config(String key) {
        if(config != null && config.containsKey(key)) return config.get(key);
        else return config_default.get(key);
    }


    public void dispose() {
        token.offPositionUpdate(this::setObjectPosition);
        dispose_override();
    }
    public abstract void dispose_override();

    public abstract void init();
    public abstract void sync();

    // Kinematic bodies
    public abstract void setVelocity(DoublePoint velocity);

    // Dynamic bodies
    public abstract void applyForce(DoublePoint force, DoublePoint point);

    // Not token position
    public abstract void setObjectPosition(Position position);

}
