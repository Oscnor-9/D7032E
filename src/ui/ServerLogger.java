package ui;

public class ServerLogger {
    public static void info(String msg) {
        System.out.println("[SERVER] " + msg);
    }

    public static void success(String msg) {
        System.out.println(msg);
    }

    public static void warn(String msg) {
        System.err.println(msg);
    }
}
