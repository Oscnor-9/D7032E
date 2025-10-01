package ui;

import card.Card;
import player.Player;
import java.util.List;

public interface GameUI {
    void showWinner(Player winner);
    void showScores(List<Player> players, java.util.function.Function<Player,Integer> scoreFn);
    void announceJudge(Player judge);
    void showSubmissions(List<Card> submissions);
    void promptPlayCard();
    void promptJudgeChoice();
    void showHand(List<Card> hand);
}
