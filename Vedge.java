import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Vedge {

    public static ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    public static long startupTimestamp = System.currentTimeMillis();

    public static void start() {

        // Files
        JSONFile config = new JSONFile("vedge_main",";/vedge.json");
        ErrorHandler.makeSilentLog(); // Doesn't require settings file down below

        // Timers
        scheduler.scheduleAtFixedRate(JSONFile::tickAllDeferTimers,0,30,TimeUnit.SECONDS);



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
