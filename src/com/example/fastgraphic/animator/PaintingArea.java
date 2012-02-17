package com.example.fastgraphic.animator;

/**
 *   Interface used by the class Animator to trigger animation (i.e. to invoke repaintFrame() method in the loop)
 */

public interface PaintingArea {
    void repaintFrame();
}
