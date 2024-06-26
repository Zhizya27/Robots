package game;

import locale.LocalManager;
import locale.LocalManagerInterface;

import java.awt.BorderLayout;
import javax.swing.*;


/**
 * Класс GameWindow представляет внутреннее окно с игровым полем.
 */
public class GameWindow extends JInternalFrame implements LocalManagerInterface
{
    private final GameVisualizer visualizer;
    private RobotModel robotModel;

    /**
     * Создает новый экземпляр GameWindow.
     */
    public GameWindow(RobotModel robotModel)
    {
        super(LocalManager.getStringLocal("playingField"), true, true, true, true);
        this.robotModel = robotModel;
        CoordinatesWindow robotCoordinatesDialog = new CoordinatesWindow(this.robotModel);
        add(robotCoordinatesDialog, BorderLayout.NORTH);
        visualizer = new GameVisualizer(robotModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void localization() {
        setTitle(LocalManager.getStringLocal("playingField"));
    }
}
