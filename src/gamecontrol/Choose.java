package gamecontrol;

/**
 * @param <T> option for value.
 */
public class Choose<T> {
    private String pressedKey;
    private T optionValue;
    private String text;

    /**
     * Constructor.
     *
     * @param key     given key.
     * @param message given message.
     * @param value   given option value.
     */
    public Choose(String key, String message, T value) {
        this.pressedKey = key;
        this.text = message;
        this.optionValue = value;
    }

    /**
     * @return the key.
     */
    public String getPressedKey() {
        return pressedKey;
    }

    /**
     * @return the text.
     */
    public String getText() {
        return text;
    }

    /**
     * @return the value option.
     */
    public T getOptionValue() {
        return optionValue;
    }
}
