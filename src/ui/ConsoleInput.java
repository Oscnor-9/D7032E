package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Read user input directly from the local system console
 * <p>
 * Implements {@link ChoiceInput} so that it can be used
 * by and {@code Player} class expecting a user input
 */
public class ConsoleInput implements ChoiceInput {
    private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readLine() throws IOException {
        return console.readLine();
    }
}
