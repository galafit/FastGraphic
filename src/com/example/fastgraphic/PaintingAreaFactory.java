package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import java.awt.*;

/**
 * Factory to create PaintingAreaName with specified Parameters
 */
public class PaintingAreaFactory {

    public static PaintingArea getGTool(Parameters params, Controller controller) {
        if (params.getGTool() == PaintingAreaName.AWT) {
            return new AWTPaintingArea(params.getGTool().getLabel(),params.getWidth(), params.getHeight(),
                          params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getGTool() == PaintingAreaName.SWING) {
            return new SwingPaintingArea(params.isSwingDoubleBuff(),params.getGTool().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getGTool() == PaintingAreaName.DOUBLE_BUFF) {
            return new DoubleBufferringPaintingArea(params.getGTool().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getGTool() == PaintingAreaName.ACCELERATED_BUFF) {
            return new VolatileImagePaintingArea(params.getGTool().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getGTool() == PaintingAreaName.DIRECT) {
            return new DirectPaintingArea(params.getGTool().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getGTool() == PaintingAreaName.PAGE_FLIPPING) {
            return new PageFlippingPaintingArea(params.isFullScreen(),params.getGTool().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getGTool() == PaintingAreaName.OPEN_GL) {
            new JogleExample(params.getWidth(),params.getHeight(),params.getFrameRate());
            return null;
        }
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
