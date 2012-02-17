package com.example.fastgraphic.painters;

import java.awt.*;

/**
 *  A painting delegate. The Painter interface defines exactly one method, paint.
 *  It is used to  separate the drawing code from the component.
 *  Painters are just encapsulated Java2D drawing code and make it fairly trivial to reuse existing
 *  Painters or to combine them together.
 *
 */
public interface Painter {
    public void paint(Graphics g);
}
