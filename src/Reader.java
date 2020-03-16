import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	
	/** Reads data from https://kenpom.com and returns the teams read in
	 * @param year Which year to read data from */
	public static List<Team> fetchKenpomStats(int year) {
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
				} catch (Exception e) { }
			}
			return teams;
		} catch (Exception e) { e.printStackTrace(); }
		return null;
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
		}
	}

	public static void mergeKenpomStats(List<Team> kenpomTeams, List<Team> teams) {
		for (int t = 0; t < teams.size(); t++) {
			for (int k = 0; k < kenpomTeams.size(); k++) {
				if (Util.isSameName(kenpomTeams.get(k).name, teams.get(t).name)) {
					teams.get(t).merge(kenpomTeams.get(k));
				}
			}
		}
	}
	
	public static void saveKenpomTeams(List<Team> kenpomTeams, int year) {
		try {
			FileWriter writer = new FileWriter("data.txt", false);
			writer.write(year + " KENPOM DATA\n");
			for (Team team : kenpomTeams)
				writer.write(team.getSaveString() + '\n');
			writer.close();
		} catch (IOException e) { e.printStackTrace(); }
	}

	public static List<Team> getDataFromFile(int year) {
		try {
			List<Team> teams = new ArrayList<Team>();
			Scanner scan = new Scanner(new File("data.txt"));
			
			if (!scan.nextLine().contains(year + " KENPOM DATA")) {
				scan.close();
				throw new Exception("There is no saved data for that year!");
			}
			
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				teams.add(new Team(
					Util.findElementInt(line, "RANK"), 
					Util.findElement(line, "NAME"), 
					Util.findElementInt(line, "SEED"), 
					Util.findElement(line, "CONF"), 
					Util.findElementInt(line, "WINS"), 
					Util.findElementInt(line, "LOSSES"), 
					Util.findElementDouble(line, "AEM"),
					Util.findElementDouble(line, "AOE"), 
					Util.findElementDouble(line, "ADE"), 
					Util.findElementDouble(line, "AT"), 
					Util.findElementDouble(line, "LUCK"), 
					Util.findElementDouble(line, "SSR"), 
					Util.findElementDouble(line, "AAOEOO"), 
					Util.findElementDouble(line, "AADEOO"), 
					Util.findElementDouble(line, "NCSSR")
				));
			}
			scan.close();
			return teams;
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
}