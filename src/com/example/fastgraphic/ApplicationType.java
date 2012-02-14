package com.example.fastgraphic;

/**
 *  enum for available graphic tools
 */
public enum ApplicationType {
    AWT("AWT"),
    SWING("SWING"),
    OPEN_GL("OpenGL"),
    VLCJ("Player (VLCJ)"),
    DIRECT("Direct"),
    DOUBLE_BUFF("Double Buffering"),
    ACCELERATED_BUFF("Accelerated Buffering"),
    PAGE_FLIPPING ("Page Flipping");


    private String label;

    ApplicationType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
