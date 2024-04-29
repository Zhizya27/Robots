package game;


import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;


/**
 * Класс  представляет визуализацию игрового поля.
 */
public class GameVisualizer extends JPanel implements Observer {

    private final RobotModel robotModel;


    /**
     * Создает новый визуализатор игры
     * @param robotModel модель робота, за изменениями которой следит визуализатор
     */
    public GameVisualizer(RobotModel robotModel) {
        this.robotModel = robotModel;
        this.robotModel.addObserver(this); // Регистрируем GameVisualizer как наблюдателя за изменениями в RobotModel

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Logger.debug("Робот начал движение");
                int adjustedX = e.getPoint().x;
                int adjustedY = e.getPoint().y;
                robotModel.setTargetPosition(new Point(adjustedX, adjustedY));
                repaint();
            }
        });
    }


    /**
     * Переопределяет метод отрисовки компонента, чтобы нарисовать робота и цель.
     * @param g объект Graphics для рисования
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, (int)robotModel.getX(), (int)robotModel.getY(), robotModel.getDirection());
        drawTarget(g2d);
    }

    /**
     * Рисует изображение робота в указанных координатах и направлении.
     * @param g объект Graphics2D для рисования
     * @param x координата X позиции робота
     * @param y координата Y позиции робота
     * @param direction направление робота в радианах
     */
    private void drawRobot(Graphics2D g, int x, int y, double direction) {
        double robotDirection = robotModel.getDirection();

        int robotCenterX = (int)robotModel.getX();
        int robotCenterY = (int)robotModel.getY();
        AffineTransform t = AffineTransform.getRotateInstance(robotDirection, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }


    /**
     * Рисует цель в указанных координатах.
     * @param g объект Graphics2D для рисования
     */
    private void drawTarget(Graphics2D g) {
        int targetX = (int)robotModel.getTargetX();
        int targetY = (int)robotModel.getTargetY();

        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, targetX, targetY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, targetX, targetY, 5, 5);
    }

    /**
     * Заполняет овал в указанных координатах и размерах.
     * @param g объект Graphics для рисования
     * @param centerX координата X центра овала
     * @param centerY координата Y центра овала
     * @param diam1 диаметр овала по оси X
     * @param diam2 диаметр овала по оси Y
     */
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    /**
     * Рисует контур овала в указанных координатах и размерах.
     * @param g объект Graphics для рисования
     * @param centerX координата X центра овала
     * @param centerY координата Y центра овала
     * @param diam1 диаметр овала по оси X
     * @param diam2 диаметр овала по оси Y
     */
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

/**
 * Обновляет визуализацию при изменениях в модели робота.
 * @param o   объект Observable, который изменился
 */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof RobotModel) {
            repaint();
        }
    }

}
