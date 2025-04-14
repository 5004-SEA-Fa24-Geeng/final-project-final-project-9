package Model.Formatter;

public enum OutputFormat {
    /** Formats to output. */
    JSON, CSV, XML, TXT;


    /**
     * Get the format type from string value.
     * @param value string value of format
     * @return the enum type of format
     */
    public static OutputFormat fromString(String value) {
        for (OutputFormat format : OutputFormat.values()) {
            if (format.toString().equalsIgnoreCase(value)) {
                return format;
            }
        }
        return null;
    }
}