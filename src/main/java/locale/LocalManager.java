package locale;
import gui.MainApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Способствует локализации приложения
 */
public class LocalManager {
    private static ResourceBundle resourceBundle; // хранит ресурсы для локализации
    private MainApplicationFrame applicationFrame;

    /**
     * Получает локализованную строку для указанного ключа.
     *
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
     *
     * @param locale - выбранный язык
     */
    public static void setLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }

    /**
     * Обновляет локализацию всех окон в приложении.
     */
    public static void updateLanguage() {
        Frame[] frames = JFrame.getFrames(); // Получаем все фреймы приложения
        for (Frame frame : frames) {
            updateFrameLanguage(frame);
        }
    }

    /**
     * Обновляет локализацию для указанного фрейма и его компонентов.
     */
    private static void updateFrameLanguage(Frame frame) {
        if (frame instanceof LocalManagerInterface) {
            ((LocalManagerInterface) frame).localization();
        }
        if (frame instanceof JFrame) {
            updateComponentsLanguage(((JFrame) frame).getRootPane());
        }
    }

    /**
     * Обновляет локализацию для компонентов внутри указанного контейнера.
     */
    private static void updateComponentsLanguage(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof LocalManagerInterface) {
                ((LocalManagerInterface) component).localization();
            }
            if (component instanceof Container) {
                updateComponentsLanguage((Container) component);
            }
        }
    }
}
