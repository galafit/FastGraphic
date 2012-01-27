package com.example.fastgraphic;


import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;


public class OptionsWindowTest {

    @Test
    public void paramsToControlsTest() {
        Parameters params = new Parameters();
        OptionsWindow optionsWindow = new OptionsWindow(params);
        optionsWindow.paramsToControls(params);
        optionsWindow.controlsToParams(params);
        assertEquals(params.getBgColor().getColor(), Color.BLACK);
        assertEquals(params.getBufferingType(),BufferingType.DIRECT);
        assertEquals(params.getFgColor().getColor(),Color.GREEN);
        assertEquals(params.getFrameRate(), 60);
        assertEquals(params.getFrameShift(),0,5f);
        assertEquals(params.getGTool(), PaintingAreaName.AWT);
        assertEquals(params.getHeight(),480);
        assertEquals(params.getWidth(),640);
        assertEquals(params.isUseBgFlipPainter(),false);
        assertEquals(params.isUseLinePainter(),false);
        assertEquals(params.isUseSinusPainter(),true);
        assertEquals(params.isUseSlowPainter(),false);
    }

}
