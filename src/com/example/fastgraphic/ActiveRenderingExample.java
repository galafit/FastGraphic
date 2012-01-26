package com.example.fastgraphic;

	import java.awt.*;
	import java.awt.event.*;
	import java.awt.image.BufferStrategy;
	import java.util.Random;
	import javax.swing.*;

	public class ActiveRenderingExample extends JFrame implements Runnable,GTool {


        private long startTime = 0;
	    private long frames = 0;
	    private int width, height;
	    private GraphicsDevice device = null;
	    private boolean running = false;
        Parameters params;

        public ActiveRenderingExample(Parameters params) {
             this.params  = params;
            this.width = params.getWidth();
	        this.height = params.getHeight();
            setBackground(params.getBgColor().getColor());
            setForeground(params.getFgColor().getColor());

	        // ��������� repaint � ������� ����������� swing-�����������,
	        // ������ ��� �� ����� �� ���� ��������������
	        setIgnoreRepaint(true);
	        getRootPane().setDoubleBuffered(false);
	        setResizable(false);
	        setVisible(true);
	        // ������������� ������� ���� ������, ����� ���������� ������� ���� ���� width x height
	        Insets ins = getInsets();
	        setSize(ins.left + ins.right + width, ins.top + ins.bottom + height);
	        setLocationRelativeTo(null);
	        init();
        }

        private void init() {
	        // ����� ����� ��� �������� fps
	        startTime = System.currentTimeMillis();
	        // �������� ���������� ������� �����������
	        createBufferStrategy(2);

	        addWindowListener(new WindowAdapter() {
	            @Override public void windowClosing(WindowEvent ev) {
	                close();
	            }
	        });
	        addKeyListener(new KeyAdapter() {
	            @Override public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                    close();
	                }
	            }
	        });

	        Thread th = new Thread(this);
	        running = true;
	        th.start();
	    }

	    private void close() {
	        running = false;
	        // ���������� fps
	        System.out.println("FPS: " + frames * 1000 / (System.currentTimeMillis() - startTime));
	        // ���� �� � ������������� ������, ������������ � �������� �����
	        if (device != null) {
	            device.setFullScreenWindow(null);
	        }
	        System.exit(0);
	    }

	    public void run() {
	        BufferStrategy bs = getBufferStrategy();
            CompositePainter painter = new CompositePainter(params);
	        while (running) {
	            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                g.setClip(getRootPane().getBounds());

	            // �.�. ���������� ����� ��������� ��� ����� ���� �������,
	            // �� ��� ����������� ���������� ��������� ������ ����� � ��������� ����,
	            // ����� �������� ������ � ���������� ������� ����
	            Insets ins = getInsets();

	            // ������� 1000 ��������� �����
	            g.clearRect(ins.left, ins.top, width, height);
	            painter.paint(g);

	            // �����������
	            g.dispose();
	            // ������� ����� �� �����
	            bs.show();
	            // ���� ���������� ���� ���������� ��������
	            Toolkit.getDefaultToolkit().sync();
	            frames++;

	            // ����� ������ �� ��������� ���������, ���� �������� ��������� �����
	            try {
	                Thread.sleep(1);
	            } catch (InterruptedException e) {}
	            // ���� fps �������� � ���� ������ ��������,
	            // �� Thread.sleep() ���� �������� �� Thread.yield()

	            // �� �������� ������ ���� ������ �� sleep() ���� ����� 400 fps (�������� CPU 1-2%),
	            // � yield() - 1300 fps (�������� CPU 100%)
	        }
	    }


        public void startAnimation() {
            try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception e) {}
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
                    new ActiveRenderingExample(params);
	            }
	        });
        }
	}