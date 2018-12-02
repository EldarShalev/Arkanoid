package interfaces;

import biuoop.DrawSurface;

/**
 * This interface controls the animation.
 */
public interface Animation {
    /**
     * @param d  a given surface.
     * @param dt the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * @return true if the animation should stop. fasle otherwise.
     */
    boolean shouldStop();
}