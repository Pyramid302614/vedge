import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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



    public JSONValue get(int index) {
        return values()[index];
    }
    public JSONValue get(String key) {
        return items.get(key);
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
        try {
//            JSONValue temp = new JSONValue(copy());
//            Sparry<JSONValue> dissected = new Sparry<>();
//            String[] split = address.split("\\.");
//            for(int i = 0; i < split.length; i++) {
//                dissected.add(temp != null ? temp : new JSONValue(new JSONObject()));
//                if(i != split.length-1 && (temp == null || temp.asJSONObject().get(split[i]).type != JSONValue.Type.JSONObject)) temp.asJSONObject().set(split[i],new JSONValue(new JSONObject()));
//                temp = temp.asJSONObject().get(split[i]);
//            }
//            dissected.push(value);
//            for(let i = split.length-1; i >= 0; i--)
//                dissected[i][split[i]] = dissected[i+1];
//
//            return dissected[0];
        } catch(Exception e) {
            ErrorHandler.silent("Error setting property for JSONObject: (Address: " + address + ") " + e);
        }
    }

    public void forEach(Consumer<JSONValue> consumer) {
        for(JSONValue value : values()) {
            consumer.accept(value);
        }
    }

    // Recursive logic
    public void forEachValue(BiConsumer<JSONValue,String> consumer) {
        forEachValue(consumer,"");
    }
    private void forEachValue(BiConsumer<JSONValue,String> consumer, String path) {
        for(String key : keys()) {
            JSONValue value = get(key);
            if(value.type != JSONValue.Type.JSONObject) consumer.accept(value,path+"."+key);
            else value.asJSONObject().forEachValue(consumer,path+"."+key);
        }
    }


    public HashMap<String,JSONValue> getRawHashMap() {
        return items;
    }

    public JSONObject copy() {
        JSONObject clone = new JSONObject();
        for(String key : clone.keys()) {
            JSONValue value = get(key);
            if(value.type == JSONValue.Type.JSONObject)
                clone.set(key,value.asJSONObject().copy());
            else
                clone.set(key,value);
        }
        return clone;
    }

    @Override
    public String toString() {
        return JSON.stringify(this,2);
    }

}
