package renameScrobbles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CredentialsHandler {
	
	/**
	 * Username of the user who is renaming an artist
	 */
	public String username;
	
	/**
	 * Password of the user who is renaming an artist
	 */
	public String password;

	/**
	 * Reads the api key from the specified file File should just consist of two
	 * lines, the first - the username, the second - the password
	 * 
	 * @param directory
	 *            The path of the file to read the details from
	 * @author Alex
	 */
	public void readCredentialsFromFile(String directory) {
		Path path = Paths.get(directory);
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			username = lines.get(0);
			password = lines.get(1);
		} catch (IOException e) {
			username = "";
			password = "";
		}
	}

}
