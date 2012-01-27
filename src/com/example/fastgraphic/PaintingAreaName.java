package com.example.fastgraphic;

/**
 *  enum for available graphic tools
 */
public enum PaintingAreaName {
    AWT("AWT"),
    SWING("SWING"),
    FULL_SCREEN("Full Screen"),
    OPEN_GL("OpenGL"),
    VLCJ("Player (VLCJ)"),
    ACTIVE_RENDERING("Active Rendering");

    private String label;

    PaintingAreaName(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
