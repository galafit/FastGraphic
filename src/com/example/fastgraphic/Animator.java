package com.example.fastgraphic;

public class Animator {
    private int frameRate;
    private PaintingArea paintingArea;
    private long cycleTime;
    private boolean isRunning = true;

    public Animator(PaintingArea paintingArea, int frameRate) {
        this.frameRate = frameRate;
        this.paintingArea = paintingArea;
    }

    public void startAnimation() {
        Runnable r = new Runnable(){
            public void run() {
                while (isRunning) {
                    paintingArea.repaint();
                    synchFramerate();
                }
            }
        };
        new Thread(r).start();
    }

    public void stopAnimation(){
        isRunning = false;
    }

    private void synchFramerate() {
        int delay = 1000 / frameRate;
        cycleTime = cycleTime + delay;
        long difference = cycleTime - System.currentTimeMillis();
        try {
            Thread.sleep(Math.max(5, difference));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


