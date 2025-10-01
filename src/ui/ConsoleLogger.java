package ui;

public class ConsoleLogger {
    public static void info(String msg) {
        System.out.println("[INFO] " + msg);
    }

    public static void warn(String msg) {
        System.out.println("[WARN] " + msg);
    }
}