package network;

import ui.ClientConsoleUI;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.*;
import java.net.Socket;

public class GameClient {
    private final Socket socket;
    private final String host;
    private final int port;
    private final ClientConsoleUI ui = new ClientConsoleUI();

    public GameClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.host = host;
        this.port = port;
    }
    private Map<String, Integer> parseScores(String data) {
        Map<String, Integer> scores = new LinkedHashMap<>();
        for (String part : data.split(";")) {
            if (part.isBlank()) continue;
            String[] kv = part.split("=");
            scores.put(kv[0], Integer.parseInt(kv[1]));
        }
        return scores;
    }

    public void start() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            ui.showConnected(host, port);

            String line;
            boolean judgeMode = false;

            while ((line = in.readLine()) != null) {
                if ("YOUR_TURN".equals(line)) {
                    ui.showPlayerTurn();
                    String choice = ui.readUserInput();   // ✅ from UI
                    out.println(choice);
                    out.flush();

                } else if ("JUDGE_TURN".equals(line)) {
                    ui.showJudgeTurn();
                    judgeMode = true;

                } else if (judgeMode && "END_SUBMISSIONS".equals(line)) {
                    ui.showJudgePrompt();
                    String choice = ui.readUserInput();   // ✅ from UI
                    out.println(choice);
                    out.flush();
                    judgeMode = false;

                }else if (line.startsWith("SCORES:")) {
                    ui.showScores(parseScores(line.substring(7)));
                }else if (line.startsWith("WINNER:")) {
                    String winnerName = line.substring(7);
                    ui.showMessage("🏆 Winner: " + winnerName);
                }
                else {
                    ui.showMessage(line);
                }
            }
        }
    }

    }

