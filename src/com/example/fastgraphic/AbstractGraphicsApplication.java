package com.example.fastgraphic;

import com.example.fastgraphic.animation.AnimationTimer;
import com.example.fastgraphic.animation.RedrawableWindow;
import com.example.fastgraphic.painters.CompoundPainter;
import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 */
public abstract class AbstractGraphicsApplication implements RedrawableWindow {

    private final AnimationTimer animationTimer = new AnimationTimer();
    protected Painter painter;  // implements the painting routine


    public AbstractGraphicsApplication(Painter painter) {
        animationTimer.addPaintingArea(this);
        this.painter = painter;
    }

    protected void setDefaultCloseOperation(final Frame mainFrame) {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopAnimation();
                mainFrame.dispose();
                //create a window with the information about a real average FrameRate
                JOptionPane.showMessageDialog(null, getReport());
            }
        });
        mainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                stopAnimation();
                mainFrame.dispose();
                //create a window with the information about a real average FrameRate
                JOptionPane.showMessageDialog(null, getReport());
            }
        });
    }

    public void startAnimation(int frameRate) {
        animationTimer.startAnimation(frameRate);
    }

    public void stopAnimation() {
        animationTimer.stopAnimation();
    }

    public String getReport() {

        if (painter instanceof CompoundPainter) {
            CompoundPainter compoundPainter = (CompoundPainter) painter;
            return compoundPainter.getFrameReport();
        }else {
            return "Frame Rate Report is unavailable";
        }
    }

    protected void setSizeAndColors(Component paintingArea, int width, int height, Color bgColor, Color fgColor) {
        Dimension dimension = new Dimension(width, height);
        paintingArea.setPreferredSize(dimension);//work with layout managers
        paintingArea.setSize(dimension);//work without layout managers
        paintingArea.setBackground(bgColor);
        paintingArea.setForeground(fgColor);
    }

    public abstract void repaintFrame();
}
