package interfaces;

/**
 * @param <T> a given task to create.
 */
public interface Task<T> {
    /**
     * @return new Task to run.
     */
    T run();
}