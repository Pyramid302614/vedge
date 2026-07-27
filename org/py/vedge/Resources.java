package org.py.vedge;

import java.io.*;
import java.util.Scanner;

public class Resources {

    public static String resolve(String path) {

        // starts with : -> appdata root
        // starts with / -> project root

        return switch(path.charAt(0)) {
            case ';' -> System.getProperty("user.dir") + path.substring(1);
            case ':' -> OS.resolveEnvFromString(JSONFile.file("vedge_main").get("app_data_path").asString()) + path.substring(1);
            default -> OS.resolveEnvFromString(path);
        };

    }


    public static File asFile(String path) throws IOException {
        return new File(resolve(path));
    }
    public static File asFile_safe(String path) {
        try {
            return asFile(path);
        } catch(IOException e) {
            ErrorHandler.silent(e);
            return null;
        }
    }

    public static InputStream asInputStream(String path) throws IOException {
        return Resources.class.getResourceAsStream(resolve(path));
    }

    public static InputStream asInputStream_safe(String path) {
        try {
            return asInputStream(path);
        } catch(IOException e) {
            ErrorHandler.silent(e);
            return null;
        }
    }

    public static String getContents(String path) throws IOException {
        Scanner sc = new Scanner(Resources.asFile(path));
        StringBuilder raw = new StringBuilder();
        while(sc.hasNext()) {
            raw.append(sc.nextLine()).append("\n");
        }
        sc.close();
        return raw.toString();
    }
    public static String getContents_safe(String path) {
        try {
            return getContents(path);
        } catch(IOException e) {
            ErrorHandler.silent(e);
            return null;
        }
    }

    public static void overrideContents(String path, String contents) throws IOException {

        if(!Resources.asFile(path).delete()) {
            ErrorHandler.silent("Failed to delete original \"" + path + "\"");
            return;
        }
        FileWriter fw = new FileWriter(Resources.asFile(path),false);
        fw.write(contents);
        fw.close();

    }
    public static void overrideContents_safe(String path, String contents) {
        try {
            overrideContents(path,contents);
        } catch(IOException e) {
            ErrorHandler.silent(e);
        }
    }

    public static void appendToContents(String path, String contents) throws IOException {

        FileWriter fw = new FileWriter(Resources.asFile(path),false);
        fw.write(contents);
        fw.close();

    }
    public static void appendToContents_safe(String path, String contents) {
        try {
            appendToContents(path,contents);
        } catch(IOException e) {
            ErrorHandler.silent(e);
        }
    }

    public static BitCluster getRawContents(String path) throws IOException {

        FileInputStream input = new FileInputStream(asFile(path));

        byte[] all = input.readAllBytes();

        input.close();

        return BitCluster.fromBytes(all);

    }

    public static BitCluster getRawContents_safe(String path) {
        try {
            return getRawContents(path);
        } catch(IOException e) {
            ErrorHandler.silent(e);
            return null;
        }
    }


    public static void createFile(String path) throws IOException {

        asFile(path).createNewFile();

    }
    public static void createFile_safe(String path) {
        try {
            createFile(path);
        } catch(IOException e) {
            ErrorHandler.silent(e);
        }
    }

    public static void createDirectory(String path) throws IOException {

        asFile(path).mkdir();

    }
    public static void createDirectory_safe(String path) {
        try {
            createDirectory(path);
        } catch(IOException e) {
            ErrorHandler.silent(e);
        }
    }




}
