package com.example.fastgraphic;

import java.awt.*;

/**
 *
 */
public enum AvailableColors {
    BLACK(Color.BLACK),
    GREEN(Color.GREEN),
    GRAY(Color.GRAY),
    RED(Color.RED);
    private Color color;

    AvailableColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
