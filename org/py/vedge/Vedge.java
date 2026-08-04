package org.py.vedge;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Vedge {

    public static ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    public static long startupTimestamp = Time.nowMs();

    public static void start() {

        Time.initializeUnits();

        // Files
        JSONFile config = new JSONFile("vedge_main",";/vedge.json");
        Settings.addFile("vedge",config);
        ErrorHandler.makeSilentLog();
        Settings.syncFromDisk();


        // Timers
        Timers.create(new Process(JSONFile::tickAllDeferTimers,"Tick Defer Timers","tick_defer_timers"),Settings.get("vedge.timers.tick_defer_timers").asString());
        Timers.create(new Process(Settings::syncToDisk,"Sync Settings","sync_settings"),Settings.get("vedge.timers.sync_settings").asString());
        Timers.create(new Process(IterativeEaser::tickAll,"Tick Iterative Timers","tick_iterative_timers"),Settings.get("vedge.timers.tick_iterative_timers").asString());
        Timers.create(new Process(Observer::tickAll,"Tick Observers","tick_observers"),Settings.get("vedge.timers.tick_observers").asString());

        Frame.frameProcesses(
                new Process(Entity2D::tickAll,"Tick Entities","tick_entities"),
                new Process(Entity2D::collisionAll,"Entity Collision","collision_entities"),
                new Process(Entity2D::renderAll,"Render Entities","render_entities")
        );

        if(config.get("new_instance").asBoolean()) {
            config.setDeferSync("new_instance",new JSONValue(false));
            if(onNewInstance != null) onNewInstance.run();
        }

    }

    private static Runnable onNewInstance;

    public static void onNewInstance(Runnable runnable) {
        onNewInstance = runnable;
    }

}
