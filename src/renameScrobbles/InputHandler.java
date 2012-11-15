package renameScrobbles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static String readLine() { 
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String readPassword() {
		return System.console().readPassword().toString();
	}
}
