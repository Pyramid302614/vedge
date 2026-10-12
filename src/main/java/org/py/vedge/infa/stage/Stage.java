package org.py.vedge.infa.stage;

import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.graphics.external.ModifiedGraphics;
import org.py.vedge.ii.Entity;
import org.py.vedge.ii.LevelMap;
import org.py.vedge.ii.Player;
import org.py.vedge.ii.TokenGroup;
import org.py.vedge.ii.elements.Element;
import org.py.vedge.ii.elements.ElementGroup;
import org.py.vedge.infa.Debug;
import org.py.vedge.infa.Timers;
import org.py.vedge.infa.external.Window;
import org.py.vedge.infa.java.Error;
import org.py.vedge.infa.java.Time;
import org.py.vedge.util.Sparry;

import java.util.HashMap;

public class Stage {

    private static String activeStage;
    private static String graceStage;

    private static StageTransition transition = StageTransition.NONE;
    private static long transitionTimestamp = -1; // -1 if no transition at the moment

    public static HashMap<String,Stage> stages = new HashMap<>();

    public static void activate(String stage) {
        activate(stage,null);
    }
    public static void activate(String stage, Runnable then) {
        if(!stages.containsKey(stage)) {
            new Error("An attempt was made to switch to a non-existent stage: " + stage);
            return;
        }
        Stage active = active();
        Stage s = stages.get(stage);
        Runnable iii = () -> {
            activeStage = stage;
            active().enter();
            if(then != null) then.run();
        };
        Runnable ii = () -> {
            transition = s.appliedTransition;
            transitionTimestamp = Time.nowMs();
            Timers.create(iii,(long)(s.appliedTransition.length*s.appliedTransition.transitionAt));
            Timers.create(() -> {
                transition = StageTransition.NONE;
                transitionTimestamp = -1;
            },s.appliedTransition.length);
        };
        Runnable i = () -> {
            if(active == null) {
                ii.run();
                return;
            }
            if(active.grace > 0) {
                graceStage = active().name;
                Timers.create(() -> graceStage = null,active.grace);
                ii.run();
            }
            if(active.linger > 0) Timers.create(ii,active.linger);
            active.exit();
        };
        i.run();
    }
    public static void jumpTo(String stage) {
        active().exit();
        activeStage = stage;
        active().enter();
    }
    public static void transitionTo(String stage, StageTransition transition) {
        Stage s = stages.get(stage);
        StageTransition buffer = s.appliedTransition;
        s.applyTransition(transition);
        activate(stage,() -> s.applyTransition(buffer));
    }
    public static void activate(String... stages) {
        for(int i = 0; i < stages.length-1; i++) {
            Stage stage = Stage.stages.get(stages[i]);
            Stage nextStage = Stage.stages.get(stages[i+1]);
            stage.addOnceExit(nextStage::activate);
        }
        Stage.activate(stages[0]);
    }
    // Vector2<Stage,DurationItWillBeheldFor>
    public static void activateScene(String[] stages, String[] durations) {
        for(int i = 0; i < stages.length-1; i++) {
            Stage stage = Stage.stages.get(stages[i]);
            Stage nextStage = Stage.stages.get(stages[i]);
            final String stageDuration = durations[i];
            stage.addOnceEnter(() -> Timers.create(nextStage::activate,stageDuration));
        }
        activate(stages[0]);
    }


    public static Stage active() {
        return stages.get(activeStage);
    }

    public static void tickStage() {
        if(active() == null) return;
        active().tick();
        if(graceStage != null) stages.get(graceStage).tick();
    }
    public static void collideStage() {
        if(active() == null) return;
        active().collide();
        if(graceStage != null) stages.get(graceStage).collide();
    }
    public static void renderStage() {
        if(active() == null) return;
        active().render(Window.vg.graphics);
        if(graceStage != null) stages.get(graceStage).render(Window.vg.graphics);
        if(transitionTimestamp != -1) transition.draw(new ModifiedGraphics(Window.vg.graphics, Window.vg.position.x, Window.vg.position.y, Window.vg.size.w, Window.vg.size.h), (double)(Time.nowMs() - transitionTimestamp)/transition.length);
    }

    private int grace = 0;
    public Stage grace(int graceMS) {
        this.grace = graceMS;
        return this;
    }

    private int linger = 0;
    public Stage linger(int lingerMS) {
        this.linger = lingerMS;
        return this;
    }


    private final TokenGroup tokens = new TokenGroup();
    private final ElementGroup elements = new ElementGroup();
    private final String name;

    private StageTransition appliedTransition = StageTransition.NONE;

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

        if(Debug.RENDER_ENTITY_HITBOXES) {
            g.noFill();
            g.stroke(Debug.RENDER_ENTITY_HITBOXES_Color);
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
    public void applyTransition(StageTransition transition) {
        appliedTransition = transition;
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

    // EXIT EXCLUDES LINGER AND GRACE

    public void onEnter() {

    }
    public void onExit() {

    }

    private Sparry<Runnable> onceEnters = new Sparry<>();
    private Sparry<Runnable> onceExits = new Sparry<>();

    public void addOnceEnter(Runnable runnable) {
        if(onceEnters == null) onceEnters = new Sparry<>(runnable);
        else onceEnters.add(runnable);
    }
    public void addOnceExit(Runnable runnable) {
        if(onceExits == null) onceExits = new Sparry<>(runnable);
        else onceExits.add(runnable);
    }

    private void enter() {
        onEnter();
        elements.onEnter();
        onceEnters.forEach(Runnable::run);
        onceEnters.clear();
    }
    private void exit() {
        onExit();
        elements.onExit();
        onceExits.forEach(Runnable::run);
        onceExits.clear();
    }




}
