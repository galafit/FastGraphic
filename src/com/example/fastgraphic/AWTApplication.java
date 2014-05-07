package com.example.fastgraphic;

import com.example.fastgraphic.painters.Painter;

import java.awt.*;

/**
 * Standard Passive Rendering using AWT components
 */

public class AWTApplication extends AbstractGraphicsApplication {

        private PaintingCanvas paintingCanvas = new PaintingCanvas();

    public AWTApplication(int frameRate, Painter painter, int width, int height, Color bgColor, Color fgColor) {
        super(painter);
        Frame mainFrame = new Frame();
            setDefaultCloseOperation(mainFrame);
            mainFrame.setTitle(ApplicationType.AWT.getLabel());
            setSizeAndColors(paintingCanvas, width, height, bgColor, fgColor);
            mainFrame.add(paintingCanvas);
            mainFrame.pack();
            // place the window to the screen center
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
            startAnimation(frameRate);
        }

        public void repaintFrame() {
            paintingCanvas.repaint();
        }

    // To implement Passive Rendering we just need to override paint() method  of the Component on which we will paint
//  and put there our drawing code
        class PaintingCanvas extends Canvas {
            @Override
            public void paint(Graphics g) {
                //rendering
                painter.paint(g);
// Extensions of java.awt.Container which override paint() should always invoke super.paint() at the end to ensure that children are painted.
// As in our case, PaintingCanvas does not work as a Container and does not have children, there is no need to invoke super.paint()
            }
        }
}

