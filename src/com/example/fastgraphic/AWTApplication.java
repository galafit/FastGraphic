package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Standard Passive rendering using AWT components
 */

public class AWTApplication implements PaintingArea {

        private PaintingCanvas paintingCanvas;  
        private Painter painter;  // implements all painting routine
        private Animator animator; // that will invoke repaintFrame() method in loop

        public AWTApplication(Painter painter, int width, int height, Color bgColor, Color fgColor) {
            this.painter = painter;
            final Frame frame = new Frame();  // Application Main Frame
            frame.setTitle(ApplicationType.AWT.getLabel());
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
            paintingCanvas = new PaintingCanvas();
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
            paintingCanvas.repaint();
        }

// To implements Passive Rendering we just need to override paint() method  of the Component on which we will paint
//  and put there all our drawing code
        class PaintingCanvas extends Canvas {
            @Override
            public void paint(Graphics g) {
                //rendering
                painter.paint(g);
// Extensions of java.awt.Container which override paint() should always invoke super.paint() at the end to ensure children are painted.
// As in our case  PaintingCanvas don´t work as a Container and don´t have children  there is no need to invoke super.paint()
            }
        }
}

