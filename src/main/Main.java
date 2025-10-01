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
        
        System.out.println("How many bots should be in the game?");
        int numOfBots = scanner.nextInt();

        switch (choice) {
            case 1 -> LocalMain.start(greenDeck, redDeck, numOfBots);
            case 2 -> ServerMain.start(greenDeck, redDeck, numOfBots);
            case 3 -> ClientMain.main(new String[]{}); // client doesn’t need decks
        }
    }
}
