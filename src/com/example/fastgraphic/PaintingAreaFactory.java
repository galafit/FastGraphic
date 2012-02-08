package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.DoubleBufferingFullScreen;

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
        if (params.getGTool() == PaintingAreaName.FULL_SCREEN) {
            return new FullScreenPaintingArea(params, controller);
        }
        if (params.getGTool() == PaintingAreaName.OPEN_GL) {
            new JogleExample(params.getWidth(),params.getHeight(),params.getFrameRate());
            return null;
        }
        if (params.getGTool() == PaintingAreaName.VLCJ) {
            return new ActiveRenderingDoubleBufferring(params, controller);

        }
        /* if (params.getGTool() == PaintingAreaName.VLCJ) {
            return new VolatileImagePaintingArea(params, controller);

        }  */
       /* if (params.getGTool() == PaintingAreaName.VLCJ) {
            try {
                new VlcjExample(params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }   */
        throw new UnsupportedOperationException("todo ");
    }

}
