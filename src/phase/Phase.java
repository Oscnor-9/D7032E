package phase;

import game.Game;

/**
 * Implementations of this interface perform one part of the round logic
 * (Drawing cards, letting player play, judging and replenishing)
 */
public interface Phase {
	/**
	 * Execute the phase of the given game instance
	 * @param game the game context on which the phase operates
	 */
	void execute(Game game);
}
