package com.example.fastgraphic;

/**
 *  enum for available graphic tools
 */
public enum GTool {
    AWT("AWT"),
    SWING("SWING"),
    FULL_SCREEN("Full Screen"),
    OPEN_GL("OpenGL"),
    VLCJ("Player (VLCJ)");

    private String label;

    GTool(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
