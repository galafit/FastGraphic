package com.example.fastgraphic;


public class Parameters {

    public static final NamedColor availableColors[] = {NamedColor.black, NamedColor.green, NamedColor.red, NamedColor.gray};


    public int width = 640;
    public int height = 480;
    public int frameRate = 60;
    public float frameShift = 1;
    public NamedColor bgColor = availableColors[0];
    public NamedColor fgColor = availableColors[1];

    public GTool gTool = GTool.AWT;
    public BufferingType bufferingType = BufferingType.DIRECT;
}
