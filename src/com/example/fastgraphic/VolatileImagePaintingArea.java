package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;

/**
 * Active rendering using VolatileImage for double buffering
 */
public class VolatileImagePaintingArea  implements PaintingArea {
    private Parameters params;
    private Frame frame = new Frame();
    private CompositePainter painter;
    private Canvas paintingCanvas;
    private Controller controller;
    private VolatileImage vImg;

    public VolatileImagePaintingArea(Parameters params, Controller contrl) {
        controller = contrl;
        this.params = params;
        painter = new CompositePainter(params.getActivePainters());
        frame.setTitle(params.getGTool().getLabel());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
            }
        });
        paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(params.getWidth(), params.getHeight()));
        paintingCanvas.setBackground(params.getBgColor().getColor());
        paintingCanvas.setForeground(params.getFgColor().getColor());
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);
        vImg = createVolatileImage();
    }

    private GraphicsConfiguration getGraphicsConfiguration(){
        return paintingCanvas.getGraphicsConfiguration();
    }

    private VolatileImage createVolatileImage(){
         return paintingCanvas.createVolatileImage(paintingCanvas.getWidth(), paintingCanvas.getHeight());
    }

    // rendering to the Volatile Image  buffer
    void renderOffscreen() {
        do {
            if (vImg.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE)
            {
                // old vImg doesn't work with new GraphicsConfig; re-create it
                vImg = createVolatileImage();
            }
            Graphics2D g = vImg.createGraphics();
            // rendering
            Rectangle paintingRectangle = paintingCanvas.getBounds();
            g.setClip(0,0,paintingRectangle.width, paintingRectangle.height);
            g.clearRect(0,0,paintingRectangle.width,paintingRectangle.height);
            painter.paint(g);
            //
            g.dispose();
        } while (vImg.contentsLost());
    }

    void  copyOnScreen(Graphics gScreen) {
        // copying from the image (here, gScreen is the Graphics
        // object for the onscreen window)
        do {
            int returnCode = vImg.validate(getGraphicsConfiguration());
            if (returnCode == VolatileImage.IMAGE_RESTORED) {
                // Contents need to be restored
                renderOffscreen();      // restore contents
            } else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
                // old vImg doesn't work with new GraphicsConfig; re-create it
                vImg = createVolatileImage();
                renderOffscreen();
            }
            gScreen.drawImage(vImg, 0, 0, null);
        } while (vImg.contentsLost());
    }

    public void changeFrame() {
        Graphics gScreen = paintingCanvas.getGraphics();
        renderOffscreen();
        copyOnScreen(gScreen);
        Toolkit.getDefaultToolkit().sync();
    }

}
