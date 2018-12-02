package creationfromfile;

/**
 * Row file for given line.
 */
public class Row {
    private Boolean isHeader = false;
    private String name;
    private String value;

    /**
     * @param str the string of the line.
     */
    public Row(String str) {
        Integer separatorIndex = str.indexOf(":");
        if (separatorIndex >= 0) {
            this.name = str.substring(0, separatorIndex);
            this.value = str.substring(separatorIndex + 1, str.length());
        } else {
            this.isHeader = true;
            this.name = str;
        }
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name1 set the name.
     */
    public void setName(String name1) {
        this.name = name1;
    }

    /**
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value1 new value to set.
     */
    public void setValue(String value1) {
        this.value = value1;
    }

    /**
     * @return is header or not.
     */
    public Boolean isHeader() {
        return isHeader;
    }

    /**
     * @return new string according the header.
     */
    @Override
    public String toString() {
        if (isHeader) {
            return name;
        } else {
            return String.format("%s:%s", name, value);
        }

    }
}
