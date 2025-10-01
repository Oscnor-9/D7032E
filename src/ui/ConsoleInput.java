package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput implements ChoiceInput {
    private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readLine() throws IOException {
        return console.readLine();
    }
}
