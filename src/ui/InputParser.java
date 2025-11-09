package ui;


/**
 * Utility class for paring and validating numeric player inputs
 */
public class InputParser {
	
	/**
	 * Parses a string index
	 * @param s the string to parse
	 * @param size Upper bound of the valid input
	 * @return a valid index between {@code 0} and {@code size -1}
	 */
    public static int parseIndex(String s, int size) {
        try {
            int x = Integer.parseInt(s.trim());
            if (x < 0) return 0;
            if (x >= size) return size - 1;
            return x;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
