package gui;

import game.CoordinatesWindow;
import game.GameWindow;
import log.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Locale;


public class MenuBar extends JMenuBar {

    private MainApplicationFrame applicationFrame;
     JMenu exitMenu;
     JMenu testMenu;

     JMenu lookAndFeelMenu;

     JMenu languageMenu;

     JMenuItem addLogMessageItem;

     JMenuItem crossplatformLookAndFeel;

     JMenuItem systemLookAndFeel;

     JMenuItem exitMenuItem;

     LogWindow logWindow;
     CoordinatesWindow coordinatesWindow;
     GameWindow gameWindow;


    /**
     * Конструктор
     * @param applicationFrame - фрейм приложения
     * @param logWindow - окно протоколов
     * @param coordinatesWindow - окно с координатами робота
     * @param gameWindow - игровое окно
     */
    public MenuBar(MainApplicationFrame applicationFrame,  LogWindow logWindow, CoordinatesWindow coordinatesWindow, GameWindow gameWindow) {
        JMenuBar menuBar = new JMenuBar();
        buildLookAndFeelMenu(menuBar);
        buildTestMenu(menuBar);
        buildExitMenu(menuBar);
        buildLanguageMenu(menuBar);
        this.applicationFrame = applicationFrame;
        this.logWindow = logWindow;
        this.coordinatesWindow = coordinatesWindow;
        this.gameWindow = gameWindow;
        this.add(menuBar);

    }



    /**
     * Строит меню "Режим отображения", включающее опции для изменения внешнего вида приложения.
     *
     * @return Меню "Режим отображения"
     */
    private void buildLookAndFeelMenu(JMenuBar menuBar) {
        lookAndFeelMenu = new JMenu(LocalManager.getStringLocal("lookAndFeelMenu"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        systemLookAndFeel = new JMenuItem(LocalManager.getStringLocal("systemLookAndFeel"), KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        crossplatformLookAndFeel = new JMenuItem(LocalManager.getStringLocal("crossplatformLookAndFeel"), KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        menuBar.add(lookAndFeelMenu);
    }

    /**
     * Строит меню "Тесты", включающее опции для выполнения тестовых команд.
     *
     * @return Меню "Тесты"
     */
    private void buildTestMenu(JMenuBar menuBar) {
        testMenu = new JMenu(LocalManager.getStringLocal("testMenu"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        addLogMessageItem = new JMenuItem(LocalManager.getStringLocal("addLogMessageItem"), KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        testMenu.add(addLogMessageItem);

        menuBar.add(testMenu);
    }


    /**
     * Строит меню "Выход", позволяющее закрыть приложение.
     */
    private void buildExitMenu(JMenuBar menuBar) {
        exitMenu = new JMenu(LocalManager.getStringLocal("exitMenu"));
        exitMenu.setMnemonic(KeyEvent.VK_X);

        exitMenuItem = new JMenuItem(LocalManager.getStringLocal("exitMenuItem"), KeyEvent.VK_Z);
        exitMenuItem.addActionListener((event) -> { // Добавляется слушатель событий для пункта меню "Закрыть приложение"
                    applicationFrame.confirmAndExit();
                });
        exitMenu.add(exitMenuItem);
        //this.add(exitMenu); // добавляем в главное меню приложения
        menuBar.add(exitMenu);
    }


    private void buildLanguageMenu(JMenuBar menuBar) {
        languageMenu = new JMenu(LocalManager.getStringLocal("languageMenu"));
        languageMenu.setMnemonic(KeyEvent.VK_X);
        JMenuItem russianLenguage = new JMenuItem("Русский", KeyEvent.VK_Z);
        russianLenguage.addActionListener((event) -> {
            LocalManager.setLocale(new Locale("ru"));
            localization();
        });
        languageMenu.add(russianLenguage);

        JMenuItem translitLenguage = new JMenuItem("Транслит", KeyEvent.VK_Z);
        translitLenguage.addActionListener((event) -> {
            LocalManager.setLocale(new Locale("en"));
            localization();
        });
        languageMenu.add(translitLenguage);
        menuBar.add(languageMenu);
    }

    private void localization() {
        LocalManager.localization(this);
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
