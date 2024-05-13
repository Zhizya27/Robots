package game;

import gui.LocalManager;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 *  представляет внутреннее окно для отображения текущих координат робота.
 */
public class CoordinatesWindow extends JInternalFrame implements Observer {
    private JLabel xLabel;
    private JLabel yLabel;

    /**
     * Создает новый экземпляр окна координат.
     *
     * @param robotModel модель робота, для которой отображаются координаты
     */
    public CoordinatesWindow(RobotModel robotModel) {
        super(LocalManager.getString("robotCoordinates"));
        robotModel.addObserver(this); // Подписываемся на уведомления от RobotModel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(150, 100);
        initializeComponents();
        layoutComponents();
    }

    /**
     * Инициализирует компоненты окна.
     */
    private void initializeComponents() {
        xLabel = new JLabel("X now: ");
        yLabel = new JLabel("Y now: ");
    }

    /**
     * Размещает компоненты в окне.
     */
    private void layoutComponents() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(xLabel);
        panel.add(yLabel);

        getContentPane().add(panel);
    }

    /**
     * Обновляет координаты робота на основе изменений в модели.
     *
     * @param observable объект, который вызвал уведомление (RobotModel)
     * @param obj        дополнительные данные (не используются)
     */
    @Override
    public void update(Observable observable, Object obj) {
        if (observable instanceof RobotModel) {
            RobotModel model = (RobotModel) observable;
            double x = model.getX();
            double y = model.getY();
            updateCoordinates(x, y);
        }
    }

    /**
     * Обновляет текстовые метки с текущими координатами робота.
     *
     * @param x текущая координата X робота
     * @param y текущая координата Y робота
     */
    private void updateCoordinates(double x, double y) {
        xLabel.setText("X now: " + x);
        yLabel.setText("Y now: " + y);
    }
}
