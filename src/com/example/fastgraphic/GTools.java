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

    private String name;

    GTools(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
