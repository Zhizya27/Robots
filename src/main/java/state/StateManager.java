package state;

import game.GameWindow;
import gui.LogWindow;
import gui.MainApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.*;

/**
 * класс, реализующий методы для сохранения и восстанговления состояний окон
 */
public class StateManager implements State {
    private final MainApplicationFrame mainFrame;
    private final LogWindow logWindow;
    private final GameWindow gameWindow;

    private File configFilePath; // файл для сохранения состояния окна


    /**
     * Конструктор класса StateManager
     *
     * @param mainFrame  основное окно приложения
     * @param logWindow  окно лога
     * @param gameWindow игровое окно
     */
    public StateManager(MainApplicationFrame mainFrame, LogWindow logWindow, GameWindow gameWindow) {
        this.mainFrame = mainFrame;
        this.logWindow = logWindow;
        this.gameWindow = gameWindow;
    }
    /**
     * Метод для сохранения состояния окон
     */
    @Override
    public void saveState() {
        Map<String, String> state = new HashMap<>();

        saveComponentState(mainFrame, state, "mainFrame");
        saveComponentState(logWindow, state, "logWindow");
        saveComponentState(gameWindow, state, "gameWindow");

        configFilePath = new File(
                System.getProperty("user.home") + File.separator + "config.dat"
        );
        try (ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(configFilePath))) {
            obj.writeObject(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для восстановления состояния окон
     */
    @Override
    public void restoreState() {
        configFilePath = new File(
                System.getProperty("user.home") + File.separator + "config.dat"
        );
        Map<String, String> state = null;
        try (ObjectInputStream obj = new ObjectInputStream(new FileInputStream(configFilePath))) {
            state = (Map<String, String>) obj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (state != null) {

            restoreComponentState(mainFrame, state, "mainFrame");
            restoreComponentState(logWindow, state, "logWindow");
            restoreComponentState(gameWindow, state, "gameWindow");
        }
    }

    /**
     * Метод для сохранения состояния компонентов окна
     *
     * @param component компонент окна
     * @param state     состояние окна
     * @param prefix    префикс для идентификации компонента
     */
    private void saveComponentState(Component component, Map<String, String> state, String prefix) {
        if (component.isVisible()) {
            state.put(prefix + "X", Integer.toString(component.getX()));
            state.put(prefix + "Y", Integer.toString(component.getY()));
            state.put(prefix + "Width", Integer.toString(component.getWidth()));
            state.put(prefix + "Height", Integer.toString(component.getHeight()));
        }
        if (component instanceof JInternalFrame) {
            JInternalFrame frame = (JInternalFrame) component;
            state.put(prefix + "IsIcon", Boolean.toString(frame.isIcon()));
        }
    }

    /**
     * Метод для восстановления состояния компонентов окна
     *
     * @param component компонент окна
     * @param state     состояние окна
     * @param prefix    префикс для идентификации компонента
     */
    private void restoreComponentState(Component component, Map<String, String> state, String prefix) {
        if (state.containsKey(prefix + "X") && state.containsKey(prefix + "Y") &&
                state.containsKey(prefix + "Width") && state.containsKey(prefix + "Height")) {
            component.setBounds(
                    Integer.parseInt(state.get(prefix + "X")),
                    Integer.parseInt(state.get(prefix + "Y")),
                    Integer.parseInt(state.get(prefix + "Width")),
                    Integer.parseInt(state.get(prefix + "Height"))
            );
        }
        if (component instanceof JInternalFrame && state.containsKey(prefix + "IsIcon")) {
            JInternalFrame internalFrame = (JInternalFrame) component;
            boolean isIcon = Boolean.parseBoolean(state.get(prefix + "IsIcon"));
            if (isIcon) {
                try {
                    internalFrame.setIcon(true);
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    internalFrame.setIcon(false);
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
