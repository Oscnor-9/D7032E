package io;

import card.Card;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * Utility class for loading cards from a plain text file.
 * <p>
 * Each non-empty line in the file becomes one {@link Card} instance,
 * created using a provided factory function.
 */
public class FileCardLoader {

	/**
	 * Load cards from a text file and constructs them using the given factory.
	 * <p>
	 * The file is read using the {@code Cp1252} charset to support extended lating characters.
	 * @param <T> the specific type of card to load
	 * @param fileName the name or path of the file to read
	 * @param factory a function that converts a line of text into a card instance
	 * @return a list of cards created from the file lines
	 * @throws IOException if the file cannot be found or read
	 */
    public static <T extends Card> ArrayList<T> loadCards(
            String fileName,
            Function<String, T> factory) throws IOException {

        Path filePath = Path.of(fileName).toAbsolutePath();

        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filePath);
        }
        
        // Read all lines using the Cp1252 charset
        List<String> lines = Files.readAllLines(filePath, Charset.forName("Cp1252"));
        ArrayList<T> cards = new ArrayList<>();

        // Create one card per non-empty line
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                cards.add(factory.apply(trimmed));
            }
        }

        return cards;
    }
}
