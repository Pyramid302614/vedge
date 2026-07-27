package org.py.vedge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OS {

    public static String resolveEnvFromString(String string) {

//        System.out.println("IN: " + string);

        int[] nameAt = {1,1}; // how many characters from left , how many characters from right
        Pattern format = Pattern.compile("%.*%");

        Matcher matcher = format.matcher(string);

        while(matcher.find()) {
            String group = matcher.group();
            string = string.replaceAll(group,System.getenv(group.substring(nameAt[0],group.length()-nameAt[1])).replaceAll("\\\\","/"));
        }

//        System.out.println("OUT: " + string);

        return string;

    }

}
