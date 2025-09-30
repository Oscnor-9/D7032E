package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
    private final Socket socket;

    public GameClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
    }

    public void start() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            String line;
            boolean judgeMode = false;

            System.out.println("Connected to server!");

            while ((line = in.readLine()) != null) {
                if ("YOUR_TURN".equals(line)) {
                    System.out.print("Choose index: ");
                    String choice = console.readLine();

                    // 🔧 non-blocking drain of extra spammed lines
                    while (console.ready()) {
                        console.readLine();
                    }

                    out.println(choice);

                } else if ("JUDGE_TURN".equals(line)) {
                    System.out.println("You are the judge! Cards to choose from:");
                    judgeMode = true;

                } else if (judgeMode && "END_CHOICES".equals(line)) {
                    System.out.print("Pick winning card index: ");
                    String choice = console.readLine();

                    // 🔧 non-blocking drain of extra spammed lines
                    while (console.ready()) {
                        console.readLine();
                    }

                    out.println(choice);
                    judgeMode = false;

                } else {
                    // normal server message (card list, prompts, etc.)
                    System.out.println("SERVER: " + line);
                }
            }
        }
    }
}

