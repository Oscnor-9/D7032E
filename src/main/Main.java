package main;

import card.Deck;
import card.GreenAppleCard;
import card.RedAppleCard;
import io.FileCardLoader;
import ui.MainConsoleUI;

public class Main {
    public static void main(String[] args) throws Exception {
        // ✅ Load decks only once
        Deck<GreenAppleCard> greenDeck = new Deck<>(
            FileCardLoader.loadCards("greenApples.txt", GreenAppleCard::new)
        );
        Deck<RedAppleCard> redDeck = new Deck<>(
            FileCardLoader.loadCards("redApples.txt", RedAppleCard::new)
        );

        MainConsoleUI ui = new MainConsoleUI();

        int choice = ui.askMode();
        int numOfBots = 0;

        if (choice == 1 || choice == 2) {
            do {
                numOfBots = ui.askNumOfBots();
                if (numOfBots < 2) {
                    ui.showInvalidBotCount();
                }
            } while (numOfBots < 2);
        }

        switch (choice) {
            case 1 -> LocalMain.start(greenDeck, redDeck, numOfBots);
            case 2 -> ServerMain.start(greenDeck, redDeck, numOfBots);
            case 3 -> ClientMain.main(new String[]{});
            default -> ui.showInvalidChoice();
        }
    }
}
