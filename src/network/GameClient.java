package network;

import ui.ClientConsoleUI;
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

    public void start() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            ui.showConnected(host, port);

            String line;
            boolean judgeMode = false;

            while ((line = in.readLine()) != null) {

                if ("YOUR_TURN".equals(line)) {
                    // Player must pick a card
                    ui.showPlayerTurn();
                    String choice = console.readLine();
                    while (console.ready()) console.readLine(); // drain spam
                    out.println(choice);
                    out.flush();

                } else if ("JUDGE_TURN".equals(line)) {
                    // Remote player is judge this round
                    ui.showJudgeTurn();
                    judgeMode = true;

                } else if (judgeMode && "END_SUBMISSIONS".equals(line)) {
                    // End of submissions block → now judge must choose
                    ui.showJudgePrompt();
                    String choice = console.readLine();
                    while (console.ready()) console.readLine();
                    out.println(choice);
                    out.flush();
                    judgeMode = false;

                } else if ("HAND".equals(line)) {
                    // Show player hand (multi-line block)
                    StringBuilder sb = new StringBuilder("Your hand:\n");
                    while ((line = in.readLine()) != null && !"END_HAND".equals(line)) {
                        sb.append(line).append("\n");
                    }
                    ui.showMessage(sb.toString());

                } else if ("SUBMISSIONS".equals(line)) {
                    // Show submissions (multi-line block)
                    StringBuilder sb = new StringBuilder("Submitted cards:\n");
                    while ((line = in.readLine()) != null && !"END_SUBMISSIONS".equals(line)) {
                        sb.append(line).append("\n");
                    }
                    ui.showMessage(sb.toString());

                } else if (line.startsWith("MESSAGE:")) {
                    // Generic server message
                    ui.showMessage(line.substring(8));

                } else if (line.startsWith("WINNER:")) {
                    // End of game winner announcement
                    ui.showMessage("🏆 " + line.substring(7));

                } else {
                    // Fallback: print any unrecognized lines
                    ui.showMessage(line);
                }
            }
        }
    }
}
