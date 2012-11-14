package renameScrobbles;

import java.util.ArrayList;
import java.util.Collection;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import de.umass.lastfm.scrobble.ScrobbleResult;

public class LastFMSession {

	private APIHandler apiHandler = new APIHandler();
	private CredentialsHandler credentialsHandler = new CredentialsHandler();
	private de.umass.lastfm.Session session;

	/**
	 * @author Alex
	 */
	public LastFMSession() {
		apiHandler.readApiKeyFromFile("C:/Users/Alex/Desktop/api.txt");
		credentialsHandler.readCredentialsFromFile("C:/Users/Alex/Desktop/det.txt");
		session = Authenticator.getMobileSession(credentialsHandler.username, credentialsHandler.password, apiHandler.apiKey, apiHandler.apiSecret);
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
	public void rename(String artistName, String newName) {
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
				toConvert.add(new Track(track.getName(), track.getUrl(), track.getMbid(), track.getPlaycount(), track.getListeners(), false, newName, "", false, false, track.getPlayedWhen()));
		}

		for (Track t : toConvert) {
			System.out.println(t.getArtist() + " - " + t.getName() + " " + t.getPlayedWhen());
			scrobble(t);
		}
	}

	/**
	 * @param track
	 * @author Alex
	 */
	private void scrobble(Track track) {
		System.out.println(track.getArtist() + " " + track.getName());
		ScrobbleResult result = Track.scrobble(track.getScrobbleData(), session);
		if (result.isSuccessful() && !result.isIgnored())
			System.out.println("success");
		else
			System.out.println("failure");
	}
}
