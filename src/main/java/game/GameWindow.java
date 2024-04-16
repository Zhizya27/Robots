package game;

import java.awt.BorderLayout;
import javax.swing.*;


/**
 * Класс GameWindow представляет внутреннее окно с игровым полем.
 */
public class GameWindow extends JInternalFrame
{
    private final GameVisualizer visualizer;
    private RobotModel robotModel;

    /**
     * Создает новый экземпляр GameWindow.
     */
    public GameWindow(RobotModel robotModel)
    {
        super("Игровое поле", true, true, true, true);
        this.robotModel = robotModel;
        CoordinatesWindow robotCoordinatesDialog = new CoordinatesWindow(this.robotModel);
        add(robotCoordinatesDialog, BorderLayout.NORTH);
        visualizer = new GameVisualizer(robotModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

}
