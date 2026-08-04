package org.py.vedge;

import java.util.HashMap;

public class Key {

    private static HashMap<String,Integer[]> keyGroups;

    public static void compileKeyGroups() {
        keyGroups = new HashMap<>();
        Settings.getFile("vedge").get("key_groups").asJSONObject().getRawHashMap().forEach((k,v) -> {
            Integer[] intArray = new Integer[v.asArray().length];
            Sparry<JSONValue> valueArray = v.asArray();
            for(int i = 0; i < valueArray.length; i++) intArray[i] = valueArray.get(i).asInteger();
            keyGroups.put(k,intArray);
        });
    }


    public int keyCode;
    public String[] keyGroupsImIn;

    public Key(int keyCode) {
        this.keyCode = keyCode;
        scanKeyGroups();
    }
    public Key() {
        scanKeyGroups();
    }
    private void scanKeyGroups() {
        Sparry<String> result = new Sparry<>();
        keyGroups.forEach((k,v) -> {
            for(Integer i : v) if(keyCode == i) result.add(k);
        });
        String[] array = new String[result.length];
        for(int i = 0; i < result.length; i++) array[i] = result.get(i);
        keyGroupsImIn = array;
    }

    public boolean inGroup(String group) {

        for(String g : keyGroupsImIn) if(group.equals(g)) return true;
        return false;

    }

    @Override
    public String toString() {
        return Integer.toString(keyCode);
    }

}