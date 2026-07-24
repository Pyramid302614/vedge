public class JSONValue {

    public enum Type { JSONObject, String }

    public Type type;
    private JSONObject jsonObjectValue;
    private String stringValue;

    public JSONObject asJSONObject() {
        return jsonObjectValue;
    }
    public String asString() {
        return stringValue;
    }
    public int asInteger() {
        return Integer.parseInt(stringValue);
    }
    public double asDouble() {
        return Double.parseDouble(stringValue);
    }
    public boolean asBoolean() {
        return Boolean.parseBoolean(stringValue);
    }

    public JSONValue(JSONObject object) {
        type = Type.JSONObject;
        jsonObjectValue = object;
    }
    public JSONValue(String string) {
        type = Type.String;
        stringValue = string;
    }
    public JSONValue(int integer) {
        type = Type.String;
        stringValue = Integer.toString(integer);
    }
    public JSONValue(double double_) {
        type = Type.String;
        stringValue = Double.toString(double_);
    }
    public JSONValue(boolean boolean_) {
        type = Type.String;
        stringValue = Boolean.toString(boolean_);
    }


    public void override(JSONObject object) {
        type = Type.JSONObject;
        jsonObjectValue = object;
    }
    public void override(String string) {
        type = Type.String;
        stringValue = string;
    }
    public void override(int integer) {
        type = Type.String;
        stringValue = Integer.toString(integer);
    }
    public void override(double double_) {
        type = Type.String;
        stringValue = Double.toString(double_);
    }
    public void override(boolean boolean_) {
        type = Type.String;
        stringValue = Boolean.toString(boolean_);
    }
    public void override(JSONValue value) {
        type = value.type;
        if(type == Type.JSONObject)
            jsonObjectValue = value.asJSONObject();
        if(type == Type.String)
            stringValue = value.asString();
    }

    @Override
    public String toString() {
        return switch(type) {
            case JSONObject -> JSON.stringify(jsonObjectValue, 2);
            case String -> stringValue;
        };
    }

}
