package org.py.vedge;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Vedge {

    public static ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    public static long startupTimestamp = Time.nowMs();

    public static void start() {

        Time.initializeUnits();

        // Files
        JSONFile config = new JSONFile("vedge_main",";/vedge.json");
        ErrorHandler.makeSilentLog(); // Doesn't require settings file down below

        Settings.syncFromDisk();


        // Timers
        scheduler.scheduleAtFixedRate(JSONFile::tickAllDeferTimers,0,Time.msFromString(config.get("timers.tick_defer_timers").asString()),TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(Settings::syncToDisk,Time.msFromString(config.get("timers.sync_settings").asString()),Time.msFromString(config.get("timers.sync_settings").asString()),TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(IterativeEaser::tickAll,0,Time.msFromString(config.get("timers.tick_iterative_timers").asString()),TimeUnit.MILLISECONDS);


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
