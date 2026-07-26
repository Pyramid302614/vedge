import java.io.File;
import java.io.IOException;

public class ErrorHandler {

    public static final Sparry<Error> silenced = new Sparry<>();

    private static String silentLogPath = ":/.silent.log";
    private static File silentLog;

    public static void printSilentLog() {
        System.err.println("SILENCED ERRORS:\n"+silenced.join("\n"));
    }
    public static void makeSilentLog() {
        try {
            if(!Resources.asFile(":").exists()) Resources.createDirectory(":");
            Resources.createFile(silentLogPath);
            Resources.overrideContents_safe(silentLogPath,"");
        } catch(IOException e) {
            System.err.println("Error creating silent log: " + e);
        }
    }

    public static void Throw(Error error) {

        System.err.println(error);

    }

    public static void silent(String message) {

        silenced.add(new Error(message,true));
        Resources.appendToContents_safe(silentLogPath,(Time.nowMs()-Vedge.startupTimestamp)+"] " + message);

    }
    public static void silent(Exception exception) {

        silenced.add(new Error(exception.getMessage(),true));
        Resources.appendToContents_safe(silentLogPath,exception.getMessage());

    }

}
