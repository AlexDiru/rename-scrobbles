package renameScrobbles;

public class Program {

	public static void main(String[] args) {
		LastFMSession session = new LastFMSession();
		session.rename("API_TEST", "API_TEST_RENAME");
	}
}
