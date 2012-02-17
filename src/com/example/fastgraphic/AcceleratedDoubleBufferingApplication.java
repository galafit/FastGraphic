package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompoundPainter;
import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;

/**
 *
 *  Active rendering Which implements double buffering by using VolatileImage class for the OffScreen back buffer.
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
 *  A VolatileImage represents an image whose content can be lost on certain occasions:
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
 *  !!! Software rendering to a VRAM image tends to take longer than software rendering to a system memory image!!!
 *  So sometimes application with not accelerated  back buffer storing in the system memory will work faster than
 *  application with  accelerated VolatileImage back buffer
 *
 *  http://java.sun.com/j2se/1.4/pdf/VolatileImage.pdf
 *  http://content.gpwiki.org/index.php/Java:Tutorials:VolatileImage
 */

public class AcceleratedDoubleBufferingApplication implements PaintingArea {

    private Canvas paintingCanvas;
    private Rectangle paintingClip; //Painting Clip for Graphics object should be set manually for Active Rendering
    private Painter painter;  // implements all painting routine
    private Animator animator; // that will invoke repaintFrame() method in loop
    private VolatileImage volatileImg;  // back  buffer for Double Buffering


    public AcceleratedDoubleBufferingApplication(Painter painter, int width, int height,Color bgColor, Color fgColor){
        this.painter = painter;
        paintingClip = new Rectangle(0,0,width,height);
        final Frame frame = new Frame();  // Application Main Frame
        frame.setTitle(ApplicationType.ACCELERATED_BUFF.getLabel());
        // add Window Listener to the frame which will stop animation and dispose the frame on closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stopAnimation();
                frame.dispose();
                //create a Window with information about real average FrameRate
                JOptionPane.showMessageDialog(frame, animator.getFrameRateReport());
            }
        });
        paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(width, height));
        paintingCanvas.setBackground(bgColor);
        paintingCanvas.setForeground(fgColor);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);
        // put the window at the screen center
        frame.setLocationRelativeTo(null);

        // create Off-Screen VolatileImage Buffer
        volatileImg = createVolatileImage();
    }

    /**
     * We can get GraphicsConfiguration object by using getGraphicsConfiguration() method of any Component
     * However, in order to get GraphicsConfiguration in this way, the component  must be displayable (or the return value may be null).
     * The most efficient way to get the  GraphicsConfiguration of the graphics device on which the program is running.
     *       <pre>
     *       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
     *       GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
     *       defaultScreenDevice.getDefaultConfiguration();
     *       </pre>
     */
    private  GraphicsConfiguration getGraphicsConfiguration(){
        return paintingCanvas.getGraphicsConfiguration();
    }

    /**
     * To create VolatileImage compatible with the hardware and the settings
     * we can use createVolatileImage(width,height) method of any Component.
     * However, in order to create VolatileImage in this way, the component  must be displayable (or the return value may be null).
     * The most efficient way to create an VolatileImage is through the GraphicsConfiguration
     * of the Graphics Device on which the program is running.
     * The GraphicsConfiguration object represents the display settings of the user's hardware.
     *   <pre>
     *  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
     *  GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
     *  GraphicsConfiguration graphicsConfiguration = defaultScreenDevice.getDefaultConfiguration();
     *  graphicsConfiguration.createCompatibleVolatileImage(width,height);
     * </pre>
     */
    private VolatileImage createVolatileImage(){
        return paintingCanvas.createVolatileImage(paintingClip.width,paintingClip.height);
    }

    public void setAnimator(Animator animator){
        this.animator = animator;
    }

    /**
     * This code is based on the example (VolatileDuke.java) from
     * http://java.sun.com/j2se/1.4/pdf/VolatileImage.pdf
     */
    public void repaintFrame() {
        do {
            // If GraphicsConsfiguration was changed and old volatileImg doesn't work with new GraphicsConfig; re-create it
            if (volatileImg.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE){
                volatileImg = createVolatileImage();
            }
            // get Off-Screen Graphics
            Graphics offScreenG =  volatileImg.createGraphics();
            // rendering to the off-Screen
            offScreenG.setClip(paintingClip);
            offScreenG.clearRect(paintingClip.x,paintingClip.y,paintingClip.width,paintingClip.height);
            painter.paint(offScreenG);
            // Dispose the graphics
            offScreenG.dispose();
            // copy on the screen
            Graphics screenG = paintingCanvas.getGraphics();
            screenG.drawImage(volatileImg, 0, 0, null);
            screenG.dispose();
            // Repeat the rendering if the content of VolatileImage  buffer was lost
        } while (volatileImg.contentsLost());

        // The call to Toolkit.sync( ) after drawImage( ) ensures that the display is promptly updated.
        // This is required for Linux, which doesn't automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a " tearing " effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();
    }
}
