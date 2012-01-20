package com.example.fastgraphic;


public class Parameters {

    public static final NamedColor availableColors[] = {NamedColor.black, NamedColor.green, NamedColor.red, NamedColor.gray};

    private int width = 640;
    private int height = 480;
    private int frameRate = 60;
    private float frameShift = 0.5f;
    private NamedColor bgColor = availableColors[0];
    private NamedColor fgColor = availableColors[1];
    private GTool gTool = GTool.OPEN_GL;
    private BufferingType bufferingType = BufferingType.DIRECT;
    private boolean useLinePainter;
    private boolean useSinusPinter;
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

    public boolean isUseLinePainter() {
        return useLinePainter;
    }

    public void setUseLinePainter(boolean useLinePainter) {
        this.useLinePainter = useLinePainter;
    }

    public boolean isUseSinusPinter() {
        return useSinusPinter;
    }

    public void setUseSinusPinter(boolean useSinusPinter) {
        this.useSinusPinter = useSinusPinter;
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
