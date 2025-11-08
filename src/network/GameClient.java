package network;

import ui.ClientConsoleUI;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.*;
import java.net.Socket;
import network.Protocol.*;


	
	/**
	 * Client that connects to the game server
	 * <p>
	 * This class listens for {@link network.Protocol} messages from the server,
	 * displays the through the console UI, and sends back player input.
	 */
public class GameClient {
    private final Socket socket;
    private final String host;
    private final int port;
    private final ClientConsoleUI ui = new ClientConsoleUI();
    
    /**
     * Creates and connects a new client to the specified host and port
     * 
     * @param host the server IP adress
     * @param port server port
     * @throws IOException if the socket cannot connect
     */

    public GameClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.host = host;
        this.port = port;
    }
    
    /**
     * Parses the score string sent by the server
     * <p>
     * Expected format: {@code "Alice=3;Bob=5;"} → {@code {Alice=3, Bob=5}}
     * @param data the encoded score string
     * @return a map of player names to their scores
     */
    private Map<String, Integer> parseScores(String data) {
        Map<String, Integer> scores = new LinkedHashMap<>();
        for (String part : data.split(";")) {
            if (part.isBlank()) continue;
            String[] kv = part.split("=");
            scores.put(kv[0], Integer.parseInt(kv[1]));
        }
        return scores;
    }
    
    /**
     * Starts the main communication loop
     * <p>
     * Listens for messages from the server and reacts according to the
     * {@link network.Protocol} definations until the connection is closed.
     *  
     * @throws IOException if an I/O error occurs on the socket
     */
    public void start() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            ui.showConnected(host, port);

            String line;
            boolean judgeMode = false; //true between JUDGE_TURN and END_SUBMISSIONS

            while ((line = in.readLine()) != null) {
                if (Protocol.YOUR_TURN.equals(line)) {
                    ui.showPlayerTurn();
                    String choice = ui.readUserInput();   
                    out.println(choice);
                    out.flush();

                } else if (Protocol.JUDGE_TURN.equals(line)) {
                    ui.showJudgeTurn();
                    judgeMode = true;

                } else if (judgeMode && Protocol.END_SUBMISSIONS.equals(line)) {
                    ui.showJudgePrompt();
                    String choice = ui.readUserInput();   
                    out.println(choice);
                    out.flush();
                    judgeMode = false;

                }else if (line.startsWith(Protocol.SCORES_PREFIX)) {
                    ui.showScores(parseScores(line.substring(Protocol.SCORES_PREFIX.length())));
                }else if (line.startsWith(Protocol.WINNER_PREFIX)) {
                    String winnerName = line.substring(Protocol.WINNER_PREFIX.length());
                    ui.showMessage("Winner: " + winnerName);
                }
                else {
                    ui.showMessage(line); //fallback for general messages
                }
            }
        }
    }

    }

