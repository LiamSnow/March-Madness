import java.util.List;

/**
 * March Madness AP Computer Science Project
 * @author Liam Snow
 * @date 2/23/2020
 */
public class Main {
	
	public static final int YEAR = 2018;
	public static final boolean PRINT = false;
	
	public static void main(String args[]) {
		List<Team> teams = Reader.readTeams();
		
		Reader.fetchKenpomStats(teams, YEAR);
		Reader.fetchTeamRankingsStats(teams, YEAR);
		Reader.checkIfTeamsHaveData(teams);
		//Reader.saveData();
		//Reader.readTeamRankingsStats(teams);
		//Reader.readKenpomStats(teams);
		
//		Bracket bracket = new Bracket(teams, YEAR);
//		bracket.save();
//		if (PRINT)
//			System.out.println(bracket);
	}
}
