package com.example.fastgraphic;

/**
 * Factory to create GToolName with specified Parameters
 */
public class GToolFactory {

    public static GTool getGTool(Parameters params) {
        if (params.getGTool() == GToolName.AWT) {
            return new AWTGTool(params);
        }
        if (params.getGTool() == GToolName.SWING) {
            return new SwingGtool(params);
        }
        if (params.getGTool() == GToolName.ACTIVE_RENDERING) {
            return  new ActiveRenderingGtool(params);

        }
        throw new UnsupportedOperationException("todo ");
    }

}
