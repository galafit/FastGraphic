package com.example.fastgraphic;

/**
 *  enum for available graphic tools
 */
public enum GTools {
    AWT("AWT"),
    SWING("SWING"),
    FULL_SCREEN("Full Screen"),
    OPEN_GL("OpenGL"),
    VLCJ("Player (VLCJ)");

    private String label;

    GTools(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
