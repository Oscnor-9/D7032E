package ui;

/**
 * Simple logging utility for server-side messages
 * <p>
 * Provides colored and categorized console ouput
 * for informational, success, and warning messages
 */
public class ServerLogger {
	
	/** Logs a general informational message */
    public static void info(String msg) {
        System.out.println("[SERVER] " + msg);
    }

    /** Logs a success message, typically after a completed connection or action */
    public static void success(String msg) {
        System.out.println(msg);
    }

    /** Logs a warning or error message to standard error */
    public static void warn(String msg) {
        System.err.println(msg);
    }
}
