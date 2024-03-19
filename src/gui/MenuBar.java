package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MenuBar extends JMenuBar {

    private MainApplicationFrame applicationFrame;

    /**
     * Конструктор
     * @param applicationFrame - фрейм приложения
     */
    public MenuBar(MainApplicationFrame applicationFrame) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(buildLookAndFeelMenu());
        menuBar.add(buildTestMenu());
        this.applicationFrame = applicationFrame;
    }

    /**
     * Строит меню "Режим отображения", включающее опции для изменения внешнего вида приложения.
     *
     * @return Меню "Режим отображения"
     */
    private JMenu buildLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        return lookAndFeelMenu;
    }

    /**
     * Строит меню "Тесты", включающее опции для выполнения тестовых команд.
     *
     * @return Меню "Тесты"
     */
    private JMenu buildTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        testMenu.add(addLogMessageItem);

        return testMenu;
    }


    /**
     * Строит меню "Выход", позволяющее закрыть приложение.
     *
     * @return Меню "Выход"
     */
    private JMenu buildExitMenu() {
        JMenu exitMenu = new JMenu("Выход");
        exitMenu.setMnemonic(KeyEvent.VK_X);

        JMenuItem exitMenuItem = new JMenuItem("Закрыть приложение", KeyEvent.VK_Z);
        exitMenuItem.addActionListener((event) -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Вы уверены, что хотите закрыть приложение?", "Подтверждение выхода",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                        new WindowEvent(this.applicationFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        exitMenu.add(exitMenuItem);
        return exitMenu;
    }

    /**
     * Устанавливает выбранную схему отображения для приложения.
     *
     * @param className Полное имя класса схемы отображения
     */
    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
