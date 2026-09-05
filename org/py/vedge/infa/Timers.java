package org.py.vedge.infa;

import org.py.vedge.Vedge;
import org.py.vedge.infa.java.Time;

import java.util.concurrent.TimeUnit;

public class Timers {

    // Duration can be "pre-frame" or "post-frame" for by-frame timing
    public static void steady(Process process, String duration) {
        switch(duration) {
            case "pre-frame":
                Frame.onNewFrame(process); break;
            case "post-frame":
                Frame.afterNewFrame(process); break;
            default:
                Vedge.scheduler.scheduleAtFixedRate(process::run,0, Time.msFromString(duration),TimeUnit.MILLISECONDS); break;
        }
    }
    public static void create(Runnable runnable, String delay) {
        create(runnable,Time.msFromString(delay));
    }
    public static void create(Runnable runnable, long delay) {
        Vedge.scheduler.schedule(runnable,delay,TimeUnit.MILLISECONDS);
    }

}
