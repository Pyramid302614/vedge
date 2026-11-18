package org.py.vedge.ii;

import org.py.vedge.data.JSONObject;
import org.py.vedge.infa.external.Window;
import org.py.vedge.util.Sparry;

import java.util.function.Consumer;

public class TokenGroup {

    public final Sparry<Token> all = new Sparry<>();
    private Sparry<Token> primary = new Sparry<>();
    private Sparry<Token> secondary = new Sparry<>();

    public void addPrimaries(Sparry<Token> tokens) {
        all.addAll(tokens);
        primary.addAll(tokens);
    }
    public void addSecondaries(Sparry<Token> tokens) {
        all.addAll(tokens);
        secondary.addAll(tokens);
    }
    public void addPrimaries(Token... tokens) {
        all.addAll(tokens);
        primary.addAll(tokens);
    }
    public void addSecondaries(Token... tokens) {
        all.addAll(tokens);
        secondary.addAll(tokens);
    }
    public void add(Sparry<Token> tokens) {
        all.addAll(tokens);
    }
    public void add(Token... tokens) {
        all.addAll(tokens);
    }

    public void tickAll() {
        all.forEach(Token::tick);
    }
    public void collideAll() {

    }
    public void renderAll() {
        all.forEach(i -> i.render(Window.vg.graphics));
    }

    public void forEach(Consumer<Token> consumer) {
        all.forEach(consumer);
    }

    // - Token object will include overriden methods
    // - Json will include the following properties:
    // * hitbox (four number array: [x,y,w,h])
    // * hitbox_type ("rect","ellipse")
    // * physics_hitbox (four number array: [x,y,w,h])
    // * physics_hitbox_type ("rect","ellipse")
    // * position (two number array: [x,y])
    // * size (two number array: [w,h])
    // * rotation (one number, in degrees)
    public static Token make(Token token, JSONObject json) {
        token.hitbox.setShape(json.get("hitbox_type").asString().equals("rect")?Polygon.rect(json.get("hitbox").asArray().get(0).asDouble(),json.get("hitbox").asArray().get(1).asDouble(),json.get("hitbox").asArray().get(2).asDouble(),json.get("hitbox").asArray().get(3).asDouble()):Polygon.ellipse(json.get("hitbox").asArray().get(0).asDouble(),json.get("hitbox").asArray().get(1).asDouble(),json.get("hitbox").asArray().get(2).asDouble(),json.get("hitbox").asArray().get(3).asDouble()));
        token.hitbox.setShape(json.get("physics_hitbox_type").asString().equals("rect")?Polygon.rect(json.get("physics_hitbox").asArray().get(0).asDouble(),json.get("physics_hitbox").asArray().get(1).asDouble(),json.get("physics_hitbox").asArray().get(2).asDouble(),json.get("physics_hitbox").asArray().get(3).asDouble()):Polygon.ellipse(json.get("hitbox").asArray().get(0).asDouble(),json.get("hitbox").asArray().get(1).asDouble(),json.get("hitbox").asArray().get(2).asDouble(),json.get("hitbox").asArray().get(3).asDouble()));
        token.position.set(json.get("position").asArray().get(0).asDouble(),json.get("position").asArray().get(1).asDouble());
        token.size.set(json.get("size").asArray().get(0).asDouble(),json.get("size").asArray().get(1).asDouble());
        token.rotation = json.get("rotation").asDouble();
        return token;
    }

}
