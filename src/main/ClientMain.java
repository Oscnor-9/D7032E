package main;

import network.GameClient;
import ui.ClientConsoleUI;


/**
 * Entry point for a remote client that wants to connect to an existing server
 */

public class ClientMain {
	/**
	 * Asks the user for server IP and port, the connects and start the client loop
	 * @param args unused
	 * @throws Exception if the client cannot connect or run
	 */
    public static void main(String[] args) throws Exception {
        ClientConsoleUI ui = new ClientConsoleUI();

        String host = ui.askServerIp();
        int port = ui.askServerPort();

        GameClient client = new GameClient(host, port);
        client.start();
    }
}
