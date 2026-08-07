package org.py.vedge;

import java.util.HashMap;

public class Settings {

    private static final HashMap<String,JSONValue> compiled = new HashMap<>();

    private static final HashMap<String,JSONFile> files = new HashMap<>();
    
    public static void addFile(String name, JSONFile file) {
        files.put(name,file);
        compileFile(name,file);
    }

    private static void compileFile(String name, JSONFile file) {
        file.asJSONObject().forEach_recursive((i, p) -> compiled.put(name+"."+p,i));
    }

    public static void syncFromDisk() {
        compiled.clear();
        for(String name : files.keySet()) {
            JSONFile file = files.get(name);
            file.syncFromDisk();
            compileFile(name,file);
        }
    }
    public static void syncToDisk() {
        for(String address : compiled.keySet()) {
                for(String key : files.keySet())
                    compiled.forEach((k, v) -> {
                        if(k.startsWith(key))
                            files.get(key).asJSONObject().setValue(key.substring(key.split("\\.")[0].length() + 1), v);
                    });
            JSONValue value = compiled.get(address);
            JSONFile file = files.get(address.split("\\.")[0]);
            file.asJSONObject().setValue(address.substring(address.split("\\.")[0].length() + 1),value);
            file.syncToDisk();
        }
    }

    public static JSONValue get(String settingAddress) {

        JSONValue value = compiled.get(settingAddress);
        if(value == null) throw new RuntimeException("Cannot find setting: " + settingAddress);
        else return value;

    }
    public static JSONFile getFile(String name) {
        return files.get(name);
    }

    // Doesn't read cache, goes straight for the money
    public static JSONValue fetch(String settingAddress) {

        try {
            JSONFile file = files.get(settingAddress.split("\\.")[0]);
            file.syncFromDisk();
            JSONValue value = file.asJSONObject().getValue(settingAddress.substring(settingAddress.split("\\.")[0].length() + 1));
            compiled.get(settingAddress).override(value);
            return value;
        } catch(Exception e) {
            ErrorHandler.silent("Failed to get setting: " + settingAddress + " | " + e);
            return null;
        }

    }

    public static void set(String settingAddress, JSONValue value) {
        compiled.get(settingAddress).override(value);

    }

    // Syncs to file afterward
    public static void push(String settingAddress, JSONValue value) {

        set(settingAddress,value);
        JSONFile file = files.get(settingAddress.split("\\.")[0]);
        file.asJSONObject().setValue(settingAddress.substring(settingAddress.split("\\.")[0].length() + 1),value);
        file.syncToDisk();

    }


}
