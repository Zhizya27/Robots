package log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Потокобезопасный буфер для хранения записей лога
 */
    class ConcurrentLogBuffer<T> {

    private final int max_size_buffer; // максимальное кол-во элементов в буфере
    private final ConcurrentLinkedDeque<T> buffer; //экземпляр, явл потокобезопасной реализацией ConcurrentLinkedDeque(двунаправленнной очереди)
    private final ReentrantLock lock;

    /**
     * Создает новый экземпляр буфера с указанной емкостью
     *
     * @param max_size_buffer максимальный размер буфера
     */
    public ConcurrentLogBuffer(int max_size_buffer) {
        this.max_size_buffer = max_size_buffer;
        this.buffer = new ConcurrentLinkedDeque<>();
        this.lock = new ReentrantLock();
    }

    /**
     * Добавляет элемент в буфер
     *
     * @param item элемент для добавления
     */
    public void add(T item) {
        lock.lock();
        try {
            if (buffer.size() >= max_size_buffer) {
                buffer.pollFirst(); // O(1)
            }
            buffer.addLast(item); // O(1)
        } finally {
            lock.unlock();
        }
    }

    /**
     * Получает элементы из буфера в диапазоне от startIndex до endIndex
     *
     * @param startIndex индекс начала диапазона (включительно)
     * @param endIndex   индекс конца диапазона (исключительно)
     * @return итерируемый объект, содержащий элементы из указанного диапазона
     */
    public Iterable<T> range(int startIndex, int endIndex) {
        lock.lock();
        try {
            if (startIndex < 0 || startIndex >= buffer.size() || endIndex <= startIndex) {
                return Collections.emptyList();
            }
            int indexTo = Math.min(startIndex + endIndex, buffer.size());
            return new ArrayList<>(buffer).subList(startIndex, indexTo);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Получает все элементы из буфера
     *
     * @return итерируемый объект, содержащий все элементы буфера
     */
    public Iterable<T> all() {
        return buffer;
    }

    /**
     * Получает текущий размер буфера
     *
     * @return текущий размер буфера
     */
    public int size() {
        return buffer.size();
    }

}
