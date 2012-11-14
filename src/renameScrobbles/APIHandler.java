package renameScrobbles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles all the API keys
 * @author Alex
 *
 */
public class APIHandler {
	
	/**
	 * Reads the api key from the specified file
	 * File should just consist of one line which is the api key
	 * @param directory The path of the file to read the key from
	 * @author Alex
	 */
	public static void readApiKeyFromFile(String directory) {
		Path path = Paths.get(directory);
		try {
			apiKey = Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
		} catch (IOException e) {
			apiKey = "";
		}
	}

	/**
	 * The last fm api key
	 */
	public static String apiKey;
}
