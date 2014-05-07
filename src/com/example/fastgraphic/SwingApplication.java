package com.example.fastgraphic;

import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;

/**
 * Standard Passive Rendering using SWING components
 */

public class SwingApplication extends AbstractGraphicsApplication{

    private JPanel paintingPanel = new PaintingPanel();

    public SwingApplication(int frameRate, boolean isSwingDoubleBuff, Painter painter, int width, int height, Color bgColor, Color fgColor) {
        super(painter);
        JFrame mainFrame = new JFrame();
        setDefaultCloseOperation(mainFrame);
        if(!isSwingDoubleBuff){
            // turn off Double Buffering
            mainFrame.getRootPane().setDoubleBuffered(false);
            // for reliability
            RepaintManager.currentManager(mainFrame).setDoubleBufferingEnabled(false);
        }
        else{
            // restore de default settings with double buffering enabled
            RepaintManager.currentManager(mainFrame).setDoubleBufferingEnabled(true);
        }
        mainFrame.setTitle(ApplicationType.SWING.getLabel());
        setSizeAndColors(paintingPanel, width, height, bgColor, fgColor);

// Set Opaque "true" suggests that the component itself is responsible for drawing all its points
// and does not need to call paint() methods of underlying components. It could be a significant optimization.
// So the component may be transparent only when it is really necessary, otherwise Opaque must be set true .
        paintingPanel.setOpaque(true);
        mainFrame.add(paintingPanel);
        mainFrame.pack();
        // place the window to the screen center
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        startAnimation(frameRate);
    }

    public void repaintFrame() {
        paintingPanel.repaint();
    }

 // To implement Passive Rendering we just need to put our drawing code in an overridden
 // paintComponent() method of the JComponent
    class PaintingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            //Most of the standard Swing components have their "look and feel" implemented by separate look-and-feel objects
            // (called "UI delegates") for Swing's Pluggable look and feel feature.
            // super.paintComponent(g) lets UI delegate paint first (including background filling, if the component is opaque)
            super.paintComponent(g);
            // rendering
            painter.paint(g);
        }
    }
}


