package ui;

import java.util.Scanner;

/**
 * Console-based UI for the server host.
 * <p>
 * Handles setup prompts and connection feedback
 * when waiting for remote players to join
 */
public class ServerConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts for the number of remote players that should connect
     * @return
     */
    public int askNumRemotePlayers() {
        System.out.print("How many remote players should join? ");
        return scanner.nextInt();
    }

    /**
     * Display server startup information including port and expected remote players
	*/
    public void showServerRunning(int port, int numRemote) {
        System.out.println("Server running on port " + port + ", waiting for " + numRemote + " players...");
    }

    /**
     * Notifies when a remote player has connected
     * @param index index of the player that connected
     * @param total total expected remote players
     */
    public void showRemoteConnected(int index, int total) {
        System.out.println("Remote player connected (" + index + "/" + total + ")");
    }
}
