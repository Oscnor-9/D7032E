package main;

import network.GameClient;

public class ClientMain {
    public static void main(String[] args) {
        try {
            GameClient client = new GameClient("localhost", 12345);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
