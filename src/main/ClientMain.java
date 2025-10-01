package main;

import network.GameClient;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        String host = (args.length > 0) ? args[0] : "localhost";
        int port    = (args.length > 1) ? Integer.parseInt(args[1]) : 12345;

        GameClient client = new GameClient(host, port);
        client.start(); // GameClient handles console I/O via ClientConsoleUI-style printing
    }
}
