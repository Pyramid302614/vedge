package org.py.vedge;

public class Process {

    public final String prettyName;
    public final String name;
    private final Runnable execute;

    public Process(Runnable execute, String prettyName, String name) {
        this.execute = execute;
        this.prettyName = prettyName;
        this.name = name;
    }
    public void run() {
        execute.run();
    }

}
