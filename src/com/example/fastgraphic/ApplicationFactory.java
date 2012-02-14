package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import java.awt.*;

/**
 * Factory to create Application with specified Parameters
 */
public class ApplicationFactory {

    public static PaintingArea getApplication(Parameters params, Controller controller) {
        if (params.getApplicationType() == ApplicationType.AWT) {
            return new AWTApplication(params.getApplicationType().getLabel(),params.getWidth(), params.getHeight(),
                          params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getApplicationType()  == ApplicationType.SWING) {
            return new SwingApplication(params.isSwingDoubleBuff(),params.getApplicationType().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getApplicationType() == ApplicationType.DOUBLE_BUFF) {
            return new DoubleBufferringApplication(params.getApplicationType().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getApplicationType() == ApplicationType.ACCELERATED_BUFF) {
            return new AcceleratedDoubleBufferingApplication(params.getApplicationType().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getApplicationType() == ApplicationType.DIRECT) {
            return new DirectPaintingApplication(params.getApplicationType().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getApplicationType() == ApplicationType.PAGE_FLIPPING) {
            return new PageFlippingApplication(params.isFullScreen(),params.getApplicationType().getLabel(),params.getWidth(), params.getHeight(),
                    params.getBgColor().getColor(), params.getFgColor().getColor(),new CompositePainter(params.getActivePainters()),controller);
        }
        if (params.getApplicationType() == ApplicationType.OPEN_GL) {
            new JogleApplication(params.getWidth(),params.getHeight(),params.getFrameRate());
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
