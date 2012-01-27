package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;

/**
 * Factory to create PaintingAreaName with specified Parameters
 */
public class PaintingAreaFactory {

    public static PaintingArea getGTool(Parameters params,Controller controller) {
        if (params.getGTool() == PaintingAreaName.AWT) {
            return new AWTPaintingArea(params,controller);
        }
        if (params.getGTool() == PaintingAreaName.SWING) {
            return new SwingPaintingArea(params, controller);
        }
        if (params.getGTool() == PaintingAreaName.ACTIVE_RENDERING) {
            return new ActiveRenderingPaintingArea(params, controller);
        }
        if (params.getGTool() == PaintingAreaName.OPEN_GL) {
            new JogleExample(params.getWidth(),params.getHeight(),params.getFrameRate());
            return null;
        }
        throw new UnsupportedOperationException("todo ");
    }

}
