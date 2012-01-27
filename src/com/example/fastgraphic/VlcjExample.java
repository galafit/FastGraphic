package com.example.fastgraphic;

import com.example.fastgraphic.painters.CompositePainter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.test.VlcjTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;


public class VlcjExample extends VlcjTest {
    private final BufferedImage image;
    private final MediaPlayerFactory factory;
    private Parameters params;
    private final DirectMediaPlayer mediaPlayer;
    private final CompositePainter compositePainter;

    private ImagePane imagePane;

    public VlcjExample(Parameters parameters) throws InterruptedException, InvocationTargetException {
        this.params = parameters;
        final int width = parameters.getWidth();
        final int height = parameters.getHeight();
        compositePainter= new CompositePainter(parameters.getActivePainters());
        image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);
        image.setAccelerationPriority(1.0f);

       SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame frame = new JFrame("VLCJ Direct Video Test");
                frame.setIconImage(new ImageIcon(getClass().getResource("/icons/vlcj-logo.png")).getImage());
                imagePane = new ImagePane(image);
                imagePane.setSize(width, height);
                imagePane.setMinimumSize(new Dimension(width, height));
                imagePane.setPreferredSize(new Dimension(width, height));
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(imagePane, BorderLayout.CENTER);
                frame.pack();
                frame.setResizable(false);
                frame.setVisible(true);
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent evt) {
                        mediaPlayer.release();
                        factory.release();
                        frame.dispose();
                    }
                });
            }
        });

        factory = new MediaPlayerFactory(new String[]{});
        mediaPlayer = factory.newDirectMediaPlayer(width, height, new TestRenderCallback());
        mediaPlayer.playMedia("d:\\m&v\\video\\unfaithful.mkv");   //todo remove or move to properties
    }

    @SuppressWarnings("serial")
    private final class ImagePane extends JPanel {

        private final BufferedImage image;

        public ImagePane(BufferedImage image) {
            this.image = image;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setBackground(params.getBgColor().getColor());
            g2.setColor(params.getFgColor().getColor());
            g2.clearRect(0,0,params.getWidth(),params.getHeight());

            compositePainter.paint(g2);
        }
    }

    private final class TestRenderCallback extends RenderCallbackAdapter {

        public TestRenderCallback() {
            super(new int[params.getWidth() * params.getHeight()]);
        }

        @Override
        public void onDisplay(int[] data) {
            image.setRGB(0, 0, params.getWidth(), params.getHeight(), data, 0, params.getWidth());
            imagePane.repaint();
        }
    }
}
