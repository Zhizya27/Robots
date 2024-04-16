package game;

import java.awt.BorderLayout;
import javax.swing.*;


/**
 * Класс GameWindow представляет внутреннее окно с игровым полем.
 */
public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
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
        m_visualizer = new GameVisualizer(robotModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

}
