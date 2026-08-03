package org.py.vedge;

public class Frame {

    private static final Sparry<Process> preFrameTimers = new Sparry<>();
    private static final Sparry<Process> postFrameTimers = new Sparry<>();

    public static void onNewFrame(Process process) {
        preFrameTimers.add(process);
    }
    public static void afterNewFrame(Process process) {
        postFrameTimers.add(process);
    }

    public static void frame() {

        preFrameTimers.forEach(Process::run);


        postFrameTimers.forEach(Process::run);

    }

}
