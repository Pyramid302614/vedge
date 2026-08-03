package org.py.vedge;

import java.time.Duration;
import java.util.HashMap;

public class JSONFile {

    private static final HashMap<String,JSONFile> jsonFiles = new HashMap<>();
    private static JSONFile main;

    public static JSONObject file(String name) {
        return jsonFiles.get(name).contents;
    }

    public static JSONValue mp(String key) {
        return main.get(key);
    }
    public static JSONFile m() {
        return main;
    }
    public JSONFile setMain() {
        main = this;
        return this;
    }
    public static void setMain(JSONFile file) {
        main = file;
    }

    private static final Duration deferAmount = Duration.ofMinutes(1);
    public long deferTimestamp = -1;
    public static void tickAllDeferTimers() {

        long now = Time.nowMs();
        jsonFiles.values().forEach(i -> {
            if(i.deferTimestamp != -1 && now - i.deferTimestamp > deferAmount.toMillis()) {
                i.syncToDisk();
                i.deferTimestamp = -1;
            }
        });

    }



    private final String path;
    private JSONObject contents;

    public JSONFile(String name, String path) {
        this.path = path;
        this.contents = JSON.parse(Resources.getContents_safe(path));
        jsonFiles.put(name,this);
    }


    public JSONValue asJSONValue() {
        return new JSONValue(contents);
    }
    public JSONObject asJSONObject() {
        return contents;
    }
    public JSONValue get(String key) {
        return contents.get(key);
    }

    public void set(String key, JSONValue value) {
        contents.set(key,value);
        syncToDisk();
    }
    // Syncs with actual file later; useful for properties that are overridden a lot
    public void setDeferSync(String key, JSONValue value) {
        deferTimestamp = 500;
        contents.set(key,value);
        syncToDisk();
    }

    public void syncFromDisk() {
        this.contents = JSON.parse(Resources.getContents_safe(path));
    }
    public void syncToDisk() {
        Resources.overrideContents_safe(path,JSON.stringify(contents,2));
    }

    @Override
    public String toString() {
        return contents.toString();
    }

}
