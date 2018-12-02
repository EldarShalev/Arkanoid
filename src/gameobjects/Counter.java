package gameobjects;

/**
 * This is a class for Counter function.
 */
public class Counter {
    // Members
    private int number1;

    /**
     * Constructor.
     *
     * @param number a given number.
     */
    public Counter(int number) {
        this.number1 = number;
    }

    /**
     * Add number to current count.
     *
     * @param number a given number.
     */
    public void increase(int number) {
        this.number1 += number;
    }


    /**
     * Subtract number from current count.
     *
     * @param number a given number.
     */
    public void decrease(int number) {
        this.number1 -= number;
    }

    /**
     * @return the current count.
     */
    public int getValue() {
        return this.number1;
    }

    /**
     * @param number2 a given number to init.
     */
    public void setNumber(int number2) {
        this.number1 = number2;
    }
}