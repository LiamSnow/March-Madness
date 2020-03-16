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
		//Get Kenpom Data
		List<Team> kenpomTeams;
		if (USE_WEB) {
			kenpomTeams = Reader.fetchKenpomStats(YEAR);
			Reader.saveKenpomTeams(kenpomTeams, YEAR);
		}
		else kenpomTeams = Reader.getDataFromFile(YEAR);
		
		//Run Regression
		if (REGRESS) {
			//Finds the best weights for comparing teams
			Regressor regressor = new Regressor(kenpomTeams);
			regressor.run(0.05);
		}
		
		//Simulate Bracket
		else {
			//Reads in positioning in the bracket from text file
			List<Team> teams = Reader.readTeams();
			Reader.mergeKenpomStats(kenpomTeams, teams);
			Reader.checkIfTeamsHaveData(teams);

			//Runs a full bracket simulation
			Bracket bracket = new Bracket(teams, YEAR);
			bracket.save();
			bracket.printBracket();
		}
	}
}