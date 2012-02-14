package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;

/**
 *
 *  Active rendering that implements double buffering by using VolatileImage class for the OffScreen back buffer.
 *
 *  VolatileImage is stored in hardware-accelerated memory (if possible). This means that instead of keeping the image
 *  in the system memory it is kept on the memory local to the graphics card.
 *  This allows for much faster drawing-to and copying-from operations.
 *
 *  For example, on Win32 systems, an image can be stored in Video RAM (VRAM) and operated on
 *  by the graphics accelerator in the PC.
 *  In a Solaris or Linux environment, an image created with VolatileImage is not volatile because neither Solaris
 *  nor Linux provide a way to directly access VRAM, like DirectDraw does on win32
 *
 *  A VolatileImage represents an image whose content in can be lost on certain occasions.
 *  This can occur in situations such as::
 *   -Another app going into fullscreen mode
 *   -The display mode changing on your screen (whether caused by your application or the user)
 *   -TaskManager being run
 *   -A screensaver kicking in
 *   -The system going into or out of StandBy or Hibernate mode...
 *
 *  In practice, direct use of VolatileImage is often not required since most graphical applications,
 *  such as those written with Swing, attempt to employ hardware acceleration implicitly. For instance,
 *  Swing uses VolatileImage for its double buffering and visuals loaded with getImage( ) are accelerated if possible,
 *  as are images used by the Java 2D API
 *
 *  !!! Software rendering to a VRAM image tends to take longer than software rendering to a system memory image.
 *  So sometimes application with not accelerated  back buffer storing in the system memory will work faster than
 *  application with  accelerated VolatileImage back buffer !!!
 *
 *  http://java.sun.com/j2se/1.4/pdf/VolatileImage.pdf
 *  http://content.gpwiki.org/index.php/Java:Tutorials:VolatileImage
 */

public class AcceleratedDoubleBufferingApplication implements PaintingArea {

    private CompositePainter painter;
    private Controller controller;
    private VolatileImage volatileImg;
    private Frame frame;
    private Canvas paintingCanvas;
    private Rectangle paintingClip;
    private Color backgroundColor;
    private Color foregroundColor;

    public AcceleratedDoubleBufferingApplication(String title, int width, int height,
                                                 Color bgColor, Color fgColor, CompositePainter painter, Controller contrl) {
        controller = contrl;
        this.painter = painter;
        paintingClip = new Rectangle(0,0,width,height);
        backgroundColor = bgColor;
        foregroundColor = fgColor;
        frame = new Frame();
        frame.setTitle(title);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
            }
        });
        paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(paintingClip.width,paintingClip.height));
        paintingCanvas.setBackground(backgroundColor);
        paintingCanvas.setForeground(foregroundColor);
// turn off paint events dispatched from the operating system since we are doing active rendering
        frame.setIgnoreRepaint(true);
// for simplicity we use  a fixed size Frame
        frame.setResizable(false);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);
// create Off-Screen VolatileImage Buffer
        volatileImg = createVolatileImage();
    }


    private  GraphicsConfiguration getGraphicsConfiguration(){
// We can get GraphicsConfiguration object by using getGraphicsConfiguration() method of any Component
// However, in order to get GraphicsConfiguration in this way, the component  must be displayable (or the return value may be null).
// The most efficient way to get the  GraphicsConfiguration of the graphics device on which the program is running.

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
        return defaultScreenDevice.getDefaultConfiguration();
    }   
    
    private VolatileImage createVolatileImage(){
// To create VolatileImage compatible with the hardware and the settings
// we can use createVolatileImage(width,height) method of any Component.
// However, in order to create VolatileImage in this way, the component  must be displayable (or the return value may be null).
// The most efficient way to create an VolatileImage is through the GraphicsConfiguration
// of the Graphics Device on which the program is running.
// The GraphicsConfiguration object represents the display settings of the user's hardware.

        GraphicsConfiguration gc = getGraphicsConfiguration();
        return gc.createCompatibleVolatileImage(paintingClip.width,paintingClip.height);
    }

// Since the content of VolatileImage buffer may become lost any time
// before any operation is done to the VolatileImage, we should test that the image is still there and usable
// and still compatible with the hardware and the settings
// There are to methods to do that: validate() and contentsLost()

    // rendering to the off-Screen VolatileImage buffer
    void renderOffscreen() {
        do {
            if (volatileImg.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE)
            {
                // if old volatileImg doesn't work with new GraphicsConfig; re-create it
                volatileImg = createVolatileImage();
            }
            // get Off-Screen Graphics
            Graphics2D offScreenG = (Graphics2D) volatileImg.createGraphics();
            // rendering to the off-Screen
            offScreenG.setClip(paintingClip);
            offScreenG.setBackground(backgroundColor);
            offScreenG.setColor(foregroundColor);
            offScreenG.clearRect(paintingClip.x,paintingClip.y,paintingClip.width,paintingClip.height);
            painter.paint(offScreenG);
            // Dispose the graphics
            offScreenG.dispose();
            // Repeat the rendering if the content of VolatileImage  buffer was lost
        } while (volatileImg.contentsLost());
    }


// Render single frame
    public void changeFrame() {
        renderOffscreen();
// copying from the offScreen Image to the Screen (here, screenG is the Graphics
// object for the onscreen window)
        do {
// verify that volatileImg is ok
            int returnCode = volatileImg.validate(getGraphicsConfiguration());
            if (returnCode == VolatileImage.IMAGE_RESTORED) {
                // Contents need to be restored
                renderOffscreen();
            } else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
                // old volatileImg doesn't work with new GraphicsConfig; re-create it
                volatileImg = createVolatileImage();
                renderOffscreen();
            }
// copy on the screen
            Graphics screenG = paintingCanvas.getGraphics();
            screenG.drawImage(volatileImg, 0, 0, null);
            screenG.dispose();
            // Repeat the rendering if the content of VolatileImage buffer was lost
        } while (volatileImg.contentsLost());

// The call to “Toolkit.getDefaultToolkit().sync()” exists to fix event queue problems on Linux
// On many systems, this method does nothing.
        Toolkit.getDefaultToolkit().sync();
    }

}
