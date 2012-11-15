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

		String choice = "";
		do
		{
		System.out.println("(1) Rename artist");
		System.out.println("(2) Rename album");
		} while (!(choice.equals("1") || choice.equals("2")));
		
		
		if (choice.equals("1")) {
			System.out.println("Enter current artist name: ");
			String artist = InputHandler.readLine();
			System.out.println("Enter new artist name: ");
			String newArtist = InputHandler.readLine();
			session.renameArtist(artist, newArtist);
		}
		
		if (choice.equals("2")) {
			System.out.println("Enter current artist name: ");
			String artist = InputHandler.readLine();
			System.out.println("Enter current album name: ");
			String album = InputHandler.readLine();
			System.out.println("Enter new album name: ");;
			String newAlbum = InputHandler.readLine();
			session.renameAlbum(artist, album, newAlbum);
		}
	}
}
