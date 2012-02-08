package com.example.fastgraphic;

/**
 *  enum for available graphic tools
 */
public enum PaintingAreaName {
    AWT("AWT"),
    SWING("SWING"),
    OPEN_GL("OpenGL"),
    VLCJ("Player (VLCJ)"),
    DIRECT("Direct"),
    DOUBLE_BUFF("Double Buffering"),
    ACCELERATED_BUFF("Accelerated Buffering"),
    PAGE_FLIPPING ("Page Flipping"),
    FULL_SCREEN("Full Screen"),
    ACTIVE_RENDERING("Active Rendering");

    private String label;

    PaintingAreaName(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
