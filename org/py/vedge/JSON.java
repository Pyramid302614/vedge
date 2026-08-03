package org.py.vedge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            }

            output.append(i != object.length() - 1 ? ( ",\n" + currentIndent + space ) : ( "\n" + currentIndent + "}" ));

        }

        return object.length() > 0 ? output.toString() : "{}";

    }

    public static JSONObject parse(String string) {

        if(string == null) return new JSONObject();

        return nestedParse(string.replaceAll(" ","").replaceAll("\n",""));

    }

    // STRING MUST BE WHITESPACE-LESS
    private static JSONObject nestedParse(String string) {

        System.out.println(string);

        JSONObject value = new JSONObject();

        Pattern itemsPattern = Pattern.compile("[^,]+:\\{[^}]+}|[^,]+");
        Matcher matcher = itemsPattern.matcher(string.substring(1,string.length()-1));
        while(matcher.find()) {
            String item = matcher.group();
            if(item.split(":").length >= 2) {
                String propertyName = item.split(":")[0]
                        .replaceAll("\\\\\"","&&&&")
                        .replaceAll("\"","")
                        .replaceAll("&&&&","\"");
                if(item.split(":")[1].charAt(0) == '{') {
//                    System.out.println("OBJECT: " + propertyName + " | ITEM: " + item.substring(item.split(":")[0].length()+1));
                    value.set(propertyName, nestedParse(item.substring(item.split(":")[0].length()+1)));
                }
                else {
                    String valueContent = item.split(":")[1]
                            .replaceAll("\\\\\"","&&&&")
                            .replaceAll("\"","")
                            .replaceAll("&&&&","\"");
//                    System.out.println("VALUE: " + propertyName + " | ITEM : " + valueContent);
                    value.set(propertyName,valueContent);
                }
            }
        }

        return value;



    }

    public static JSONObject Parse(String string) {

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

            if(type == JSONValue.Type.JSONObject && c == '{') scope++;
            else if(type == JSONValue.Type.JSONObject && c == '}') scope--;

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
                        JSONValue value = type == JSONValue.Type.JSONObject ? new JSONValue(Parse(buffer)) : new JSONValue(buffer);
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
                        default:
                            type = JSONValue.Type.NonString;
                            break;
                    }
                    break;

                case ',':
                    write = true;
                    mode = false;
                    buffer = "";
                    break;


            }

        }

        JSONValue value = type == JSONValue.Type.JSONObject ? new JSONValue(Parse(buffer)) : new JSONValue(buffer);
        value.type = type;
        obj.set(propertyBuffer,value);

        return obj;

    }

}
