package main;

import network.GameClient;

public class ClientMain {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        try {
            GameClient client = new GameClient(host, port);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

