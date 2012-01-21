package com.example.fastgraphic;

/**
 * Factory to create GToolName with specified Parameters
 */
public class GToolFactory {

    public static AbstractGTool getGTool(Parameters params) {
        if (params.getGTool() == GToolName.AWT) {
            return new AWTGTool(params);
        }
        if (params.getGTool() == GToolName.SWING) {
            return new SwingGTool(params);
        }
        throw new UnsupportedOperationException("todo ");
    }

}
