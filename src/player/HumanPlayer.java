package player;

import card.Card;
import ui.ChoiceInput;
import ui.InteractiveUI;

import java.util.ArrayList;
import java.util.List;

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

    public InteractiveUI getUI() {
    	return ui;
    }
        
    @Override
    public Card playCard() {
        try {
            ui.showHand(hand);
            ui.promptPlayCard();
            String line = input.readLine();
            int idx = parseIndex(line, hand.size());
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

    public void showHand() {
        ui.showHand(hand);
    }

    @Override
    public Card selectWinner(List<Card> submissions) {
        try {
            ui.showSubmissions(submissions);
            ui.promptJudgeChoice();
            String line = input.readLine();
            int idx = parseIndex(line, submissions.size());
            return submissions.get(idx);
        } catch (Exception e) {
            return submissions.get(0);
        }
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    // -- helpers --
    private int parseIndex(String s, int size) {
        int x = Integer.parseInt(s.trim());
        if (x < 0) return 0;
        if (x >= size) return size - 1;
        return x;
    }
    

}
