package com.example.fastgraphic;

import java.awt.*;

/**
 *
 */
public interface PaintingArea {
    void addPainter(Painter painter);
    void repaint();
    void setPreferredSize(Dimension dimension);
    void setBackground(Color color);
    void setForeground(Color color);
}
