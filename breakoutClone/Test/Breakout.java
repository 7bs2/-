package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout {
    private Frame frame = new Frame("Breakout clone");

    //桌面 宽高
    private final int TABLE_WIDTH = 300;
    private final int TABLE_HIGH = 400;

    //球拍的 宽高
    private final int RACKET_WIDTH = 60;
    private final int RACKET_HIGH = 20;

    //小球的初始大小
    private final int BALL_SIZE = 16;

    //记录小球坐标
    //默认初始坐标
    private int ballX = 120;
    private int ballY = 20;

    //记录小球在x，y上移动的速度 控制坐标变换
    private int speedY = 10;
    private int speedX = 15;

    //球拍的坐标
    private int racketX = 120;
    //球拍在y轴上一直是一个高度
    private final int racketY = 320;

    private int score = 0;

    private boolean isOver = false;

    Timer timer;

    private class MyCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            if (isOver) {
                g.setColor(Color.GREEN);
                g.setFont(new Font("ss", Font.BOLD, 30));
                g.drawString("score:" + score, 100, 200);
            } else {
                g.setColor(Color.red);
                g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

                g.setColor(Color.blue);
                g.fillRect(racketX, racketY, RACKET_WIDTH, RACKET_HIGH);
            }
        }
    }

    MyCanvas myCanvas = new MyCanvas();

    public void begin() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ballX < 0 || ballX > TABLE_WIDTH - BALL_SIZE) {
                    speedX = -speedX;
                }
                if (ballY < 0 || (ballY > racketY - BALL_SIZE && ballX >= racketX && ballX <= racketX + RACKET_WIDTH - BALL_SIZE)) {
                    if (ballY > 0) {
                        score++;
                    }
                    speedY = -speedY;
                }
                if (ballY > racketY - BALL_SIZE && (ballX < racketX || ballX > racketX + RACKET_WIDTH)) {
                    isOver = true;
                    timer.stop();
                }
                ballX += speedX;
                ballY += speedY;
                myCanvas.repaint();
            }
        });
        timer.start();
        KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (racketX > 0) {
                        racketX -= 10;
                        myCanvas.repaint();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (racketX < TABLE_WIDTH - RACKET_WIDTH) {
                        racketX += 10;
                        myCanvas.repaint();
                    }
                }

            }
        };
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        myCanvas.addKeyListener(keyListener);
        frame.addKeyListener(keyListener);
        myCanvas.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HIGH));
        frame.add(myCanvas);
        frame.setLocation(400, 200);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Breakout().begin();
    }
}
