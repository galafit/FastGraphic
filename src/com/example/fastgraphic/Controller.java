package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 */
public class Controller {

    Animator animator;

    public void start(Parameters params) {
        PaintingArea paintingArea = GToolFactory.getGTool(params,this);
        animator = new Animator(paintingArea, params.getFrameRate());
        animator.startAnimation();
    }

    public void stopAnimation(){
       animator.stopAnimation();
    }

}
