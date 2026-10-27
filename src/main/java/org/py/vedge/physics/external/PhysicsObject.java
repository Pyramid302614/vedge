package org.py.vedge.physics.external;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.py.vedge.data.JSONObject;
import org.py.vedge.ii.*;
import org.py.vedge.infa.external.Pedro;
import org.py.vedge.infa.java.ErrorHandler;
import org.py.vedge.physics.PhysicsObjectAdapter;

import static org.py.vedge.physics.PhysicsAdapter.scale;

public class PhysicsObject extends PhysicsObjectAdapter {

    public World world; // Acts as a "pointer" (defined on PhysicsAdapter::init)
    public Body body; // Defined later

    public PhysicsObject(Token token) {
        super(token);
        token.physics = this; // Crucial
        if(token.physicsConfig != null) config = token.physicsConfig; // Also crucial ig
    }

    public PhysicsObject(Token token, JSONObject config) {
        super(token,config);
        token.physics = this; // Crucial
        if(token.physicsConfig != null) this.config = token.physicsConfig; // Also crucial ig
    }

    @Override
    public void init() {

        if(world == null) {
            ErrorHandler.silent("'world' is null upon calling PhysicsObject.init(), when it should have been defined beforehand in Physics::init");
            return;
        }
        if(!token.physicsHitbox.isElliptic && token.physicsHitbox.length == 0) {
            ErrorHandler.silent("No physics hitbox was specified for the token ("+token.hashCode()+") associated with this PhysicsObject.");
            return;
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = switch(config("type").asString()) {
            case "static" -> BodyDef.BodyType.StaticBody;
            case "kinematic" -> BodyDef.BodyType.KinematicBody;
            default -> BodyDef.BodyType.DynamicBody;
        };

        Polygon hitbox = token.physicsHitbox.scale(scale);
        if(!token.physicsHitbox.isElliptic) {
            Vector2 gdxPosition = Pedro.vg_gdx(Pedro.txyGdx(token.position.scale(scale),token.size.scale(scale)));
            bodyDef.position.set(gdxPosition);
        } else {
            Vector2 gdxPosition = Pedro.vg_gdx(Pedro.txyGdx(token.position.rOffset(new DoublePoint(-token.physicsHitbox.isElliptic__w/2.0,token.physicsHitbox.isElliptic__h/2.0)).scale(scale),token.size.scale(scale)));
            bodyDef.position.set(gdxPosition);
        }
        bodyDef.angle = (float)Math.toRadians(token.rotation);

        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = config("density").asFloat();
        fixtureDef.friction = config("friction").asFloat();
        fixtureDef.restitution = config("restitution").asFloat();

        if(token.physicsHitbox.isElliptic && token.physicsHitbox.isElliptic__w == token.physicsHitbox.isElliptic__h) {
            CircleShape circle = Pedro.vg_gdx(hitbox,new CircleShape());
            fixtureDef.shape = circle;
            body.createFixture(fixtureDef);
            circle.dispose();
        } else if(token.physicsHitbox.isElliptic && token.physicsHitbox.isElliptic__w != token.physicsHitbox.isElliptic__h) {
            for(PolygonShape indicy : Pedro.vg_gdx(hitbox,12)) {
                fixtureDef.shape = indicy;
                body.createFixture(fixtureDef);
                indicy.dispose();
            }
        } else {
            PolygonShape polygon = Pedro.vg_gdx(hitbox,new PolygonShape());
            fixtureDef.shape = polygon;
            body.createFixture(fixtureDef);
            polygon.dispose();
        }

        this.body = body;

    }

    @Override
    public void dispose_override() {}

    @Override
    public void sync() {

        if(body == null) return;
        Vector2 gdxPosition = body.getPosition(); // Around center (fml) EDIT: it's actually not
        if(body != null) {
            if(!token.physicsHitbox.isElliptic) {
                token.position.set(Pedro.txyVg(Pedro.gdx_vg(gdxPosition).toPosition(),token.size.scale(scale)).toPosition().rScale(scale));
            } else {
                token.position.set(Pedro.txyVg(Pedro.gdx_vg(gdxPosition).toPosition(),token.size.scale(scale)).toPosition().rScale(scale).offset(new DoublePoint(-token.physicsHitbox.isElliptic__w/2.0,token.physicsHitbox.isElliptic__h/2.0)));
            }
            token.rotation = Math.toDegrees(Pedro.tangGdx(body.getAngle()));
        }
    }

    // Not token position
    @Override
    public void setObjectPosition(Position position) {
        body.getPosition().set(Pedro.vg_gdx(Pedro.txyGdx(position.toDPoint())));
    }



    // Kinematic bodies
    @Override
    public void setVelocity(DoublePoint velocity) {
        body.setLinearVelocity(Pedro.txGdx(velocity.x()),Pedro.tyGdx(velocity.y()));
    }

    // Dynamic bodies
    @Override
    public void applyForce(DoublePoint force, DoublePoint point) {
        body.applyForce(Pedro.tdGdx(force),new Vector2(Pedro.txGdx(point.x()),Pedro.tyGdx(point.y())),true);
    }

}
