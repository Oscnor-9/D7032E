package ui;

public class NetworkLogger {
    public static void error(String msg, Exception e) {
        System.err.println("[NETWORK ERROR] " + msg);
        if (e != null) e.printStackTrace(System.err);
    }
}
