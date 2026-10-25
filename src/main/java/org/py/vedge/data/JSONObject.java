package org.py.vedge.data;

import org.py.vedge.infa.java.ErrorHandler;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class JSONObject {

    private final HashMap<String,JSONValue> items = new HashMap<>();

    public int length() {
        return items.size();
    }
    public String[] keys() {
        return items.keySet().toArray(new String[0]);
    }
    public JSONValue[] values() {
        return items.values().toArray(new JSONValue[0]);
    }


    public static JSONObject of(String string) {
        return JSON.parse(string);
    }


    // Shortcut
    public JSONObject o(String key) {
        return get(key).asJSONObject();
    }
    public JSONObject s(String k, JSONValue v) {
        set(k,v);
        return this;
    }
    public JSONObject s(String k, JSONObject v) {
        set(k,v);
        return this;
    }
    public JSONObject s(String k, String v) {
        set(k,v);
        return this;
    }
    public JSONObject s(String k, int v) {
        set(k,v);
        return this;
    }
    public JSONObject s(String k, double v) {
        set(k,v);
        return this;
    }
    public JSONObject s(String k, boolean v) {
        set(k,v);
        return this;
    }


    public boolean containsKey(String key) {
        return items.containsKey(key);
    }

    public JSONValue get(int index) {
        return values()[index];
    }
    public JSONValue get(String key) {
        return items.getOrDefault(key,null);
    }
    public JSONValue getValue(String address) {
        try {
            JSONValue buffer = new JSONValue(this);
            for(String part : address.split("\\.")) {
                buffer = buffer.asJSONObject().get(part);
            }
            return buffer;
        } catch(Exception e) {
            ErrorHandler.silent("Failed to get value from JSON object: (Address: " + address + ") " + e);
            return null;
        }
    }

    public void set(String key, JSONValue value) {
        items.put(key,value);
    }
    public void set(String key, JSONObject object) {
        items.put(key,new JSONValue(object));
    }
    public void set(String key, String string) {
        items.put(key,new JSONValue(string));
    }
    public void set(String key, int integer) {
        items.put(key,new JSONValue(integer));
    }
    public void set(String key, double double_) {
        items.put(key,new JSONValue(double_));
    }
    public void set(String key, boolean boolean_) {
        items.put(key,new JSONValue(boolean_));
    }
    public void setValue(String address, JSONValue value) {
        // do NOT touch
        JSONValue buffer = new JSONValue(this); // stores reference and somehow works :sob:
        String[] parts = address.split("\\.");
        for(int i = 0; i < parts.length-1; i++) {
            buffer = buffer.asJSONObject().get(parts[i]);
        }
        buffer.asJSONObject().set(parts[parts.length-1],value);
    }

    public void forEach(BiConsumer<JSONValue,String> consumer) {
        for(String key : keys()) {
            consumer.accept(get(key),key);
        }
    }

    // Recursive logic
    public void forEach_recursive(BiConsumer<JSONValue,String> consumer) {
        forEach_recursive(consumer,"");
    }
    private void forEach_recursive(BiConsumer<JSONValue,String> consumer, String path) {
        for(String key : keys()) {
            JSONValue value = get(key);
            if(value.type != JSONValue.Type.JSONObject) consumer.accept(value,path+key); // path: ___. + name
            else value.asJSONObject().forEach_recursive(consumer,path+key+"."); // adds period for next one to keep clean path
        }
    }


    public HashMap<String,JSONValue> getRawHashMap() {
        return items;
    }

    public JSONObject copy() {
        JSONObject clone = new JSONObject();
        for(String key : keys()) {
            JSONValue value = get(key);
            clone.set(key,value.copy());
        }
        return clone;
    }

    @Override
    public String toString() {
        return JSON.stringify(this,2);
    }

}
