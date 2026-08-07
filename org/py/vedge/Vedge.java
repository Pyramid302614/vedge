package org.py.vedge;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Vedge {

    public static ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    public static long startupTimestamp = Time.nowMs();

    public static void start() {

        // Files
        JSONFile config = new JSONFile("vedge_main",";/vedge.json");
        Settings.addFile("vedge",config);
        ErrorHandler.makeSilentLog();
        Settings.syncFromDisk();


        // Timers / Time
        Time.initializeUnits();
        Timers.create(new Process(JSONFile::tickAllDeferTimers,"Tick Defer Timers","tick_defer_timers"),Settings.get("vedge.timers.tick_defer_timers").asString());
        Timers.create(new Process(Settings::syncToDisk,"Sync Settings","sync_settings"),Settings.get("vedge.timers.sync_settings").asString());
        Timers.create(new Process(IterativeEaser::tickAll,"Tick Iterative Timers","tick_iterative_timers"),Settings.get("vedge.timers.tick_iterative_timers").asString());
        Timers.create(new Process(Observer::tickAll,"Tick Observers","tick_observers"),Settings.get("vedge.timers.tick_observers").asString());
        Timers.create(new Process(InputListener::handleWiles,"Tick Input Listeners","tick_while_input_listeners"),Settings.get("vedge.timers.tick_while_input_listeners").asString());

        // Input
        InputListener.compileInputGroups();
        Window.addOnInitFinish(InputListener::configure);

        // Frame processes
        Frame.frameProcesses(
                new Process(Entity2D::tickAll,"Tick Entities","tick_entities"),
                new Process(Token::collisionAll,"Token Collision","collision_entities"),
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
