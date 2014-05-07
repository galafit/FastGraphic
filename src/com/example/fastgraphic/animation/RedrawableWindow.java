package com.example.fastgraphic.animation;

/**
 *   Interface used by the class AnimationTimer to trigger animation (i.e. to invoke repaintFrame() method in the loop)
 */

public interface RedrawableWindow {
    void repaintFrame();
}
