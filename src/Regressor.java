import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Regressor {

	private List<Team> teams;
	private List<Game> games;
	
	public Regressor(List<Team> teams) {
		try {
			this.teams = teams;
			this.games = new ArrayList<Game>();
			Scanner scan = new Scanner(new File("games.txt"));
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String team1Name = line.substring(0, line.indexOf("/")), team2Name = line.substring(line.indexOf("/") + 1, line.indexOf(" -> "));
				Team team1 = getTeam(team1Name), team2 = getTeam(team2Name);
				int result = Util.parseIntSafe(line.substring(line.indexOf(" -> ") + 4));
				if (team1 == null) {
					System.out.println("Couldn't find team " + team1Name);
					continue;
				}
				if (team2 == null) {
					System.out.println("Coudln't find team " + team2Name);
					continue;
				}
				games.add(new Game(team1, team2, result == 1));
			}
			scan.close();
		} catch (FileNotFoundException e) { e.printStackTrace(); }
	}

	private static final class Game {
		public Team team1, team2;
		public boolean firstTeamWon;
		public Game(Team team1, Team team2, boolean firstTeamWon) {
			this.team1 = team1;
			this.team2 = team2;
			this.firstTeamWon = firstTeamWon;
		}
	}
	
	public void run(double step) {
		TeamRatingWeights bestWeights = Team.weights;
		double bestWeightsAccuracy = 0;
		System.out.println("Running Regression...");
		for (double aem = 0; aem <= 1; aem += step) {
			for (double aoe = 0; aoe <= 1; aoe += step) {
				for (double ade = 0; ade <= 1; ade += step) {
					for (double aaoeoo = 0; aaoeoo <= 1; aaoeoo += step) {
						for (double aadeoo = 0; aadeoo <= 1; aadeoo += step) {
							//for (double wl = 0; wl <= 1; wl += step) {
							//for (double at = 0; at <= 1; at += step) {
							//for (double luck = 0; luck <= 1; luck += step) {
							//for (double sosr = 0; sosr <= 1; sosr += step) {
							//for (double ncsosr = 0; ncsosr <= 1; ncsosr += step) {
							//Team.weights.WL = wl;
							//Team.weights.AT = at;
							//Team.weights.LUCK = luck;
							//Team.weights.SOSR = sosr;
							//Team.weights.NCSOSR = ncsosr;
							Team.weights.AEM = aem;
							Team.weights.AOE = aoe;
							Team.weights.ADE = ade;
							Team.weights.AAOEOO = aaoeoo;
							Team.weights.AADEOO = aadeoo;
							double curAcc = getCurrentAccuracy();
							if (curAcc > bestWeightsAccuracy) {
								bestWeights = Team.weights.copy();
								bestWeightsAccuracy = curAcc;
							}
						}
					}
				}
			}
		}
		System.out.println("Regressor Output: ");
		System.out.println(" - best weights: " + bestWeights);
		System.out.println(" - best accuracy: " + bestWeightsAccuracy);
	}
	
	private double getCurrentAccuracy() {
		int total = 0, correct = 0;
		for (Game game : games) {
			if ((game.team1.getRating() > game.team2.getRating()) == game.firstTeamWon)
				correct++;
			total++;
		}
		return (double) correct / total;
	}
	
	private Team getTeam(String name) {
		for (Team team : teams) {
			if (Util.isSameName(name, team.name))
				return team;
		}
		return null;
	}
}