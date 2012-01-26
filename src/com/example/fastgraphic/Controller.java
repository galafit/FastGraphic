package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 */
public class Controller {


    public void start(Parameters params) {
        PaintingArea paintingArea = GToolFactory.getGTool(params);
        final Animator animator = new Animator(paintingArea, params.getFrameRate());
        paintingArea.getMainWindow().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stopAnimation();
            }
        });
        animator.startAnimation();
    }

}
