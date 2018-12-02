package interfaces;

/**
 * This is the interface of the notifier hit.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     *
     * @param hl a given hit listener.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of Listeners.
     *
     * @param hl a given hit listener.
     */
    void removeHitListener(HitListener hl);
}