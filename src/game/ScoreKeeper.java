package game;

import card.GreenAppleCard;
import player.Player;
import java.util.*;


/**
 * Handles all score-keeping logic.
 * <p>
 * Internally stores which green cards each player has won and
 * determines when a winning score has been reached
 */
public class ScoreKeeper {

		private final Map<Player, List<GreenAppleCard>> scores = new HashMap<>();
		private final int winningScore;
		
		/**
		 * Creates a new score keeper for the current game
		 * @param players the players participating in the current game
		 * @param winningScore the number of points to win the game
		 */
		public ScoreKeeper(List<Player> players, int winningScore) {
			this.winningScore = winningScore;
			for(Player p : players) {
				scores.put(p, new ArrayList<>());
			}
		}
		
		/**
		 * Awards a point to the given player by adding the specified green card
		 * @param player the player receiving the point
		 * @param greenCard the green card associated with this point
		 */
		public void awardPoint(Player player, GreenAppleCard greenCard) {
			if(greenCard != null) {
				scores.get(player).add(greenCard);
			}
		}
		
		/**
		 * Returns the current score of the given player
		 */
		public int getScore(Player player) {
			return scores.getOrDefault(player, List.of()).size();
		}
		
		/**
		 * Return {@code true} if any player has reached the winning score
		 */
		public boolean hasWinner() {
			return scores.values().stream()
					.anyMatch(list -> list.size() >= winningScore);
		}
		
		/**
		 * Return the first player who has reached the winning score,
		 * or {@code null} if no player has reached the winning score.
		 */
		public Player getWinner() {
			return scores.entrySet().stream()
					.filter(e -> e.getValue().size() >= winningScore)
					.map(Map.Entry::getKey)
					.findFirst()
					.orElse(null);
		}
		
		/**
		 * Returns the total number of points awarded to all players combined
		 */
		public int totalPoint() {
			return scores.values().stream()
					.mapToInt(List::size)
					.sum();
		}
}
