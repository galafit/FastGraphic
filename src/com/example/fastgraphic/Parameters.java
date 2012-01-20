package com.example.fastgraphic;


public class Parameters {

    private int width = 640;
    private int height = 480;
    private int frameRate = 60;
    private float frameShift = 0.5f;
    private AvailableColors bgColor = AvailableColors.BLACK;
    private AvailableColors fgColor = AvailableColors.GREEN;
    private GTool gTool = GTool.AWT;
    private BufferingType bufferingType = BufferingType.DIRECT;
    private boolean useLinePainter;
    private boolean useSinusPainter = true;
    private boolean useBgFlipPainter;
    private boolean useSlowPainter;


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

    public AvailableColors getBgColor() {
        return bgColor;
    }

    public void setBgColor(AvailableColors bgColor) {
        this.bgColor = bgColor;
    }

    public AvailableColors getFgColor() {
        return fgColor;
    }

    public void setFgColor(AvailableColors fgColor) {
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

    public boolean isUseLinePainter() {
        return useLinePainter;
    }

    public void setUseLinePainter(boolean useLinePainter) {
        this.useLinePainter = useLinePainter;
    }

    public boolean isUseSinusPainter() {
        return useSinusPainter;
    }

    public void setUseSinusPainter(boolean useSinusPainter) {
        this.useSinusPainter = useSinusPainter;
    }

    public boolean isUseBgFlipPainter() {
        return useBgFlipPainter;
    }

    public void setUseBgFlipPainter(boolean useBgFlipPainter) {
        this.useBgFlipPainter = useBgFlipPainter;
    }

    public boolean isUseSlowPainter() {
        return useSlowPainter;
    }

    public void setUseSlowPainter(boolean useSlowPainter) {
        this.useSlowPainter = useSlowPainter;
    }
}
