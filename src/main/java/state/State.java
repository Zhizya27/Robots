package state;

/**
 * интерфейс для работы с сохранением/восстановлением состояний
 */
public interface State {
    void saveState();
    void restoreState();

}
