package card;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generic deck that holds cards of any type extending Card.
 */
public class Deck<T extends Card> {
    private ArrayList<T> cards;

    public Deck(ArrayList<T> cards) {
        this.cards = new ArrayList<>(cards); // copy list
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public T draw() {
        if (cards.isEmpty()) {
            return null; // or throw an exception
        }
        return cards.remove(0); 
    }

    public void addCard(T card) {
        cards.add(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }
    public T get(int index) {
        return cards.get(index);
    }
}
