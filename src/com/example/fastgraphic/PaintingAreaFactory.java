package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;

/**
 * Factory to create GToolName with specified Parameters
 */
public class PaintingAreaFactory {

    public static PaintingArea getGTool(Parameters params,Controller controller) {
        if (params.getGTool() == GToolName.AWT) {
            return new AWTPaintingArea(params,controller);
        }
        if (params.getGTool() == GToolName.SWING) {
            return new SwingPaintingArea(params, controller);
        }
        if (params.getGTool() == GToolName.ACTIVE_RENDERING) {
            throw new UnsupportedOperationException("todo");
        }
        throw new UnsupportedOperationException("todo ");
    }

}
