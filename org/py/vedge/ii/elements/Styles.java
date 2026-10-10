package org.py.vedge.ii.elements;

import org.py.vedge.graphics.Color;

import java.util.HashMap;

public class Styles {

    public final HashMap<String,Property> value = new HashMap<>();

    public void set(String key, Property value) {
        this.value.put(key,value);
    }
    public void setValue(String key, Property value) { // Extracts value from Property object
        if(this.value.containsKey(key)) this.value.get(key).set(value);
        else set(key,value);
    }
    public void setValue(String key, Color value) {
        if(this.value.containsKey(key)) this.value.get(key).set(value);
        else set(key,new Property(value));
    }
    public void setValue(String key, double value) {
        if(this.value.containsKey(key)) this.value.get(key).set(value);
        else set(key,new Property(value));
    }
    public void setValue(String key, Property.P_Alignment value) {
        if(this.value.containsKey(key)) this.value.get(key).set(value);
        else set(key,new Property(value));
    }

    public Property get(String key) {
        return value.get(key);
    }

    public void tick() {
        value.forEach((k,v) -> v.tick());
    }

}
