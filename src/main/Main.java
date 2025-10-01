package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import io.FileCardLoader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // ✅ Load decks only once
        Deck<GreenAppleCard> greenDeck = new Deck<>(
            FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new)
        );
        Deck<RedAppleCard> redDeck = new Deck<>(
            FileCardLoader.loadCards("redApples.txt", RedAppleCard::new)
        );

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose mode: (1) Local, (2) Server, (3) Client");
        int choice = scanner.nextInt();
        
        int numOfBots = 0; // default, ignored for client

        // Ask for bots only if Local or Server
        if (choice == 1 || choice == 2) {
            System.out.print("How many bots should be in the game? ");
            numOfBots = scanner.nextInt();
        }

        switch (choice) {
            case 1 -> LocalMain.start(greenDeck, redDeck, numOfBots);
            case 2 -> ServerMain.start(greenDeck, redDeck, numOfBots);
            case 3 -> ClientMain.main(new String[]{}); // client doesn’t need bots
            default -> System.out.println("Invalid choice");
        }
    }
}