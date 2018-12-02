package gamecontrol;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Menu;
import interfaces.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @param <T> a given menuAnimation.
 */
public class MenuAnimation<T> implements Menu<T> {

    // Members
    private List<Choose<T>> selections;
    private T currentOption = null;
    private boolean shouldStop;
    private String title;
    private KeyboardSensor keyboardSensor;
    private Sprite background;
    private List<Choose<Menu<T>>> subMenus = new ArrayList<>();
    private AnimationRunner animationRunner;
    private boolean isPressed;

    /**
     * @param title           title.
     * @param ks              keyboard.
     * @param bg              background.
     * @param animationRunner animation runner.
     */
    public MenuAnimation(String title, KeyboardSensor ks, Sprite bg, AnimationRunner animationRunner) {
        this.title = title;
        this.keyboardSensor = ks;
        this.background = bg;
        this.shouldStop = false;
        this.isPressed = true;
        this.currentOption = null;
        this.selections = new LinkedList<>();
        this.animationRunner = animationRunner;
    }


    /**
     * @param key       a given key that was chosen.
     * @param message   the messege we want to show.
     * @param returnVal the action we want to perform.
     */
    public void addSelection(String key, String message, T returnVal) {
        Choose<T> newOption = new Choose<T>(key, message, returnVal);
        this.selections.add(newOption);

    }

    /**
     * @param key     a given key for sub menu.
     * @param message a given message to display.
     * @param subMenu a given submenu.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        subMenus.add(new Choose<>(key, message, subMenu));
    }

    /**
     * @return the state of the menu (it's value).
     */
    public T getStatus() {
        return this.currentOption;
    }

    /**
     * @param d  a given surface.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        background.drawOn(d);
        d.setColor(Color.BLUE);
        d.drawText(200, 100, this.title, 30);
        int initHeight = 200;
        for (Choose<Menu<T>> option : subMenus) {
            d.drawText(200, initHeight, "Press " + option.getPressedKey() + " to " + option.getText(), 30);
        }

        // Showing in our menu all the possible options we have.
        for (Choose<T> options : this.selections) {
            initHeight += 40;
            d.drawText(200, initHeight, "Press " + options.getPressedKey() + " to " + options.getText(), 30);
        }


        // Stoping condition
        for (Choose<T> theOption : this.selections) {
            if (this.keyboardSensor.isPressed(theOption.getPressedKey())) {
                this.currentOption = theOption.getOptionValue();
                this.shouldStop = true;
            }
        }

        // Iteration of the submenu
        for (Choose<Menu<T>> subMenu : this.subMenus) {
            if (this.keyboardSensor.isPressed(subMenu.getPressedKey()) && !isPressed) {
                setStop();
                subMenu.getOptionValue().doOneFrame(d, dt);
                this.animationRunner.run(subMenu.getOptionValue());
                this.currentOption = subMenu.getOptionValue().getStatus();
            } else if (!keyboardSensor.isPressed(subMenu.getPressedKey())) {
                this.isPressed = false;
            }
        }

    }

    /**
     * @return true if the animation should stop. fasle otherwise.
     */
    public boolean shouldStop() {
        return this.shouldStop;
    }

    /**
     * Setting the stop of the submenu iteration.
     */
    public void setStop() {
        this.shouldStop = true;
    }
}
