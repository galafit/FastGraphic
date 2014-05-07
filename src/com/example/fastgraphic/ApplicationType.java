package com.example.fastgraphic;

/**
 *  enum for available graphics applications
 */
public enum ApplicationType {
    AWT("AWT"),
    SWING("SWING"),
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
