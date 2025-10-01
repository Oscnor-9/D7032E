package main;

import network.GameClient;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Ask user for server address
        System.out.print("Enter server IP (default = localhost): ");
        String host = scanner.nextLine().trim();
        if (host.isEmpty()) {
            host = "localhost";
        }

        // Ask user for server port
        System.out.print("Enter server port: ");
        int port = scanner.nextInt();

        // Start client
        GameClient client = new GameClient(host, port);
        client.start();
    }
}
