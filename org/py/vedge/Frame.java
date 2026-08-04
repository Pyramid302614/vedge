package org.py.vedge;

public class Frame {

    private static final Sparry<Process> preFrameTimers = new Sparry<>();
    private static final Sparry<Process> postFrameTimers = new Sparry<>();

    private static Process[] frameTimers = new Process[0];

    public static void onNewFrame(Process process) {
        preFrameTimers.add(process);
    }
    public static void afterNewFrame(Process process) {
        postFrameTimers.add(process);
    }
    public static void frameProcesses(Process... processes) {
        frameTimers = processes;
    }

    public static void frame() {

        preFrameTimers.forEach(Process::run);

        for(Process process : frameTimers) process.run();

        postFrameTimers.forEach(Process::run);

    }

}
