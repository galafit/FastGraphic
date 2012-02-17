package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Standard Passive rendering using SWING components
 */

public class SwingApplication implements PaintingArea {

    private JFrame frame; // Application Main Frame
    private JPanel paintingPanel;
    private Painter painter;  // implements all painting routine
    private Animator animator; // that will invoke repaintFrame() method in loop

    public SwingApplication(boolean isSwingDoubleBuff, Painter painter, int width, int height, Color bgColor, Color fgColor) {
        this.painter = painter;
        frame = new JFrame();
        if(!isSwingDoubleBuff){
            // turn off Double Buffering
            frame.getRootPane().setDoubleBuffered(false);
            //just for the case
            RepaintManager.currentManager(frame).setDoubleBufferingEnabled(false);
        }

        frame.setTitle(ApplicationType.SWING.getLabel());
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
        paintingPanel = new PaintingPanel();
        paintingPanel.setPreferredSize(new Dimension(width, height));
        paintingPanel.setBackground(bgColor);
        paintingPanel.setForeground(fgColor);
// Set Opaque "true" suggests that the component itself is responsible for drawing of all its points
// and don´t need to call paint() methods of underlying components. It could be a significant optimization
// So the component may be transparent only when it is really necessary, otherwise Opaque must be set true .
        paintingPanel.setOpaque(true);
        frame.add(paintingPanel);
        frame.pack();
        frame.setVisible(true);
        // put the window at the screen center
        frame.setLocationRelativeTo(null);
    }

    public void setAnimator(Animator animator){
        this.animator = animator;
    }

    public void repaintFrame() {
        paintingPanel.repaint();
    }

 // To implements Passive Rendering we just need to put all our drawing code in an overridden
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


