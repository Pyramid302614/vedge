package org.py.vedge.infa;

import org.py.vedge.graphics.Graphics;
import org.py.vedge.ii.Entity;
import org.py.vedge.ii.LevelMap;
import org.py.vedge.ii.Player;
import org.py.vedge.ii.TokenGroup;
import org.py.vedge.ii.elements.Element;
import org.py.vedge.ii.elements.ElementGroup;
import org.py.vedge.infa.java.Error;
import org.py.vedge.util.Sparry;

import java.util.HashMap;

public class Stage {

    private static String activeStage;
    private static String graceStage;

    public static HashMap<String,Stage> stages = new HashMap<>();

    public static void activate(String stage) {
        if(activeStage != null) {
            if(active().grace > 0) {
                graceStage = active().name;
                Timers.create(() -> graceStage = null,active().grace);
            }
            active().onExit();
        }
        activeStage = stage;
        if(!stages.containsKey(stage)) new Error("An attempt was made to switch to a non-existent stage: " + stage);
        active().onEnter();
    }

    public static Stage active() {
        return stages.get(activeStage);
    }

    public static void tickStage() {
        active().tick();
        if(graceStage != null) stages.get(graceStage).tick();
    }
    public static void collideStage() {
        active().collide();
        if(graceStage != null) stages.get(graceStage).collide();
    }
    public static void renderStage() {
        active().render(Window.graphics);
        if(graceStage != null) stages.get(graceStage).render(Window.graphics);
    }


    private int grace = 0;
    public Stage grace(int grace) {
        this.grace = grace;
        return this;
    }


    private final TokenGroup tokens = new TokenGroup();
    private final ElementGroup elements = new ElementGroup();
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

    private void tickElements() {
        elements.tick();
    }
    private void renderElements(Graphics g) {
        elements.render(g);
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

    public void addElements(Element... elements) {
        this.elements.add(elements);
    }




    private void tick() {
        if(levelMap != null) tickLevelMap();
        if(entities.length > 0) tickEntities();
        if(player != null) tickPlayer();
        if(elements.length() > 0) tickElements();
    }
    private void collide() {

    }
    private void render(Graphics g) {
        if(levelMap != null) renderLevelMap(g);
        if(entities.length > 0) renderEntities(g);
        if(player != null) renderPlayer(g);
        if(elements.length() > 0) renderElements(g);
    }



    private void onEnter() {
        elements.onEnter();
    }
    private void onExit() {
        elements.onExit();
    }



}
