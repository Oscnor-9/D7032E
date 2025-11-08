package card;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A generic deck that holds cards of any type extending {@link Card}
 * <p>
 * Provides methods to shuffle, draw and inspect cards in the deck
 * @param <T> the tyype of card contained in the deck
 */
 
public class Deck<T extends Card> {
    private ArrayList<T> cards;

    /**
     * Creates a new deck containing the given list of cards
     * The list is copied to avoid external modifications and the shuffled
     * @param cards the initial collection of cards
     */
    public Deck(ArrayList<T> cards) {
        this.cards = new ArrayList<>(cards); 
        shuffle();
    }

    /** Randomly shuffle the deck */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draw the top card by removing and returning a card from the list
     * @return the drawn card or {@code null} if the deck is empty
     */
    public T draw() {
        if (cards.isEmpty()) {
            return null; // or throw an exception
        }
        return cards.remove(0); 
    }
    
    /**
     * Adds a single card to the bottom of the deck
     * @param card the card to add
     */
    public void addCard(T card) {
        cards.add(card);
    }
    
    /** returns if the deck is empty    */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /** Returns the number of cards in the deck*/
    public int size() {
        return cards.size();
    }
    
    /**
     * Returns the card at the given position without removing it
     * @param index the index of the card
     * @return the card at the specified position
     */
    public T get(int index) {
        return cards.get(index);
    }
}
