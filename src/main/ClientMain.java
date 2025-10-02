package main;

import network.GameClient;
import ui.ClientConsoleUI;


//Main class that starts when a remote Player wants to join a server

public class ClientMain {
    public static void main(String[] args) throws Exception {
        ClientConsoleUI ui = new ClientConsoleUI();

        String host = ui.askServerIp();
        int port = ui.askServerPort();

        GameClient client = new GameClient(host, port);
        client.start();
    }
}
