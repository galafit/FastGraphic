package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompoundPainter;
import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Direct Active rendering on the Screen without Buffering
 */

public class DirectPaintingApplication implements PaintingArea {

    private Canvas paintingCanvas;
    private Rectangle paintingClip; //Painting Clip for Graphics object should be set manually for Active Rendering
    private Painter painter;  // implements all painting routine
    private Animator animator; // that will invoke repaintFrame() method in loop

    public DirectPaintingApplication(Painter painter, int width, int height,Color bgColor, Color fgColor){
            this.painter = painter;
            paintingClip = new Rectangle(0,0,width,height);
            final Frame frame = new Frame();  // Application Main Frame
            frame.setTitle(ApplicationType.DIRECT.getLabel());
            // add Window Listener to the frame which will stop animation and dispose the frame on closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    animator.stopAnimation();
                    frame.dispose();
                    //create a Window with information about real average FrameRate
                    JOptionPane.showMessageDialog(frame, animator.getFrameRateReport());
                }
            });
            paintingCanvas = new Canvas();
            paintingCanvas.setPreferredSize(new Dimension(width, height));
            paintingCanvas.setBackground(bgColor);
            paintingCanvas.setForeground(fgColor);
            frame.add(paintingCanvas);
            frame.pack();
            frame.setVisible(true);
            // put the window at the screen center
            frame.setLocationRelativeTo(null);
        }

    public void setAnimator(Animator animator){
        this.animator = animator;
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
        // This is required for Linux, which doesn't automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a " tearing " effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();
    }
}
