package org.py.vedge;

import java.util.Arrays;
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
            }

            output.append(i != object.length() - 1 ? ( ",\n" + currentIndent + space ) : ( "\n" + currentIndent + "}" ));

        }

        return output.toString();

    }

    public static JSONObject parse(String string) {

        if(string == null) return new JSONObject();

        return nestedParse(string.replaceAll(" ","").replaceAll("\n",""));

    }

    // STRING MUST BE WHITESPACE-LESS
    private static JSONObject nestedParse(String string) {

        JSONObject value = new JSONObject();

        Pattern itemsPattern = Pattern.compile("[^,]+:\\{.+}|[^,]+");
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

}
