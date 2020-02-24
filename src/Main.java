import java.util.List;

/**
 * March Madness AP Computer Science Project
 * @author Liam Snow
 * @date 2/23/2020
 */
public class Main {
	
	public static final int YEAR = 2018;
	
	public static void main(String args[]) {
		
		String teamPositioning = Reader.readTextFile();
		List<Team> teams = Reader.readKenpom(YEAR);
		Bracket bracket = new Bracket(Reader.organizeTeamsBy(teams, teamPositioning), YEAR);
		bracket.save();
	}
}
