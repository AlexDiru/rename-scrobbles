package renameScrobbles;

public class Program {

	public static void main(String[] args) {

		LastFMSession session = new LastFMSession();
		
		System.out.println("* * * * * * * * * * *");
		System.out.println("* Rename Scrobbles  *");

		while (true) {
			String choice = "";
			do {
				System.out.println("* * * * * * * * * * * * *");
				System.out.println("* (1) Rename artist     *");
				System.out.println("* (2) Rename album      *");
				System.out.println("* (3) Help              *");
				System.out.println("* (q) Quit application  *");
				System.out.println("* * * * * * * * * * * * *");
				choice = InputHandler.readLine();
			} while (!(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("q")));

			if (choice.equals("1") || choice.equals("2")) {
				while (!session.isSessionSuccessful()) {
					System.out.println("Enter username: ");
					String username = InputHandler.readLine();
					System.out.println("Enter password: ");
					String password = InputHandler.readPassword();
					session = new LastFMSession(username, password);
				}

				if (choice.equals("1")) {
					System.out.println("Enter current artist name: ");
					String artist = InputHandler.readLine();
					System.out.println("Enter new artist name: ");
					String newArtist = InputHandler.readLine();
					session.renameArtist(artist, newArtist);
				} else if (choice.equals("2")) {
					System.out.println("Enter current artist name: ");
					String artist = InputHandler.readLine();
					System.out.println("Enter current album name: ");
					String album = InputHandler.readLine();
					System.out.println("Enter new album name: ");
					String newAlbum = InputHandler.readLine();
					session.renameAlbum(artist, album, newAlbum);
				}
			}

			if (choice.equals("3")) {
				System.out.println("Rename Scrobbles");
				System.out.println("Alexander Spedding http://www.github.com/AlexDiru");
				System.out.println("lastfm-java https://code.google.com/p/lastfm-java/");
				System.out.println("Renames the artist name of scrobbles or an album name");
				System.out.println("Scrobbles of the original artist/album name have to be deleted manually");
				System.out.println("");
			}

			if (choice.equals("q"))
				return;
		}
	}
}
