package renameScrobbles;

import java.util.ArrayList;
import java.util.Collection;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import de.umass.lastfm.scrobble.ScrobbleResult;

public class LastFMSession {

	/**
	 * Holds the API key and secret
	 */
	private APIHandler apiHandler = new APIHandler();
	
	/**
	 * Holds the username and password
	 */
	private CredentialsHandler credentialsHandler = new CredentialsHandler();
	
	/**
	 * The session used to scrobble
	 */
	private de.umass.lastfm.Session session;
	
	/**
	 * Whether the session connected successfully
	 * @return Flag
	 */
	public boolean isSessionSuccessful() {
		return Authenticator.isSuccessful();
	}

	/**
	 * @author Alex
	 */
	public LastFMSession() {
		apiHandler.readApiKeyFromFile("C:/Users/Alex/Desktop/api.txt");
		credentialsHandler.readCredentialsFromFile("C:/Users/Alex/Desktop/det.txt");
		session = Authenticator.getMobileSession(credentialsHandler.username, credentialsHandler.password, apiHandler.apiKey, apiHandler.apiSecret);

		if (!Authenticator.isSuccessful())
			System.out.println("Connection failed. Check username and/or password.");
	}
	
	public LastFMSession(String username, String password) {
		apiHandler.readApiKeyFromFile("C:/Users/Alex/Desktop/api.txt");
		credentialsHandler.username = username;
		credentialsHandler.password = password;
		session = Authenticator.getMobileSession(credentialsHandler.username, credentialsHandler.password, apiHandler.apiKey, apiHandler.apiSecret);

		if (!Authenticator.isSuccessful())
			System.out.println("Connection failed. Check username and/or password.");
	}

	/**
	 * Renames all the songs by an artist to a different artist
	 * 
	 * @param artistName
	 *            The artist to get the tracks of
	 * @param newName
	 *            The name to rename the artist to
	 * @author Alex
	 */
	public void renameArtist(String artistName, String newName) {
		// Get the first page of results
		PaginatedResult<Track> page = User.getArtistTracks(credentialsHandler.username, artistName, 1, apiHandler.apiKey);

		// List of tracks which need to be 're'scrobbled
		ArrayList<Track> toConvert = new ArrayList<Track>();

		// Get the total amount of pages to read through
		int pageNumber = page.getTotalPages();

		// The tracks on each page
		Collection<Track> tracks;

		for (int i = 1; i <= pageNumber; i++) {
			// Indicate progress to the user
			System.out.println("Parsing page " + i + "/" + pageNumber);

			// We have already read the first page
			tracks = i == 1 ? page.getPageResults() : User.getArtistTracks(credentialsHandler.username, artistName, i, apiHandler.apiKey).getPageResults();

			// Iterate the tracks and modify the artist parameters and add to
			// the convert list
			for (Track track : tracks)
				toConvert.add(new Track(track.getName(), track.getUrl(), track.getMbid(), track.getPlaycount(), track.getListeners(), false, newName, "", false, false, track.getPlayedWhen(), track.getAlbum()));
		}
		
		scrobbleTracks(toConvert);
	}
	
	/**
	 * Renames all the songs by an artist to a different album
	 * 
	 * @param artistName
	 *            The artist to get the tracks of
	 * @param newName
	 *            The name to rename the artist to
	 * @author Alex
	 */
	public void renameAlbum(String artistName, String albumName, String newName) {
		// Get the first page of results
		PaginatedResult<Track> page = User.getArtistTracks(credentialsHandler.username, artistName, 1, apiHandler.apiKey);

		// List of tracks which need to be 're'scrobbled
		ArrayList<Track> toConvert = new ArrayList<Track>();

		// Get the total amount of pages to read through
		int pageNumber = page.getTotalPages();

		// The tracks on each page
		Collection<Track> tracks;

		for (int i = 1; i <= pageNumber; i++) {
			// Indicate progress to the user
			System.out.println("Parsing page " + i + "/" + pageNumber);

			// We have already read the first page
			tracks = i == 1 ? page.getPageResults() : User.getArtistTracks(credentialsHandler.username, artistName, i, apiHandler.apiKey).getPageResults();

			// Iterate the tracks and modify the artist parameters and add to
			// the convert list
			for (Track track : tracks)
				//Filter by albums
				if (track.getAlbum().equals(albumName))
					toConvert.add(new Track(track.getName(), track.getUrl(), track.getMbid(), track.getPlaycount(), track.getListeners(), false, track.getArtist(), "", false, false, track.getPlayedWhen(), newName));
		}
		
		scrobbleTracks(toConvert);
	}
	
	private void scrobbleTracks(ArrayList<Track> tracks) {
		for (Track track : tracks) {
			scrobble(track);
		}
	}

	/**
	 * @param track
	 * @author Alex
	 */
	private void scrobble(Track track) {
		System.out.println("Scrobbling: " + track.getArtist() + " - " + track.getName() + " ~ " + track.getAlbum());
		ScrobbleResult result = Track.scrobble(track.getScrobbleData(), session, UnixTime.getDateInThePastTwoWeeks());
		if (result.isSuccessful() && !result.isIgnored())
			System.out.println("Success");
		else {
			System.out.println("Failed");
			System.out.println("Error code: " + result.getErrorCode());
			System.out.println("Error message: " + result.getErrorMessage());
			System.out.println("Ignored Error message: " + result.getIgnoredMessage());
		}
	}
}
