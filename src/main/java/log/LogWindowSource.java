package log;

import java.util.ArrayList;

/**
 * Хранит и управляет логом сообщений. Позволяет регистрировать слушателей,
 * добавлять сообщения в лог и получать доступ к содержимому лога.
 */
public class LogWindowSource
{
    private int queueLength;
    
    private ConcurrentLogBuffer m_messages;
    private final ArrayList<LogChangeListener> listeners;
    private volatile LogChangeListener[] m_activeListeners;


    /**
     * Создает новый экземпляр LogWindowSource с указанной максимальной длиной очереди.
     *
     * @param queueLength максимальная длина очереди сообщений в логе
     */
    public LogWindowSource(int queueLength)
    {
        this.queueLength = queueLength;
        this.m_messages = new ConcurrentLogBuffer(queueLength);
        this.listeners = new ArrayList<>();
    }


    /**
     * Регистрирует нового слушателя для изменений в логе.
     *
     * @param listener слушатель для регистрации
     */
    public void registerListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.add(listener);
            m_activeListeners = null;
        }
    }

    /**
     * Отменяет регистрацию слушателя для изменений в логе.
     *
     * @param listener слушатель для отмены регистрации
     */
    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.remove(listener);
            m_activeListeners = null;
        }
    }

    /**
     * Добавляет новую запись в лог с указанным уровнем и сообщением.
     *
     * @param logLevel   уровень логирования
     * @param strMessage текст сообщения
     */
    public void append(LogLevel logLevel, String strMessage)
    {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        m_messages.add(entry);
        LogChangeListener [] activeListeners = m_activeListeners;
        if (activeListeners == null)
        {
            synchronized (listeners)
            {
                if (m_activeListeners == null)
                {
                    activeListeners = listeners.toArray(new LogChangeListener [0]);
                    m_activeListeners = activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners)
        {
            listener.onLogChanged();
        }
    }

    /**
     * Возвращает текущий размер лога (количество сообщений).
     *
     * @return текущий размер лога
     */
    public int size()
    {
        return m_messages.size();
    }

    /**
     * Возвращает диапазон сообщений из лога, начиная с указанного индекса и указанной длиной.
     *
     * @param startFrom индекс, с которого начинать получение сообщений
     * @param count     количество сообщений для получения
     * @return итерируемый объект с сообщениями из указанного диапазона
     */
    public Iterable<LogEntry> range(int startFrom, int count)
    {
        return m_messages.range(startFrom, count);
    }

    /**
     * Возвращает все сообщения из лога.
     *
     * @return итерируемый объект со всеми сообщениями из лога
     */
    public Iterable<LogEntry> all()
    {
        return m_messages.all();
    }
}
