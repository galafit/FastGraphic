package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.painters.CompoundPainter;
import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 *
 *  Active rendering using BufferStrategy class.
 *  BufferStrategy chooses the best buffering method based on the capabilities of the system
 *  and if possible uses hardware-accelerated memory and realizes Page Flipping Strategy
 *
 *  Also supposed that it waits on the monitor refresh to finish before performing any page flip to avoid tearing
 *  (when part of the old buffer can display at the same time as part of the new buffer)
 *
 *  Application can work in two modes - Full Screen Mode or Window Mode
 *
 *   http://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html
 */

public class PageFlippingApplication implements PaintingArea {

    // BufferStrategy can use more then 2 Buffers but 2 is the most common
    public static final int TWO_BUFFERS = 2;
    private GraphicsDevice screenDevice;
    private BufferStrategy bufferStrategy;
    private Rectangle paintingClip;
    private Frame frame;
    private Painter painter;  // implements all painting routine
    private Animator animator; // that will invoke repaintFrame() method in loop


    public PageFlippingApplication(boolean isFullScreen,  Painter painter, int width, int height, Color bgColor, Color fgColor) {
        screenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.painter = painter;
        paintingClip = new Rectangle(0,0,width,height);
        frame = new Frame();
        frame.setSize(new Dimension(width,height));
        frame.setBackground(bgColor);
        frame.setForeground(fgColor);
        // turn off paint events dispatched from the operating system since we are doing active rendering
        frame.setIgnoreRepaint(true);
        // Full-screen exclusive applications should not be resizable,
        // since resizing a full-screen application can cause unpredictable (or possibly dangerous) behavior.
        // In windowed mode it is also better to use a fixed size Window
        frame.setResizable(false);
        // Most full-screen exclusive applications are better suited to use undecorated windows.
        frame.setUndecorated(true);
        // Add Kay Listener to exit from the Full Screen Mode on pressing any button
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                animator.stopAnimation();
                frame.dispose();
                // in case of FullScreen Mode switch back from Full Screen to Window mode
                screenDevice.setFullScreenWindow(null);
                //create a Window with information about real average FrameRate
                JOptionPane.showMessageDialog(frame, animator.getFrameRateReport());
            }
        });
        frame.setVisible(true);
        // put the window at the screen center
        frame.setLocationRelativeTo(null);

        // createBufferStrategy have to be done AFTER  the element (frame or canvas) is set visible!
        frame.createBufferStrategy(TWO_BUFFERS);
        bufferStrategy = frame.getBufferStrategy();

        if (isFullScreen) {
            enterFullScreenMode();
        }
    }

    private void enterFullScreenMode(){
        if(screenDevice.isFullScreenSupported()) {
            paintingClip = new Rectangle(0, 0, screenDevice.getDisplayMode().getWidth(), screenDevice.getDisplayMode().getHeight());

            screenDevice.setFullScreenWindow(frame);
        } else {
            JOptionPane.showMessageDialog(frame, "Full Screen Exclusive Mode is NOT Supported");
        }
    }

    public void setAnimator(Animator animator){
        this.animator = animator;
    }

    /**
     * Since the buffers in a Buffer Strategy are usually of the type VolatileImage, they may become lost.
     */
    public void repaintFrame() {
        do {
            // Get a new graphics context every time through the loop
            // to make sure the strategy is validated
            Graphics graphics = bufferStrategy.getDrawGraphics();
            graphics.setClip(paintingClip);
            graphics.clearRect(paintingClip.x, paintingClip.y, paintingClip.width, paintingClip.height);
            // Rendering
            painter.paint(graphics);
            // Display the back buffer
            bufferStrategy.show();
            // Dispose the graphics
            graphics.dispose();
            // Repeat the rendering if the drawing buffer contents were lost or restored
        } while (bufferStrategy.contentsLost() || bufferStrategy.contentsRestored());

        // The call to Toolkit.sync( ) after drawImage( ) ensures that the display is promptly updated.
        // This is required for Linux, which doesn't automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a " tearing " effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();
    }

}
