package org.py.vedge.ii.elements;

import java.util.HashMap;

public class Styles {

    public final HashMap<String,Property> value = new HashMap<>();

    public Styles set(String key, Property value) {
        this.value.put(key,value);
        return this;
    }
    public Styles setValue(String key, Property value) {
        if(this.value.containsKey(key)) this.value.get(key).set(value);
        return this;
    }
    public Property get(String key) {
        return value.get(key);
    }

    public void tick() {
        value.forEach((k,v) -> v.tick());
    }

}
