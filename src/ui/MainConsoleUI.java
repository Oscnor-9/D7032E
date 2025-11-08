package ui;

import java.util.Scanner;

public class MainConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    public int askMode() {
        System.out.println("Choose mode: (1) Local, (2) Server, (3) Client");
        return scanner.nextInt();
    }

    public int askNumOfBots() {
        System.out.print("How many bots should be in the game? ");
        return scanner.nextInt();
    }

    public void showInvalidChoice() {
        System.out.println("Invalid choice");
    }
    public void showInvalidBotCount() {
        System.out.println("You must add at least 2 bots for a valid game.");
    }
}
