package ui;

import java.util.Scanner;

/**
 * Console-based user interface for selecting the game startup mode.
 * <p>
 * Used at program launch to configure whether the player
 * hosts a a local game, runs a server, or joins as a client
 */
public class MainConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Asks the user which game mode to start
     * @return 1 for local, 2 for server, 3 for client
     */
    public int askMode() {
        System.out.println("Choose mode: (1) Local, (2) Server, (3) Client");
        return scanner.nextInt();
    }

    /**
     * Prompts the user to choose number of bots
     * @return number of bots chosen by the player
     */
    public int askNumOfBots() {
        System.out.print("How many bots should be in the game? ");
        return scanner.nextInt();
    }

    /** Displays and error message for invalid menu choices */
    public void showInvalidChoice() {
        System.out.println("Invalid choice");
    }
    
    /** Displays an error message when too few both are selected */
    public void showInvalidBotCount() {
        System.out.println("You must add at least 2 bots for a valid game.");
    }
}
