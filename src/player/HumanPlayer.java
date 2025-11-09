package player;

import card.Card;
import ui.ChoiceInput;
import ui.InteractiveUI;
import ui.InputParser;
import java.util.ArrayList;
import java.util.List;

/**
 * A local human-controlled player using an {@link InteractiveUI}
 * and a {@link ChoiceInput} for interaction
 */
public class HumanPlayer implements Player {
    private final String name;
    private final InteractiveUI ui;          // prints only
    private final ChoiceInput input;  // reads input
    private final List<Card> hand = new ArrayList<>();

    public HumanPlayer(String name, InteractiveUI ui, ChoiceInput input) {
        this.name = name;
        this.ui = ui;
        this.input = input;
    }

    /**
     * Return the UI for this human player
     */
    public InteractiveUI getUI() {
    	return ui;
    }
        
    @Override
    public Card playCard() {
        try {
            ui.showHand(hand);
            ui.promptPlayCard();
            String line = input.readLine();
            int idx = InputParser.parseIndex(line, hand.size());
            return hand.remove(idx);
        } catch (Exception e) {
            // fallback
            return hand.remove(0);
        }
    }

    @Override
    public void receiveCard(Card c) {
        hand.add(c);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Method to display the current hand to the player
     */
    public void showHand() {
        ui.showHand(hand);
    }

    @Override
    public Card selectWinner(List<Card> submissions) {
        try {
            ui.showSubmissions(submissions);
            ui.promptJudgeChoice();
            String line = input.readLine();
            int idx = InputParser.parseIndex(line, submissions.size());
            return submissions.get(idx);
        } catch (Exception e) {
            return submissions.get(0);
        }
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }


}
