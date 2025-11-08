package ui;

public class InputParser {
	
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
