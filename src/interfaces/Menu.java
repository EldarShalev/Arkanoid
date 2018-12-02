package interfaces;

/**
 * This class for menu interface.
 *
 * @param <T> a generic type.
 */
public interface Menu<T> extends Animation {
    /**
     * @param key       a given key that was chosen.
     * @param message   the messege we want to show.
     * @param returnVal the action we want to perform.
     */


    /**
     * @param key       a given key to the menu.
     * @param message   a given message to display.
     * @param returnVal the return value of the option selected.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @param key     a given key for sub menu.
     * @param message a given message to display.
     * @param subMenu a given submenu.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * @return the state of the menu (it's value).
     */
    T getStatus();

}
