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
                    ui.showPlayerTurn();
                    String choice = console.readLine();
                    while (console.ready()) console.readLine(); // drain spam
                    out.println(choice);
                    out.flush();

                } else if ("JUDGE_TURN".equals(line)) {
                    ui.showJudgeTurn();
                    judgeMode = true;

                } else if (judgeMode && "END_SUBMISSIONS".equals(line)) {
                    ui.showJudgePrompt();
                    String choice = console.readLine();
                    while (console.ready()) console.readLine();
                    out.println(choice);
                    out.flush();
                    judgeMode = false;

                } 
                else {
                    ui.showMessage(line);
                    System.out.println("hurrdurr");
                }
            }
        }
    }
}
