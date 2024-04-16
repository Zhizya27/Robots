package game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Контроллер. Управляет игровым процессом, используя модель робота и визуализатор игры
 */
public class GameController {
    private RobotModel robotModel;
    private GameVisualizer gameVisualizer;

    /**
     * Таймер для регулярного вызова метода moveRobot()
     */
    private final Timer timer = initTimer();

    /**
     * Инициализирует таймер для регулярного вызова метода moveRobot()
     * @return новый объект Timer
     */
    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    /**
     * Конструктор класса GameController
     *
     * @param robotModel  модель робота
     * @param gameVisualizer  визуализатор игры
     */
    public GameController(RobotModel robotModel, GameVisualizer gameVisualizer){
        this.robotModel = robotModel;
        this.gameVisualizer = gameVisualizer;
        this.robotModel.addObserver(this.gameVisualizer);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robotModel.moveRobot();
            }
        }, 0, 10);
    }

}

