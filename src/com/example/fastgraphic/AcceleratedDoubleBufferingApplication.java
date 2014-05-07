package com.example.fastgraphic;

import com.example.fastgraphic.painters.Painter;

import java.awt.*;
import java.awt.image.VolatileImage;

/**
 * Active rendering which implements double buffering by using VolatileImage class for the OffScreen back buffer.
 * <p/>
 * VolatileImage class allows you to create and manage a hardware-accelerated offscreen image.
 * That means that instead of keeping offscreen image in the system memory it is kept on the memory local to the graphics card.
 * This allows much faster drawing-to and copying-from operations.
 * <p/>
 * For example, on Win32 systems, an image can be stored in Video RAM (VRAM) and operated on
 * by the graphics accelerator in the PC.
 * In a Solaris or Linux environment, an image created with VolatileImage is not volatile because neither Solaris
 * nor Linux provide a way to directly access VRAM, like DirectDraw does on win32
 * <p/>
 * A VolatileImage represents an image which content can be lost on certain occasions:
 * -Another app going into fullscreen mode
 * -The display mode changing on your screen (whether caused by your application or the user)
 * -TaskManager being run
 * -A screensaver kicking in
 * -The system going into or out of StandBy or Hibernate mode...
 * <p/>
 * In practice, direct use of VolatileImage is often not required since most graphical applications,
 * such as those written with Swing, attempt to employ hardware acceleration implicitly. For instance,
 * Swing uses VolatileImage for its double buffering and visuals loaded with getImage( ) are accelerated if possible,
 * as images used by the Java 2D API
 * <p/>
 * http://java.sun.com/j2se/1.4/pdf/VolatileImage.pdf
 * http://content.gpwiki.org/index.php/Java:Tutorials:VolatileImage
 */

public class AcceleratedDoubleBufferingApplication extends AbstractGraphicsApplication {

    private Canvas paintingCanvas = new Canvas();
    private Rectangle paintingClip; //Painting Clip for Graphics object should be set manually for Active Rendering
    private VolatileImage volatileImg;  // back  buffer for Double Buffering


    public AcceleratedDoubleBufferingApplication(int frameRate, Painter painter, int width, int height, Color bgColor, Color fgColor) {
        super(painter);
        paintingClip = new Rectangle(0, 0, width, height);
        Frame mainFrame = new Frame();
        setDefaultCloseOperation(mainFrame);
        mainFrame.setTitle(ApplicationType.ACCELERATED_BUFF.getLabel());
        setSizeAndColors(paintingCanvas, width, height, bgColor, fgColor);
        mainFrame.add(paintingCanvas);
        mainFrame.pack();
        // place the window to the screen center
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // create Off-Screen VolatileImage Buffer
        volatileImg = createVolatileImage();
        startAnimation(frameRate);
    }

    /**
     * We can get GraphicsConfiguration object by using getGraphicsConfiguration() method of any Component.
     * However, in order to get GraphicsConfiguration in this way, the component  must be displayable (otherwise the return value might be null).
     * The most efficient way to get the  GraphicsConfiguration of the graphics device on which the program is running:
     * <pre>
     *       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
     *       GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
     *       defaultScreenDevice.getDefaultConfiguration();
     *       </pre>
     */
    private GraphicsConfiguration getGraphicsConfiguration() {
        return paintingCanvas.getGraphicsConfiguration();
    }

    /**
     * To create VolatileImage compatible with the hardware and the settings
     * we can use createVolatileImage(width,height) method of any Component.
     * However, in order to create VolatileImage in this way, the component  must be displayable (otherwise the return value might be null).
     * The most efficient way to create an VolatileImage is through the GraphicsConfiguration
     * of the Graphics Device on which the program is running:
     * <pre>
     *  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
     *  GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
     *  GraphicsConfiguration graphicsConfiguration = defaultScreenDevice.getDefaultConfiguration();
     *  graphicsConfiguration.createCompatibleVolatileImage(width,height);
     * </pre>
     * The GraphicsConfiguration object represents the display settings of the user's hardware.
     */
    private VolatileImage createVolatileImage() {
        return paintingCanvas.createVolatileImage(paintingClip.width, paintingClip.height);
    }

    /**
     * VolatileImage is an image which can lose its content at any time.
     * So usually drawing to the off-screen buffer and coping the image to the screen done in do-while loop.
     * This ensure that if something was wrong (rendering data was lost or Graphics settings was changed) and
     * on the screen appears "damaged" image, painting will be repeated till we see intact image.
     * See: http://docs.oracle.com/javase/6/docs/api/java/awt/image/VolatileImage.html
     *
     * In our case of quickly-changing frames it is not necessary  -
     * instead of repainting the damaged frame the next frame simply will be painted
    */
    public void repaintFrame() {
        // Create new volatileImg every time
        // to make sure that it is compatible with GraphicsConsfiguration which could be changed
        if (volatileImg.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
            volatileImg = createVolatileImage();
        }
        // get Off-Screen Graphics
        Graphics offScreenG = volatileImg.createGraphics();
        // rendering to the off-Screen
        offScreenG.setClip(paintingClip);
        offScreenG.clearRect(paintingClip.x, paintingClip.y, paintingClip.width, paintingClip.height);
        painter.paint(offScreenG);
        // Dispose the off-screen graphics
        offScreenG.dispose();
        // copy to the screen
        Graphics screenG = paintingCanvas.getGraphics();
        screenG.drawImage(volatileImg, 0, 0, null);
        // Dispose the screen graphics
        screenG.dispose();

        // The call to Toolkit.sync( ) after drawImage( ) ensures that the display is promptly updated.
        // This is required for Linux, which does not automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a " tearing " effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append(super.getReport());
        report.append("\nVolatileImage is Accelerated - "
                + volatileImg.getCapabilities().isAccelerated());
        report.append("\nVolatileImage is True Volatile  - "
                + volatileImg.getCapabilities().isTrueVolatile());
        return report.toString();
    }
}
