package ui;

import java.util.List;
import card.Card;
import player.Player;
import game.Game;

public interface InteractiveUI extends GameUI {
    void promptPlayCard();
    void promptJudgeChoice();
    void showHand(List<Card> hand);
    void showJudgeDecision(Player judge, Card winningCard, Player winner, List<Player> players, Game game);
}