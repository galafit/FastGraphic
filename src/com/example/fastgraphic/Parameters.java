package com.example.fastgraphic;


import com.example.fastgraphic.painters.LinePainter;
import com.example.fastgraphic.painters.Painter;
import com.example.fastgraphic.painters.SinusPainter;
import com.example.fastgraphic.painters.SlowPainter;

import java.util.ArrayList;
import java.util.List;

public class Parameters {

    private int width = 640;
    private int height = 480;
    private int frameRate = 60;
    private float frameShift = 0.5f;
    private AvailableColors bgColor = AvailableColors.BLACK;
    private AvailableColors fgColor = AvailableColors.GREEN;
    private GToolName gToolName = GToolName.AWT;
    private BufferingType bufferingType = BufferingType.DIRECT;
    private boolean useLinePainter;
    private boolean useSinusPainter = true;
    private boolean useBgFlipPainter;
    private boolean useSlowPainter;


    public List<Painter> getActivePainters() {
        List<Painter> activePainters = new ArrayList<Painter>();
       if (isUseBgFlipPainter()) {
            activePainters.add(new BGFlipPainter());
        }
        if (isUseLinePainter()) {
            activePainters.add(new LinePainter(getFrameShift()));
        }
        if (isUseSinusPainter()) {
            activePainters.add(new SinusPainter(getFrameShift()));
        }
        if (isUseSlowPainter()) {
            activePainters.add(new SlowPainter());
        }
        return activePainters;
    }

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

    public GToolName getGTool() {
        return gToolName;
    }

    public void setGTool(GToolName gToolName) {
        this.gToolName = gToolName;
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
