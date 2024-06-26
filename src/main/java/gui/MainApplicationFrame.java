package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import game.*;
import javax.swing.*;

import locale.LocalManager;
import log.Logger;
import state.StateManager;


/**
 * Главное окно приложения.
 */
public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final StateManager stateManager;
    private CoordinatesWindow robotCoordinates;
    private RobotModel robotModel;
    private GameController gameController;
    private GameVisualizer gameVisualizer;

    /**
     * Создает новый экземпляр главного окна приложения.
     */
    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);


        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        robotModel = new RobotModel(100, 100);
        GameWindow gameWindow = new GameWindow(robotModel);
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);
        gameVisualizer = new GameVisualizer(robotModel);
        gameController = new GameController(robotModel, gameVisualizer);

        robotCoordinates = new CoordinatesWindow(robotModel);
        addWindow(robotCoordinates);

        stateManager = new StateManager(this, logWindow, gameWindow, robotCoordinates);

        setJMenuBar(new MenuBar(this, logWindow, robotCoordinates, gameWindow));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Отключаем стандартное действие при закрытии окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndExit();
            }
        });
        stateManager.restoreState();
    }


    /**
     * Подтверждает выход из приложения.
     */
    void confirmAndExit() {
        Object[] options = {LocalManager.getStringLocal("yesButton"), LocalManager.getStringLocal("noButton")};
        int result = JOptionPane.showOptionDialog(
                this,
                LocalManager.getStringLocal("confirmExitMessage"),
                LocalManager.getStringLocal("confirmExitPanel"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );
        if (result == JOptionPane.YES_OPTION) {
            stateManager.saveState();
            dispose();
            System.exit(0);

        }

    }

    /**
     * Создает окно протокола работы(Лог - окно)
     */
    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("");
        return logWindow;
    }

    /**
     * Добавляет внутреннее окно в рабочее пространство.
     *
     * @param frame внутреннее окно
     */
    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }


}