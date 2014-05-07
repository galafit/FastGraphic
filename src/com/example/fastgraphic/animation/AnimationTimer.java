package com.example.fastgraphic.animation;


/**
 *  Class that repaints frames in a loop with given frame rate.
 *
 * It has RedrawableWindow (where frames are repaint) and two control methods:
 * startAnimation() creates new Thread in which the method RedrawableWindow.repaintFrame() called in the loop with given FrameRate.
 * stopAnimation() stops this Thread.
 */


public class AnimationTimer {
    private RedrawableWindow redrawableWindow;

    private final int MINIMUM_DELAY = 5; // min delay in milliseconds. To allow other threads to run


    private int delay; // delay between frames in milliseconds. Delay is more convenient than FrameRate for calculations
    private long startTime; // the time when the AnimationTimer started
    private long nextFrameTime; // time to paint next frame
    private boolean isRunning = true;

    public void startAnimation(int frameRate) {
        delay = 1000/frameRate;
        startTime = System.currentTimeMillis();
        nextFrameTime = startTime;
//  Create new Thread that repaints frames while is_Running==true.
        Runnable r = new Runnable(){
            public void run() {
                while (isRunning) {
                    redrawableWindow.repaintFrame();
                    waitNextFrame();
                }
            }
        };
        new Thread(r).start();
    }

    public void stopAnimation(){
        isRunning = false;
    }

    private void waitNextFrame() {
        nextFrameTime+=delay;
        long waitTime =  nextFrameTime - System.currentTimeMillis();
        try {
            // wait till next repaint. MINIMUM_DELAY allows other threads to run.
            Thread.sleep(Math.max(waitTime, MINIMUM_DELAY));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addPaintingArea (RedrawableWindow redrawableWindow){
        this.redrawableWindow = redrawableWindow;
    }
}


