package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private final Socket socket;

    public GameClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
    }

    public void start() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String line;
            boolean judgeMode = false;

            while ((line = in.readLine()) != null) {
                if (line.equals("YOUR_TURN")) {
                    System.out.print("Choose index: ");
                    String choice = scanner.nextLine();
                    // discard any spammed extra input
                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                    }
                    out.println(choice);

                } else if (line.equals("JUDGE_TURN")) {
                    System.out.println("You are the judge! Cards to choose from:");
                    judgeMode = true;

                } else if (line.equals("END_CHOICES") && judgeMode) {
                    System.out.print("Pick winning card index: ");
                    String choice = scanner.nextLine();
                    // discard any spammed extra input
                    while (scanner.hasNextLine()) {
                        scanner.nextLine();
                    }
                    out.println(choice);
                    judgeMode = false;

                } else {
                    System.out.println("SERVER: " + line);
                }
            }
        }
    }
}

