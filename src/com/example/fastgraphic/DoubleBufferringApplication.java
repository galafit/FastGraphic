package com.example.fastgraphic;

import com.example.fastgraphic.painters.Painter;

import java.awt.*;

/**
 * Active Rendering in this case implements simple Double Buffering with OffScreen back buffer stored in the system memory.
 *
 * Instead of drawing directly to the screen, Double buffering involves first drawing to an offscreen image,
 * and then coping that image to the screen.
 *
 */

public class DoubleBufferringApplication extends AbstractGraphicsApplication {

    private Canvas paintingCanvas = new Canvas();
    private Rectangle paintingClip; //Painting Clip for Graphics private Painter painter;  // implements the painting routine
    private Image offScreenImage; // back  buffer for Double Buffering

    public DoubleBufferringApplication(int frameRate, Painter painter, int width, int height,Color bgColor, Color fgColor){
        super(painter);
        paintingClip = new Rectangle(0,0,width,height);
        Frame mainFrame = new Frame();
        mainFrame.setTitle(ApplicationType.DOUBLE_BUFF.getLabel());
        setDefaultCloseOperation(mainFrame);
        setSizeAndColors(paintingCanvas, width, height, bgColor, fgColor);
        mainFrame.add(paintingCanvas);
        mainFrame.pack();
        // place the window to the screen center
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // create Off-Screen Image Buffer
        offScreenImage = createBufferedImage();
        startAnimation(frameRate);
    }



    public void repaintFrame() {
        // get Off-Screen Graphics
        Graphics offScreenG = offScreenImage.getGraphics();
        // rendering to the off-Screen
        offScreenG.setClip(paintingClip);
        offScreenG.clearRect(paintingClip.x,paintingClip.y,paintingClip.width,paintingClip.height);
        painter.paint(offScreenG);
        // Dispose the off-Screen graphics
        offScreenG.dispose();
        //get Graphics object for the onscreen window
        Graphics screenG = paintingCanvas.getGraphics();
        // copy of-Screen Image to the Screen
        screenG.drawImage(offScreenImage,paintingClip.x,paintingClip.y, null);
        // Dispose the on-screen graphics
        screenG.dispose();

        // The call to Toolkit.sync( ) after drawImage( ) ensures that the display is promptly updated.
        // This is required for Linux, which does not automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a "tearing" effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();

    }

    /**
     * For the OffScreen back buffer we need to create an image as close as possible to the Graphics Configuration
     * in terms of its format, with a data layout and color model compatible with the graphics configuration it is drawing to.
     * We can do it by using method createImage(width,height) of any Component
     * However, in order to create an image in this way, the component  must be displayable (otherwise the return value might be null)
     * The most efficient way to create an image is through the Graphics Configuration of the graphics device
     * where the program is running:
     * <pre>
     *  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
     *  GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
     *  GraphicsConfiguration graphicsConfiguration = defaultScreenDevice.getDefaultConfiguration();
     *  graphicsConfiguration.createCompatibleImage(width,height);
     * </pre>
     *
     */
    private Image createBufferedImage(){
// Returns a BufferedImage (Subclass of Image) with a data layout and color model compatible with this graphicsConfiguration.
        return paintingCanvas.createImage(paintingClip.width,paintingClip.height);
    }
}
