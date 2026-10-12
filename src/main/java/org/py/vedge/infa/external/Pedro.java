package org.py.vedge.infa.external;

public class Pedro {

    public static com.badlogic.gdx.graphics.Color vg_gdx(org.py.vedge.graphics.Color vg) {
        return new com.badlogic.gdx.graphics.Color(
            vg.red()/255f,
            vg.green()/255f,
            vg.blue()/255f,
            vg.alpha()/255f
        );
    }

}
