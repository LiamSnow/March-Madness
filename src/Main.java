import java.util.List;


/**
 * March Madness AP Computer Science Project
 * @author Liam Snow
 * @date 2/23/2020
 */
public class Main {
	
	public static final int YEAR = 2020;
	public static final boolean REGRESS = false;
	public static final boolean USE_WEB = true;
	
	public static void main(String args[]) {
		//Get Teams and Kenpom Data
		List<Team> teams = Reader.readTeams(), kenpomTeams;
		if (USE_WEB) {
			kenpomTeams = Reader.fetchKenpomStats(YEAR);
			Reader.saveKenpomTeams(kenpomTeams, YEAR);
		}
		else kenpomTeams = Reader.getDataFromFile(YEAR);
		
		Reader.mergeKenpomStats(kenpomTeams, teams);
		Reader.checkIfTeamsHaveData(teams);

		if (REGRESS) {
			//Finds the best weights for comparing teams
			Regressor regressor = new Regressor(kenpomTeams);
			regressor.run(0.025);
		}
		else {
			//Runs a full bracket simulation
			Bracket bracket = new Bracket(teams, YEAR);
			bracket.save();
			bracket.printBracket();
		}
	}
}