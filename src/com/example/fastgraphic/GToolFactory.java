package com.example.fastgraphic;

/**
 * Factory to create GToolName with specified Parameters
 */
public class GToolFactory {

    public static AbstractGtool getGTool(Parameters params) {
        if (params.getGTool() == GToolName.AWT) {
            return new AWTGTool(params);
        }
        if (params.getGTool() == GToolName.SWING) {
            return new SwingGtool(params);
        }
        throw new UnsupportedOperationException("todo ");
    }

}
