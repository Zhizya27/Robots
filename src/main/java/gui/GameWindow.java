package gui;

import state.State;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.*;


/**
 * Класс GameWindow представляет внутреннее окно с игровым полем.
 */
public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;

    /**
     * Создает новый экземпляр GameWindow.
     */
    public GameWindow() 
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

}
