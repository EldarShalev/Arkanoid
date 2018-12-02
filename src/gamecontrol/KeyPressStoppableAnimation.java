package gamecontrol;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * This class is for animations by key.
 */
public class KeyPressStoppableAnimation implements Animation {
    // Members
    private Animation decoreatedAnimation;
    private KeyboardSensor keyboardSensor;
    private String keyToStop;
    private boolean shouldStop;
    private boolean isPressed;

    /**
     * Consturctor.
     *
     * @param sensor    a given sensor keyboard.
     * @param key       a given string key to act.
     * @param animation an animation to run according to the key.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.isPressed = true;
        this.keyboardSensor = sensor;
        this.decoreatedAnimation = animation;
        this.shouldStop = false;
        this.keyToStop = key;
    }

    /**
     * @param d  a given surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.decoreatedAnimation.doOneFrame(d, dt);
        if ((this.keyboardSensor.isPressed(this.keyToStop)) && (!this.isPressed)) {
            this.shouldStop = true;
        } else if (!this.keyboardSensor.isPressed(this.keyToStop)) {
            this.isPressed = false;
        }
    }

    /**
     * @return true if the animation should stop. fasle otherwise.
     */
    public boolean shouldStop() {
        return this.shouldStop;
    }

}

