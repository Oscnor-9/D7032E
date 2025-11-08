package game;

import card.*;
import ui.GameUI;
import player.Player;
import phase.ReplenishPhase;

import java.util.*;



/**
 * Core game engine that coordinates decks, players, phases, scoring
 * and submissions.
 */
public class Game{
	private Deck<GreenAppleCard> greenDeck;
	private Deck<RedAppleCard> redDeck;
	private List<Player> players;
	private Player currentJudge;
	private GreenAppleCard currentGreenCard;
	
	private final ScoreKeeper scoreKeeper;
	private final SubmissionsManager submissionsManager;
	
	/**
	 * Creates a new game with the given decks and players
	 * <p> 
	 * The initial judge is chosen at random, and the winning score
	 * is derived from the number of players  
	 * @param greenDeck the deck of green apple cards
	 * @param redDeck the deck of red apple cards
	 * @param players the participating players
	 */
	
	public Game(Deck<GreenAppleCard> greenDeck, Deck<RedAppleCard> redDeck, List<Player> players) {
		this.greenDeck = greenDeck;
		this.redDeck = redDeck;
		this.players = new ArrayList<>(players);
		
		//Randomize judge at start
		Random rand = new Random();
		currentJudge = players.get(rand.nextInt(players.size()));  
		
		//Determine winning score based on amount of players
		int playerCount = players.size();				
		int winningScore;
		if (playerCount >= 4 && playerCount <= 7) {
		    winningScore = 12 - playerCount;
		} 
		else if (playerCount >= 8) {
		    winningScore = 4;
		} 
		else {
		    winningScore = 8; // default fallback
		}
		
		this.scoreKeeper = new ScoreKeeper(this.players, winningScore);
		this.submissionsManager = new SubmissionsManager();
		
	}
	
    // ------------------------------------------------------------
    // Core game loop
    // ------------------------------------------------------------
	
	/**
	 * Starts the game loop and runs until a winner is found
	 * <p>
	 * Each round execute the Draw, Play, Judge and Replenish phases,
	 * the rotates the judge
	 */
	
	public void start() {
	    // Rule 4: deal 7 red apples to all players
	    new ReplenishPhase().execute(this);

	    // Define the phases in round order
	    List<phase.Phase> phases = List.of(
	        new phase.DrawPhase(),
	        new phase.PlayPhase(),
	        new phase.JudgePhase(),
	        new phase.ReplenishPhase()
	    );

	    while (!hasWinner()) {
	        for (phase.Phase p : phases) {
	            p.execute(this);
	        }
	        clearSubmissions(); // discard used red cards
	        nextJudge();        // rotate judge
	    }

	    Player winner = getWinner();

	 // Announce game over to all players
	    for (Player p : players) {
	    	if (p instanceof player.HumanPlayer hp) {
	    		hp.getUI().showWinner(winner);
	    		hp.getUI().showScores(players, this::getScore);
	    		hp.getUI().showMessage("Game Over – Thanks for playing!");
	     }
	    	if (p instanceof player.RemotePlayer rp) {
	    		rp.getUI().showWinner(winner);
	    		rp.getUI().showScores(players, this::getScore);
	    		rp.getUI().showMessage("Game Over – Thanks for playing!");
	     }
	 }
	}
	
	// ------------------------------------------------------------
    // Gameplay helpers
    // ------------------------------------------------------------
	
	public Deck<GreenAppleCard> getGreenDeck() { 
		return greenDeck;
		}
    public Deck<RedAppleCard> getRedDeck() { 
    	return redDeck; 
    	}

    public List<Player> getPlayers() { 
    	return players; 
    	}
    public Player getCurrentJudge() { 
    	return currentJudge; 
    	}
    public GreenAppleCard getCurrentGreenCard() { 
    	return currentGreenCard; 
    	}
    public void setCurrentGreenCard(GreenAppleCard card) { 
    	this.currentGreenCard = card; 
    	}
    
    
    /**
     * Returns all players except the current judge
     */
    public List<Player> getPlayersExcludingJudge() {
        return players.stream()
                      .filter(p -> !p.equals(currentJudge))
                      .toList();
    }
    /**
     * Advances the {@code currentJudge} to the next player in the list
     */
    public void nextJudge() {
        int index = players.indexOf(currentJudge);
        currentJudge = players.get((index + 1) % players.size());
    }
    
	 // ------------------------------------------------------------
	 // UI helpers
	 // ------------------------------------------------------------
    
    /**
     * Applies a UI action to all players that have an {@link GameUI}
     * @param action a consumer that operates on each of the player's UI
     */
	 public void broadcast(java.util.function.Consumer<ui.GameUI> action) {
	     for (Player p : players) {
	         if (p.getUI() != null) {
	             action.accept(p.getUI());
	         }
	     }
	 }
    
    
    // ------------------------------------------------------------
    // Submissions
    // ------------------------------------------------------------

	 /**
	  * Registers a  red card submission for the given player in the round
	  */
    public void submitRedCard(Player player, RedAppleCard card) {
        submissionsManager.submitRedCard(player, card);
    }  
    
    /**
     * Returns all red cards that have been submitted in the current round
     */
    public Collection<RedAppleCard> getSubmittedCards() {
        return submissionsManager.getSubmittedCards();
    }
    
    /**
     * Looks up the player who submitted the given card in the current round
     * @param card the winning card
     * @return the player who submitted it
     */
    public Player getOwnerOf(Card card) {
    	return submissionsManager.getOwnerOf(card);
    }
    
    /**
     * Clears all submissions in the end of the round
     */
    public void clearSubmissions() {
    	submissionsManager.clear();
    }

    // ------------------------------------------------------------
    // Scoring
    // ------------------------------------------------------------
    
    /**
     * Award a point to the winner of the round
     */
    public void awardPoint(Player player) {
    	scoreKeeper.awardPoint(player, currentGreenCard);
    }
    
    /**
     * Return the current score for a given player
     */
    public int getScore(Player player) {
        return scoreKeeper.getScore(player);
    }

    /**
     * Returns {@code true} if any of the players has reached a winning score
     */
    public boolean hasWinner() {
        return scoreKeeper.hasWinner();
    }

    /**
     * Returns the winning player,
     * or {@code null} if no winner exist yet
     */
    public Player getWinner() {
        return scoreKeeper.getWinner();
    } 
    
    /**
     * Return the total num of points awarded to all players combined
     */
    public int totalPoints() {
        return scoreKeeper.totalPoint();
    }  
}

