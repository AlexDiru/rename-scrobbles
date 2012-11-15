package renameScrobbles;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UnixTime {
	
	/**
	 * A random generator
	 */
	private static Random random = new Random();
	
	/**
	 * Gets a random timestamp for lastfm which belongs to the past 2 weeks
	 * @return The timestamp
	 */
	public static int getDateInThePastTwoWeeks() {
		Date date = new Date();
		int endDate = (int) (date.getTime() / 1000);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -13);
		int startDate = (int) (calendar.getTime().getTime() / 1000);
		
		return random.nextInt(endDate - startDate) + startDate;
	}
}