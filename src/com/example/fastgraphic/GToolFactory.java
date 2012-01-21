package com.example.fastgraphic;

/**
 * Factory to create GToolName with specified Parameters
 */
public class GToolFactory {

    public static  GTool getGTool(Parameters param){
      if(param.getGTool()==GToolName.AWT )  {
          return new AWTGTool(param);
      }
      throw new UnsupportedOperationException("todo ");
    }

}
