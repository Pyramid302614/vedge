package org.py.vedge;

public class JSON {

    public static String stringify(JSONObject object, int space) {

        return nestedStringify(object,""," ".repeat(Math.max(0,space)));

    }
    private static String nestedStringify(JSONObject object, String currentIndent, String space) {

        StringBuilder output = new StringBuilder("{\n"+currentIndent+space);

        for(int i = 0; i < object.length(); i++) {

            String key = object.keys()[i];
            JSONValue value = object.values()[i];

            switch(value.type) {
                case JSONObject -> output.append("\"").append(key).append("\":").append(!space.isEmpty() ? " " : "").append(nestedStringify(value.asJSONObject(), currentIndent+space, space));
                case String -> output.append("\"").append(key).append("\":").append(!space.isEmpty() ? " " : "").append("\"").append(value.asString()).append("\"");
                case NonString -> output.append("\"").append(key).append("\":").append(!space.isEmpty() ? " " : "").append(value.asString());
                case Array -> output.append("\"").append(key).append("\":").append(!space.isEmpty() ? " " : "").append(value.asArray().length == 0 ? "[]" : value.asArray().toString());
            }

            output.append(i != object.length() - 1 ? ( ",\n" + currentIndent + space ) : ( "\n" + currentIndent + "}" ));

        }

        return object.length() > 0 ? output.toString() : "{}";

    }

    public static JSONObject parse(String string) {

        JSONObject obj = new JSONObject();
        String str = string
                .replaceAll("\n","")
                .replaceAll(" ","")
                .replaceAll("\"(?=:)|(?<=\\{)\"|(?<=,)\"|(?<=^)\"","");
        str = str.substring(1,str.length()-1);

        if(str.equals("{}")) return obj;

        String buffer = "";
        String propertyBuffer = "";
        boolean mode = false; // false = property, true = value
        boolean write = true;
        int scope = 0;
        JSONValue.Type type = null;

        for(int i = 0; i < str.length(); i++) {

            char pc = i != 0 ? str.charAt(i-1) : (char)0;
            char c = str.charAt(i);
            char nc = i != str.length()-1 ? str.charAt(i+1) : (char)0;

            if((type == JSONValue.Type.JSONObject && c == '{') || (type == JSONValue.Type.Array && c == '[')) scope++;
            else if((type == JSONValue.Type.JSONObject && c == '}') || (type == JSONValue.Type.Array && c == ']')) scope--;

            if(scope == 0) switch(c) {

                case '"':
                    if(mode && type == JSONValue.Type.String && pc != '\\') {
                        write = false;
                        obj.set(propertyBuffer,buffer);
                        buffer = "";
                        propertyBuffer = "";
                    }
                    break;

                case ',':
                    if(type != JSONValue.Type.String) {
                        write = false;
                        JSONValue value =
                                type == JSONValue.Type.JSONObject ? new JSONValue(parse(buffer)) :
                                        (type == JSONValue.Type.Array ? parseArray(buffer) : new JSONValue(buffer));
                        value.type = type;
                        obj.set(propertyBuffer,value);
                        buffer = "";
                        propertyBuffer = "";
                    }
                    break;
                case ':':
                    if(propertyBuffer.isEmpty()) {
                        write = false;
                        propertyBuffer = buffer;
                        buffer = "";
                    }
                    break;

            }

            if(write) buffer += c;

            if(scope == 0) switch(c) {

                case ':':
                    if(buffer.isEmpty()) {
                        write = true;
                        mode = true;
                        switch(nc) {
                            case '"':
                                type = JSONValue.Type.String;
                                i++;
                                break;
                            case '{':
                                type = JSONValue.Type.JSONObject;
                                break;
                            case '[':
                                type = JSONValue.Type.Array;
                                break;
                            default:
                                type = JSONValue.Type.NonString;
                                break;
                        }
                    }
                    break;

                case ',':
                    if(buffer.isEmpty()) {
                        write = true;
                        mode = false;
                        buffer = "";
                        break;
                    }

            }

        }

        if(!propertyBuffer.isEmpty()) {
            JSONValue value =
                    type == JSONValue.Type.JSONObject ? new JSONValue(parse(buffer)) :
                            (type == JSONValue.Type.Array ? parseArray(buffer) : new JSONValue(buffer));
            value.type = type;
            obj.set(propertyBuffer,value);
        }

        return obj;

    }

    public static JSONValue parseArray(String array) {

        array = array.replaceAll("\n","").replaceAll(" ","");

        if(array.equals("[]")) return new JSONValue(new JSONValue[0]);

        Sparry<JSONValue> output = new Sparry<>();

        for(String item : array.substring(1,array.length()-1).split(",")) {
            if(item.charAt(0) == '"') output.add(new JSONValue(item.substring(1,item.length()-1)));
            else {
                JSONValue value = new JSONValue(item);
                value.type = JSONValue.Type.NonString;
                output.add(value);
            }
        }

        return new JSONValue(output);

    }

}
