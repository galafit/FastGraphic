package com.example.fastgraphic;

import java.awt.*;

/**
 *
 */
public interface PaintingArea {
    void repaint();
    void setPreferredSize(Dimension dimension);
    void setBackground(Color color);
    void setForeground(Color color);
}
