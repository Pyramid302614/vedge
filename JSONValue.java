public class JSONValue {

    public enum Type { JSONObject, String }

    public final Type type;
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

    @Override
    public String toString() {
        return switch(type) {
            case JSONObject -> JSON.stringify(jsonObjectValue, 2);
            case String -> stringValue;
        };
    }

}
