package com.example.fastgraphic.animator;


/**
 *
 * Class contains PaintingArea and have two main methods startAnimation and stopAnimation
 * startAnimation() create new thread where method PaintingArea.repaintFrame() invoked in the loop with given FrameRate
 * stopAnimation() stop that Thread.
 *
 */


public class Animator {
    private PaintingArea paintingArea;

    private final int MINIMUM_DELAY = 5; // milliseconds. To give other threads a chance to execute
    private final int THOUSAND = 1000; // to calculate delay between frames in milliseconds

    private int frameRate; //per second
    private int delay; // milliseconds. delay between frames. Delay is more convenient then FrameRate for calculations
    private long startTime; // the time when the Animator was started
    private long nextFrameTime; // time to paint next frame
    private boolean isRunning = true;
    private long iterationCount; // number of iteration made by the Animator
    private String frameRateReport;

    public  Animator(int frameRate) {
        this.frameRate = frameRate;
        delay = THOUSAND/frameRate;
    }

    public void startAnimation() {
        startTime = System.currentTimeMillis();
        nextFrameTime = startTime;
//  Create new Thread that repaints frames until is_Running==true.
        Runnable r = new Runnable(){
            public void run() {
                while (isRunning) {
                    paintingArea.repaintFrame();
                    iterationCount++;
                    waitNextFrame();
                }
            }
        };
        new Thread(r).start();
    }

    public void stopAnimation(){
        isRunning = false;
        int averageFrameRate = (int) (iterationCount *THOUSAND/(System.currentTimeMillis() - startTime));
        frameRateReport = "FrameRate was set= "+frameRate+"\n\nReal Average FrameRate = "+averageFrameRate;

    }

    private void waitNextFrame() {
        nextFrameTime+=delay;
        long waitTime =  nextFrameTime - System.currentTimeMillis();
        try {
            // wait till the next repaint. MINIMUM_DELAY gives other threads a chance to execute.
            Thread.sleep(Math.max(waitTime, MINIMUM_DELAY));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addPaintingArea (PaintingArea paintingArea){
        this.paintingArea = paintingArea;
    }
    
    public String getFrameRateReport(){
        return frameRateReport;
    }
}


