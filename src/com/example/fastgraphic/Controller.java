package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;

/**
 *
 */
public class Controller {

    Animator animator;

    public void start(Parameters params) {
        PaintingArea paintingArea = ApplicationFactory.getApplication(params, this);
        if (paintingArea!=null) {
            animator = new Animator(paintingArea, params.getFrameRate());
            animator.startAnimation();
        }
    }

    public void stopAnimation(){
       animator.stopAnimation();
    }

}
