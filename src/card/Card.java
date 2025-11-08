package card;

/**
 * Abstract base class representing a generic card used in the game.
 * <p>
 * Both {@link RedAppleCard} and {@link GreenAppleCard} extend this class.
 */

public abstract class Card {
	/** The text printed on the card */
    protected String text;
    
    /**
     * Creates a card with the given text
     * @param text text the txt or phrase on the card
     */
    public Card(String text) { this.text = text; }
    
    /**
     * Returns the text printed on this card
     */
    public String getText() { return text; }
    @Override 
    public String toString() { return text; }
}
