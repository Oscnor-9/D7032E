package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🍏 Apples to Apples");
        System.out.println("1. Local game");
        System.out.println("2. Host a server");
        System.out.println("3. Join a server");
        System.out.print("Choose option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                LocalMain.main(args);
                break;

            case 2:
                System.out.print("Enter port (default 12345): ");
                String portInput = scanner.nextLine().trim();
                int port = portInput.isEmpty() ? 12345 : Integer.parseInt(portInput);

                // forward to ServerMain with port as argument
                ServerMain.main(new String[]{String.valueOf(port)});
                break;

            case 3:
                System.out.print("Enter server IP: ");
                String ip = scanner.nextLine().trim();

                System.out.print("Enter port (default 12345): ");
                portInput = scanner.nextLine().trim();
                port = portInput.isEmpty() ? 12345 : Integer.parseInt(portInput);

                // forward to ClientMain with ip + port as arguments
                ClientMain.main(new String[]{ip, String.valueOf(port)});
                break;

            default:
                System.out.println("Invalid choice");
        }
    }
}
