package org.py.vedge.infa;

import org.py.vedge.util.Sparry;
import org.py.vedge.graphics.Graphics;
import org.py.vedge.ii.Entity;
import org.py.vedge.ii.LevelMap;
import org.py.vedge.ii.Player;
import org.py.vedge.ii.TokenGroup;

import java.util.HashMap;

public class Stage {

    public static String activeStage;

    public static HashMap<String,Stage> stages = new HashMap<>();

    public static void activate(String stage) {
        activeStage = stage;
    }

    private static Stage active() {
        return stages.get(activeStage);
    }

    public static void tickStage() {
        active().tick();
    }
    public static void collideStage() {
        active().collide();
    }
    public static void renderStage() {
        active().render(Window.graphics);
    }


    private final TokenGroup tokens = new TokenGroup();
    private final String name;

    public Stage(String name) {

        this.name = name;
        stages.put(name,this);

    }
    public void activate() {
        Stage.activate(name);
    }


    private final Sparry<Entity> entities = new Sparry<>();
    private Player player;
    private LevelMap levelMap;


    private void tickEntities() {
        entities.forEach((entity) -> {
            switch(entity.state) {
                case Loaded, Stained:
                    entity.tick(); break;
                case Offloaded, Deleted:
                    break;
                case Unloaded:
                    entities.remove(entity); break;
            }
            entity.tick();
        });
    }
    private void renderEntities(Graphics g) {

        entities.forEach(i -> {
            if(i.loaded()) i.render(g);
        });

        if(Debug.RENDER_TOKEN_HITBOXES) {
            g.noFill();
            g.stroke(Debug.RENDER_TOKEN_HITBOXES_Color);
            g.strokeWeight(2);
            entities.forEach(e -> {
                double[] X = new double[e.hitbox.length];
                double[] Y = new double[e.hitbox.length];
                for(int i = 0; i < e.hitbox.length; i++) {
                    X[i] = e.TCX(e.hitbox.get(i).x());
                    Y[i] = e.TCY(e.hitbox.get(i).y());
                }
                g.polygon(X,Y);
            });
        }

    }


    private void tickPlayer() {
        player.tick();
    }
    private void renderPlayer(Graphics g) {
        player.render(g);
    }


    private void tickLevelMap() {
        levelMap.tick();
    }
    private void renderLevelMap(Graphics g) {
        levelMap.render(g);
    }



    public void addEntities(Entity... entities) {
        this.entities.addAll(entities);
        tokens.addSecondaries(entities);
    }
    public void setPlayer(Player player) {
        this.player = player;
        tokens.addPrimaries(player);
    }
    public void setLevelMap(LevelMap levelMap) {
        this.levelMap = levelMap;
        tokens.addSecondaries(levelMap.asTokens());
    }



    private void tick() {
        if(levelMap != null) tickLevelMap();
        if(entities.length > 0) tickEntities();
        if(player != null) tickPlayer();
    }
    private void collide() {

    }
    private void render(Graphics g) {
        if(levelMap != null) renderLevelMap(g);
        if(entities.length > 0) renderEntities(g);
        if(player != null) renderPlayer(g);
    }



}
