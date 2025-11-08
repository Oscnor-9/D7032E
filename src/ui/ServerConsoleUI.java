package ui;

import java.util.Scanner;

public class ServerConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    public int askNumRemotePlayers() {
        System.out.print("How many remote players should join? ");
        return scanner.nextInt();
    }

    public void showServerRunning(int port, int numRemote) {
        System.out.println("Server running on port " + port + ", waiting for " + numRemote + " players...");
    }

    public void showRemoteConnected(int index, int total) {
        System.out.println("Remote player connected (" + index + "/" + total + ")");
    }
}
