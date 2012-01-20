package com.example.fastgraphic;

/**
 Enum for Buffering type
 */
public enum BufferingType {

    DIRECT("Direct"),
    DOUBLE_BUFFERING("Double Buffering"),
    PAGE_FLIPPING("Page Flipping");

    private String label;

    BufferingType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
