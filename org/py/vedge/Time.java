package org.py.vedge;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time {

    public static long nowMs() {

        return System.currentTimeMillis();

    }

    // y = year
    // M = month (30 day approx)
    // w = week
    // d = day
    // h = hour
    // m = minute
    // s = second
    // ms = millisecond
    // us = microsecond
    // ns = nanosecond

    private static final HashMap<String,Double> units = new HashMap<>();

    public static void initializeUnits() {
        units.put("ns",0.0000001);
        units.put("us",0.0001);
        units.put("ms",1.0);
        units.put("s",1_000.0);
        units.put("m",60_000.0);
        units.put("h",3_600_000.0);
        units.put("d",86_400_000.0);
        units.put("w",4_514_400_000.0);
        units.put("M",2_628_000_000.0);
        units.put("y",31_536_000_000.0);
    }

    public static long msFromString(String string) {

        AtomicLong output = new AtomicLong();
        units.keySet().forEach(i -> {
            Matcher matcher = Pattern.compile("\\d+ *"+i+"( |$)").matcher(string);
            if(matcher.find()) output.addAndGet((long)(Double.parseDouble(matcher.group().replaceAll(i,""))*units.get(i)));
        });
        return output.get();

    }

}
