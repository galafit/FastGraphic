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

	        // отключаем repaint и двойную буферизацию swing-компонентов,
	        // потому что всё равно всё сами перерисовываем
	        setIgnoreRepaint(true);
	        getRootPane().setDoubleBuffered(false);
	        setResizable(false);
	        setVisible(true);
	        // устанавливаем размеры окна такими, чтобы клиентская область окна была width x height
	        Insets ins = getInsets();
	        setSize(ins.left + ins.right + width, ins.top + ins.bottom + height);
	        setLocationRelativeTo(null);
	        init();
        }

        private void init() {
	        // время нужно для подсчета fps
	        startTime = System.currentTimeMillis();
	        // включаем аппаратную двойную буферизацию
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
	        // показываем fps
	        System.out.println("FPS: " + frames * 1000 / (System.currentTimeMillis() - startTime));
	        // если мы в полноэкранном режиме, возвращаемся в исходный режим
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

	            // т.к. аппаратный буфер создается для всего окна целиком,
	            // то при перерисовке приходится учитывать размер рамки и заголовок окна,
	            // чтобы рисовать только в клиентской области окна
	            Insets ins = getInsets();

	            // выводим 1000 случайных линий
	            g.clearRect(ins.left, ins.top, width, height);
	            painter.paint(g);

	            // обязательно
	            g.dispose();
	            // выводим буфер на экран
	            bs.show();
	            // ждем завершения всех аппаратных операций
	            Toolkit.getDefaultToolkit().sync();
	            frames++;

	            // чтобы сильно не нагружать процессор, надо вставить небольшую паузу
	            try {
	                Thread.sleep(1);
	            } catch (InterruptedException e) {}
	            // если fps критичен и надо выжать максимум,
	            // то Thread.sleep() надо заменить на Thread.yield()

	            // на тестовой машине этот пример со sleep() дает около 400 fps (загрузка CPU 1-2%),
	            // с yield() - 1300 fps (загрузка CPU 100%)
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