package game;

import log.Logger;

import java.awt.*;
import java.util.Observable;

public class RobotModel extends Observable {

    /**
     * позиция робота по координате x
     */
    private double positionX;

    /**
     * позиция робота по координате y
     */
    private double positionY;

    /**
     * направление робота
     */
    private double direction;

    /**
     * позиция цели по координате x
     */
    private volatile int targetPositionX = 150;
    /**
     * позиция цели по координате y
     */
    private volatile int targetPositionY = 100;

    /**
     * максималная скорость перемещения робота
     */
    private static final double maxVelocity = 0.1;

    /**
     * максимальная скорость поворота робота
     */
    private static final double maxAngularVelocity = 0.003;


    /**
     * Создает новый экземпляр RobotModel с указанной начальной позицией.
     * @param initialPositionX начальная координата X
     * @param initialPositionY начальная координата Y
     */
    public RobotModel(double initialPositionX, double initialPositionY) {
        this.positionX = initialPositionX;
        this.positionY = initialPositionY;
    }


    /**
     * Вычисляет расстояние между двумя точками.
     *
     * @param x1 координата X первой точки
     * @param y1 координата Y первой точки
     * @param x2 координата X второй точки
     * @param y2 координата Y второй точки
     * @return расстояние между точками
     */
    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    /**
     * Вычисляет угол между двумя точками.
     *
     * @param fromX координата X начальной точки
     * @param fromY координата Y начальной точки
     * @param toX   координата X конечной точки
     * @param toY   координата Y конечной точки
     * @return угол между точками в радианах
     */
    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }


    /**
     * Приводит угол к диапазону от 0 до 2π радиан.
     * @param angle угол для нормализации
     * @return нормализованный угол
     */
    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    /**
     * Обновляет позицию и направление робота.
     * @param newX новая координата X
     * @param newY новая координата Y
     * @param newDirect новое направление в радианах
     */
    public void updatePosition(double newX, double newY, double newDirect) {
        this.positionX = newX;
        this.positionY = newY;
        this.direction = newDirect;

        setChanged();
        notifyObservers();
    }

    /**
     * // Метод для расчета угловой скорости на основе разницы углов
     * @param angleDifference
     * @return угловая скорость со знаком
     */
    private double calculateAngularVelocity(double angleDifference) {
        return Math.signum(angleDifference) * maxAngularVelocity;
    }

    private boolean reachedTarget = false;

    /**
     * Выполняет движение робота к цели.
     */
    void moveRobot() {
        double distance = distance(targetPositionX, targetPositionY, positionX, positionY);
        boolean previouslyReached = reachedTarget;

        if (distance < 5) {
            reachedTarget = true;
        } else {
            reachedTarget = false;
        }

        if (reachedTarget && !previouslyReached) {
           Logger.debug("Робот у цели");
        }

        if (reachedTarget) {
            return;
        }

        double angleToTarget = angleTo(positionX, positionY, targetPositionX, targetPositionY);
        double angleDifference = angleToTarget - direction;

        if (angleDifference > Math.PI) {
            angleDifference -= 2 * Math.PI;
        } else if (angleDifference < -Math.PI) {
            angleDifference += 2 * Math.PI;
        }

        double angularVelocity = calculateAngularVelocity(angleDifference); // Если угол положителен,
        // то робот будет поворачиваться по часовой стрелке с максимальной угловой скоростью, а если отрицателен, то против часовой стрелки.

        double velocity = Math.min(maxVelocity, distance);

        double duration = 10.0;
        double newX = positionX + velocity * Math.cos(direction) * duration;
        double newY = positionY + velocity * Math.sin(direction) * duration;
        double newDirection = asNormalizedRadians(direction + angularVelocity * duration);

        updatePosition(newX, newY, newDirection);
    }

    /**
     * Получает текущую координату X робота.
     * @return текущая координата X
     */
    public double getX() {
        return (double) positionX;
    }

    /**
     * Получает текущую координату Y робота.
     * @return текущая координата Y
     */
    public double getY() {
        return (double) positionY;
    }

    /**
     * Получает текущее направление робота в радианах.
     * @return текущее направление в радианах
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Получает координату цели по x
     * @return координата цели по x
     */
    public double getTargetX() {
        return targetPositionX;
    }

    /**
     * Получает координату цели по y
     * @return координата цели по y
     */
    public int getTargetY() {
        return targetPositionY;
    }

    /**
     * Устанавливает целевую позицию для робота.
     * @param p точка, которая является целью для робота
     */
    public void setTargetPosition(Point p) {
        this.targetPositionX = p.x;
        this.targetPositionY = p.y;
    }
}
