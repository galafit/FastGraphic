package com.example.fastgraphic;


import javax.swing.*;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGtool implements GTool {
    protected Parameters params;
    protected final PaintingArea paintingArea = getPaintingArea();
    protected Frame frame = getFrame();
    protected CompositePainter painter;
    private Animator animator;


    protected abstract Frame getFrame();

    protected abstract PaintingArea getPaintingArea();



    public AbstractGtool(Parameters params) {
        this.params = params;
        painter = new CompositePainter(params);
        frame.setTitle(params.getGTool().getLabel());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                animator.stopAnimation();
            }
        });
        paintingArea.setPreferredSize(new Dimension(params.getWidth(), params.getHeight()));
        paintingArea.setBackground(params.getBgColor().getColor());
        paintingArea.setForeground(params.getFgColor().getColor());
        frame.pack();
        frame.setVisible(true);
        animator = new Animator(paintingArea, params.getFrameRate());
    }

    public void startAnimation(){
        animator.startAnimation();
    }
}
