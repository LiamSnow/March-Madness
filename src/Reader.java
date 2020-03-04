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
	
	/** Reads data from https://kenpom.com and inserts it into a team list
	 * @param year Which year to read data from */
	public static void insertKenpomStats(List<Team> teams, int year) {
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
			String[] teamStrs = data.split("<tr");
			for (String teamStr : teamStrs) {
				try {
					if (teamStr.indexOf("<td class=") != -1) {
						String[] elements = teamStr.split("<td");
						String name = Util.inside(Util.insideTag(elements[2]), "\">", "</a>");
						
						//match
						for (int i = 0; i < teams.size(); i++) {
							if (Util.isSameName(name, teams.get(i).name)) {
								teams.get(i).setKenpomStats(
									Util.parseIntSafe(Util.insideTag(elements[1])),
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
								);
								break;
							}
						}
					}
				} catch (Exception e) { }
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/** Reads data from https://www.teamrankings.com/ and inserts it into a team list
	 * @param year Which year to read data from */
	public static void insertTeamRankingsStats(List<Team> teams, int year) {
		try {
			URL url = new URL("https://www.teamrankings.com/ncaa-basketball/stat/true-shooting-percentage?date=" + year + "-01-01");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuffer reader = new StringBuffer();
			String line, data;
			while ((line = rd.readLine()) != null)
				reader.append(line);
			data = reader.toString();
			data = data.substring(data.indexOf("<table"));
			String[] teamStrs = data.split("<tr");
			for (String teamStr : teamStrs) {
				try {
					if (teamStr.indexOf("<td class=\"rank") != -1) {
						String[] elements = teamStr.split("<td");
						String name = Util.insideTag(Util.insideTag(elements[2]));
						
						//match
						for (int i = 0; i < teams.size(); i++) {
							if (Util.isSameName(name, teams.get(i).name)) {
								teams.get(i).setTeamRankingsStats(Util.parseDoubleSafe(Util.insideTag(elements[3])) / 100.0);
								break;
							}
						}
					}
				} catch (Exception e) { e.printStackTrace(); }
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/** Reads the team positioning in the bracket from "positioning.txt" */
	public static List<Team> readTeams() {
		try {
			Scanner scan = new Scanner(new File("positioning.txt"));
			List<Team> teams = new ArrayList<Team>();
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.contains("/")) {
					teams.add(new InitialEightTeam(line.split("/")[0].replace("amp;", "")));
					teams.add(new InitialEightTeam(line.split("/")[1].replace("amp;", "")));
				}
				else teams.add(new Team(line.replace("amp;", "")));
			}
			scan.close();
			return teams;
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}

	public static void checkIfTeamsHaveData(List<Team> teams) {
		for (Team team : teams) {
			if (!team.hasKenpomData())
				System.out.println(team.name + " missing kenpom data!");
			if (!team.hasTeamRankingData())
				System.out.println(team.name + " missing team ranking data!");
		}
	}
}