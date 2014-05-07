package com.example.fastgraphic;

import com.example.fastgraphic.painters.Painter;

import java.awt.*;


/**
 * Direct Active Rendering on the Screen without Buffering
 */

public class DirectPaintingApplication extends AbstractGraphicsApplication {

    private Canvas paintingCanvas = new Canvas();
    private Rectangle paintingClip; //Painting Clip for Graphics object should be set manually for Active Rendering

    public DirectPaintingApplication(int frameRate, Painter painter, int width, int height,Color bgColor, Color fgColor){
            super(painter);
            paintingClip = new Rectangle(0,0,width,height);
            Frame mainFrame = new Frame();
            setDefaultCloseOperation(mainFrame);
            mainFrame.setTitle(ApplicationType.DIRECT.getLabel());
            setSizeAndColors(paintingCanvas, width, height, bgColor, fgColor);
            mainFrame.add(paintingCanvas);
            mainFrame.pack();
            // place the window to the screen center
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
            startAnimation(frameRate);
        }

    public void repaintFrame() {
        //get Graphics object for the onscreen window
        Graphics screenG =  paintingCanvas.getGraphics();
        // Rendering
        screenG.setClip(paintingClip);
        screenG.clearRect(paintingClip.x, paintingClip.y, paintingClip.width, paintingClip.height);
        painter.paint(screenG);
        // Dispose the graphics
        screenG.dispose();
        // The call to Toolkit.sync( ) after drawImage( ) ensures that the display is promptly updated.
        // This is required for Linux, which does not automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a " tearing " effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();
    }


}
