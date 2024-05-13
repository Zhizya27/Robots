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
     * @param key ключ строки
     * @return локализованная строка
     */
    public static String getString(String key) {
        if (resourceBundle == null) {
            setLocale(Locale.getDefault()); // Загружаем язык по умолчанию, если не был установлен
        }
        return resourceBundle.getString(key);
    }

    /**
     * Устанавливает выбранный язык.
     * @param locale выбранный язык
     */
     static void setLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
    }



}
