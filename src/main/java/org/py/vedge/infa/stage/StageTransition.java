package org.py.vedge.infa.stage;

import org.py.vedge.graphics.external.ModifiedGraphics;

import java.util.function.BiConsumer;

public class StageTransition {

    // WARNING: Adding any other fields must be copied in deriveLength()

    public int length; // MS
    public double transitionAt; // 0-1
    private final BiConsumer<ModifiedGraphics,Double> draw; // BiConsumer<mg,progress>

    public StageTransition(int lengthMS, double transitionAtPercent, BiConsumer<ModifiedGraphics,Double> draw) {
        length = lengthMS;
        transitionAt = transitionAtPercent;
        this.draw = draw;
    }
    public StageTransition Clone() {
        return new StageTransition(length,transitionAt,draw);
    }
    public StageTransition derive(int lengthMS) {
        StageTransition clone = Clone();
        clone.length = lengthMS;
        clone.transitionAt = transitionAt;
        return clone;
    }

    // Gets called after all render calls, meaning it's like an overlay
    public final void draw(ModifiedGraphics mg, double progress) {
        draw.accept(mg,progress);
    } // progress = 0-1

    public final static StageTransition NONE = new StageTransition(0,0,(mg,progress) -> {});
    public final static StageTransition WHITE_FADE = new StageTransition(
            1000,
            0.5,
            (mg,progress) -> {
                mg.fillNoStroke(254,255,255,(int)(-255*Math.abs(2*progress-1)+255));
                mg.rect(0,0,1,1);
            }
    );

}
