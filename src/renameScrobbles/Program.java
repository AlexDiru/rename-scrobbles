package renameScrobbles;

public class Program {

	public static void main(String[] args) {
		LastFMSession session;
		do
		{
			System.out.println("Enter username: ");
			String username = InputHandler.readLine();
			System.out.println("Enter password: ");
			String password = InputHandler.readPassword();
			session = new LastFMSession(username, password);
		} while (!session.isSessionSuccessful());
		
		System.out.println("Enter current artist name: ");
		String artist = InputHandler.readLine();
		System.out.println("Enter new artist name: ");
		String newArtist = InputHandler.readLine();
		
		session.rename(artist, newArtist);
	}
}
