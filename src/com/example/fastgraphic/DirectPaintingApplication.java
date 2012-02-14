package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Direct Active rendering on the Screen without Buffering
 */

public class DirectPaintingApplication implements PaintingArea {

    private Frame frame;
    private CompositePainter painter;
    private Controller controller;
    private Canvas paintingCanvas;
    private Rectangle paintingClip;

    public DirectPaintingApplication(String title, int width, int height,
                                     Color bgColor, Color fgColor, CompositePainter painter, Controller contrl) {
        controller = contrl;
        this.painter = painter;
        paintingClip = new Rectangle(0,0,width,height);
        frame = new Frame();
        frame.setTitle(title);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
            }
        });
        paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(width, height));
        paintingCanvas.setBackground(bgColor);
        paintingCanvas.setForeground(fgColor);
// turn off paint events dispatched from the operating system since we are doing active rendering
        frame.setIgnoreRepaint(true);
// for simplicity we use  a fixed size Frame
        frame.setResizable(false);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void changeFrame() {
        //get Graphics object for the onscreen window
        Graphics screenG =  paintingCanvas.getGraphics();
        // Rendering
        screenG.setClip(paintingClip);
        screenG.clearRect(paintingClip.x, paintingClip.y, paintingClip.width, paintingClip.height);
        painter.paint(screenG);
        // Dispose the graphics
        screenG.dispose();
// The call to “Toolkit.getDefaultToolkit().sync()” exists to fix event queue problems on Linux
// On many systems, this method does nothing.
        Toolkit.getDefaultToolkit().sync();
    }
}
