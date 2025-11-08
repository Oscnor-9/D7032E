package game;

import card.*;
import ui.GameUI;
import player.Player;
import phase.ReplenishPhase;

import java.util.*;

public class Game{
	private Deck<GreenAppleCard> greenDeck;
	private Deck<RedAppleCard> redDeck;
	private List<Player> players;
	private Player currentJudge;
	private GreenAppleCard currentGreenCard;
	
	private final ScoreKeeper scoreKeeper;
	private final SubmissionsManager submissionsManager;
	
	public Game(Deck<GreenAppleCard> greenDeck, Deck<RedAppleCard> redDeck, List<Player> players) {
		this.greenDeck = greenDeck;
		this.redDeck = redDeck;
		this.players = new ArrayList<>(players);
		
		
		Random rand = new Random();
		currentJudge = players.get(rand.nextInt(players.size()));  //Randomize judge at start
		
		int playerCount = players.size();				//Sets winning score based on number of players
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
	
	public Deck<GreenAppleCard> getGreenDeck() { return greenDeck; }
    public Deck<RedAppleCard> getRedDeck() { return redDeck; }

    public List<Player> getPlayers() { return players; }
    public Player getCurrentJudge() { return currentJudge; }
    public GreenAppleCard getCurrentGreenCard() { return currentGreenCard; }
    public void setCurrentGreenCard(GreenAppleCard card) { this.currentGreenCard = card; }
    
    
    public List<Player> getPlayersExcludingJudge() {
        return players.stream()
                      .filter(p -> !p.equals(currentJudge))
                      .toList();
    }
    
    public void nextJudge() {
        int index = players.indexOf(currentJudge);
        currentJudge = players.get((index + 1) % players.size());
    }
    
	 // ------------------------------------------------------------
	 // UI helpers
	 // ------------------------------------------------------------
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

    public void submitRedCard(Player player, RedAppleCard card) {
        submissionsManager.submitRedCard(player, card);
    }  
    
    public Collection<RedAppleCard> getSubmittedCards() {
        return submissionsManager.getSubmittedCards();
    }

    public Player getOwnerOf(Card card) {
    	return submissionsManager.getOwnerOf(card);
    }
    

    public void clearSubmissions() {
    	submissionsManager.clear();
    }

    // ------------------------------------------------------------
    // Scoring
    // ------------------------------------------------------------
    
    public void awardPoint(Player player) {
    	scoreKeeper.awardPoint(player, currentGreenCard);
    }

    public int getScore(Player player) {
        return scoreKeeper.getScore(player);
    }

    public boolean hasWinner() {
        return scoreKeeper.hasWinner();
    }

    public Player getWinner() {
        return scoreKeeper.getWinner();
    } 
    public int totalPoints() {
        return scoreKeeper.totalPoint();
    }  
}

