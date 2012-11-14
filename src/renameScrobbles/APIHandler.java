package renameScrobbles;

import java.util.List;
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
	 * File should just consist of twos line - the api key, and the secret key
	 * @param directory The path of the file to read the key from
	 * @author Alex
	 */
	public void readApiKeyFromFile(String directory) {
		Path path = Paths.get(directory);
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			apiKey = lines.get(0);
			apiSecret = lines.get(1);
		} catch (IOException e) {
			apiKey = "";
			apiSecret = "";
		}
	}

	/**
	 * The last fm api key
	 */
	public String apiKey;
	
	/**
	 * Secret key for the api
	 */
	public String apiSecret;
}
