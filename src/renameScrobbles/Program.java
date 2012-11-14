package renameScrobbles;

import java.util.ArrayList;
import java.util.Collection;

import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

public class Program {

	/**
	 * Renames all the songs by an artist to a different artist
	 * 
	 * @param artistName
	 *            The artist to get the tracks of
	 * @param newName
	 *            The name to rename the artist to
	 * @author Alex
	 */
	public static void rename(String artistName, String newName) {
		// Get the first page of results
		PaginatedResult<Track> page = User.getArtistTracks("AlexDiru", artistName, 1, APIHandler.apiKey);

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
			tracks = i == 1 ? page.getPageResults() : User.getArtistTracks("AlexDiru", artistName, i, APIHandler.apiKey).getPageResults();

			// Iterate the tracks and modify the artist parameters and add to
			// the convert list
			for (Track track : tracks)
				toConvert.add(new Track(track.getName(), track.getUrl(), track.getMbid(), track.getPlaycount(), track.getListeners(), false, newName, "", false, false, track.getPlayedWhen()));
		}

		for (Track t : toConvert) {
			System.out.println(t.getArtist() + " - " + t.getName() + " " + t.getPlayedWhen());
		}

	}

	public static void main(String[] args) {
		APIHandler.readApiKeyFromFile("C:/Users/Alex/Desktop/api.txt");
		rename("ＬＯＳＴ", "LOST");
	}
}
