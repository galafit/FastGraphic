package com.example.fastgraphic;


public class Parameters {

    public static final NamedColor availableColors[] = {NamedColor.black, NamedColor.green, NamedColor.red, NamedColor.gray};

    private int width = 640;
    private int height = 480;
    private int frameRate = 60;
    private float frameShift = 1;
    private NamedColor bgColor = availableColors[0];
    private NamedColor fgColor = availableColors[1];
    private GTool gTool = GTool.AWT;
    private BufferingType bufferingType = BufferingType.DIRECT;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public float getFrameShift() {
        return frameShift;
    }

    public void setFrameShift(float frameShift) {
        this.frameShift = frameShift;
    }

    public NamedColor getBgColor() {
        return bgColor;
    }

    public void setBgColor(NamedColor bgColor) {
        this.bgColor = bgColor;
    }

    public NamedColor getFgColor() {
        return fgColor;
    }

    public void setFgColor(NamedColor fgColor) {
        this.fgColor = fgColor;
    }

    public GTool getGTool() {
        return gTool;
    }

    public void setGTool(GTool gTool) {
        this.gTool = gTool;
    }

    public BufferingType getBufferingType() {
        return bufferingType;
    }

    public void setBufferingType(BufferingType bufferingType) {
        this.bufferingType = bufferingType;
    }
}
