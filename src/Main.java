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
		Reader.insertKenpomStats(teams, YEAR);
		Bracket bracket = new Bracket(teams, YEAR);
		bracket.save();
		if (PRINT)
			System.out.println(bracket);
	}
}
