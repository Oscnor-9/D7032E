package game;

import card.GreenAppleCard;
import player.Player;
import java.util.*;

public class ScoreKeeper {

		private final Map<Player, List<GreenAppleCard>> scores = new HashMap<>();
		private final int winningScore;
		
		public ScoreKeeper(List<Player> players, int winningScore) {
			this.winningScore = winningScore;
			for(Player p : players) {
				scores.put(p, new ArrayList<>());
			}
		}
		
		public void awardPoint(Player player, GreenAppleCard greenCard) {
			if(greenCard != null) {
				scores.get(player).add(greenCard);
			}
		}
		
		public int getScore(Player player) {
			return scores.getOrDefault(player, List.of()).size();
		}
		
		public boolean hasWinner() {
			return scores.values().stream()
					.anyMatch(list -> list.size() >= winningScore);
		}
		
		public Player getWinner() {
			return scores.entrySet().stream()
					.filter(e -> e.getValue().size() >= winningScore)
					.map(Map.Entry::getKey)
					.findFirst()
					.orElse(null);
		}
		
		public int totalPoint() {
			return scores.values().stream()
					.mapToInt(List::size)
					.sum();
		}
}
