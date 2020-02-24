import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Liam Snow
 */
public class Reader {
	
	/** Reads data from https://kenpom.com
	 * @param year Which year to read data from */
	public static List<Team> readKenpom(int year) {
		try {
			URL url = new URL("https://kenpom.com/index.php?y=" + year);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuffer reader = new StringBuffer();
			String line, data;
			while ((line = rd.readLine()) != null) {
				reader.append(line);
			}
			data = reader.toString();
			String[] teamStrs = data.split("<tr>");
			List<Team> teams = new ArrayList<Team>();
			for (String teamStr : teamStrs) {
				try {
					if (teamStr.indexOf("<td class=") != -1) {
						String[] elements = teamStr.split("<td");
						teams.add(new Team(
								Util.parseIntSafe(Util.insideTag(elements[1])),
								Util.inside(Util.insideTag(elements[2]), "\">", "</a>"),
								Util.parseIntSafe(Util.inside(Util.insideTag(elements[2]), "seed\">", "</span>"), -1),
								Util.insideTag(Util.insideTag(elements[3])),
								Util.parseIntSafe(Util.insideTag(elements[4]).split("-")[0]),
								Util.parseIntSafe(Util.insideTag(elements[4]).split("-")[1]),
								Util.parseDoubleSafe(Util.insideTag(elements[5])),
								Util.parseDoubleSafe(Util.insideTag(elements[6])),
								Util.parseDoubleSafe(Util.insideTag(elements[8])),
								Util.parseDoubleSafe(Util.insideTag(elements[10])),
								Util.parseDoubleSafe(Util.insideTag(elements[12])),
								Util.parseDoubleSafe(Util.insideTag(elements[14])),
								Util.parseDoubleSafe(Util.insideTag(elements[16])),
								Util.parseDoubleSafe(Util.insideTag(elements[18])),
								Util.parseDoubleSafe(Util.insideTag(elements[20]))
							));
					}
				} catch (Exception e) { /*expected to have issues*/ }
			}
			return teams;
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	/** Reads the team positioning in the bracket from a text file */
	public static String readTextFile() {
		try {
			Scanner scan = new Scanner(new File("positioning.txt"));
			String returnString = "";
			while (scan.hasNextLine())
				returnString += scan.nextLine();
			scan.close();
			return returnString;
		} catch (Exception e) { e.printStackTrace(); }
		return "";
	}
	
	public static List<Team> organizeTeamsBy(List<Team> teams, String teamPositioning) {
		return teams;
	}
}