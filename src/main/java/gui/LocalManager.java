package gui;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Способствует локализации приложения
 */
public class LocalManager {
    private static ResourceBundle resourceBundle;

    /**
     * Получает локализованную строку для указанного ключа.
     * @param key -  ключ строки
     * @return локализованная строка
     */
    public static String getStringLocal(String key) {
        if (resourceBundle == null) {
            setLocale(Locale.getDefault()); // getDefault() возвращает тот язык, который в операционной системе
        }
        return resourceBundle.getString(key);
    }

    /**
     * Устанавливает выбранный язык.
     * @param locale - выбранный язык
     */
     static void setLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    /**
     * локализует текстовые компоненты приложения
     */
    public static void localization(MenuBar menuBar){
        menuBar.exitMenu.setText(LocalManager.getStringLocal("exitMenu"));
        menuBar.testMenu.setText(LocalManager.getStringLocal("testMenu"));
        menuBar.lookAndFeelMenu.setText(LocalManager.getStringLocal("lookAndFeelMenu"));
        menuBar.languageMenu.setText(LocalManager.getStringLocal("languageMenu"));
        menuBar.addLogMessageItem.setText(LocalManager.getStringLocal("addLogMessageItem"));
        menuBar.crossplatformLookAndFeel.setText(LocalManager.getStringLocal("crossplatformLookAndFeel"));
        menuBar.systemLookAndFeel.setText(LocalManager.getStringLocal("systemLookAndFeel"));
        menuBar.exitMenuItem.setText(LocalManager.getStringLocal("exitMenuItem"));
        menuBar.logWindow.setTitle(LocalManager.getStringLocal("logWindow"));
        menuBar.gameWindow.setTitle(LocalManager.getStringLocal("playingField"));
        menuBar.coordinatesWindow.setTitle(LocalManager.getStringLocal("robotCoordinates"));
    }

}
