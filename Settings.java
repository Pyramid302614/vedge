import java.util.HashMap;

public class Settings {

    private static final HashMap<String,JSONValue> compiled = new HashMap<>();

    private static final HashMap<String,JSONFile> files = new HashMap<>();
    
    public static void addFile(String name, JSONFile file) {
        files.put(name,file);
    }

    public static void syncFromDisk() {
        for(String name : files.keySet()) {
            JSONFile file = files.get(name);
            file.syncFromDisk();
            file.asJSONObject().forEachValue((i, p) -> compiled.put(name+"."+p,i));
        }
    }
    public static void syncToDisk() {
        for(String address : compiled.keySet()) {
            JSONValue value = compiled.get(address);
//            files.get(address.split("\\.")[0]).setValue(); // gotta do that stupid bucketing logic
        }
    }


}
