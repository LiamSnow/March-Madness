import java.io.FileWriter;
import java.util.List;

/**
 * 
 * @author Liam Snow
 */
public class Bracket {
	
	private static final int WIDTH = 24;
	private Team[][] bracket;
	private int year;
	
	/** Runs a simulated bracket with the input teams
	 * @param teams The teams (in the order of the bracket) to play */
	public Bracket(List<Team> teams, int year) {
		this.year = year;
		bracket = new Team[13][34];
		
		//Play for the First Four
		for (int i = 0, c = 0; i < teams.size(); i++) {
			if (teams.get(i).getClass() == InitialEightTeam.class) {
				bracket[c * 3 + 1][0] = teams.get(i);
				bracket[c * 3 + 1][1] = teams.get(i + 1);
				bracket[c * 3 + 2][1] = teams.get(i).play(teams.get(i + 1));
				teams.set(i, teams.get(i).play(teams.get(i + 1)));
				teams.remove(i + 1);
				c++;
			}
		}
		
		//Fill in teams
		for (int i = 0; i < 64; i++) {
			bracket[i > 31 ? 12 : 0][i % 32 + 2] = teams.get(i);
		}
		
		//Play games
		//side of the bracket (0 = left, 1 = right)
		for (int side = 0; side < 2; side++) {
			//rounds (1 is round 1, 5/7 is final four)
			for (int n = 1; n < 6; n++) {
				//loop down the bracket and play the games
				for (int i = (int) Math.pow(2, n - 2); i < 32; i += Math.pow(2, n)) {
					bracket[side == 0 ? n : 12 - n][i + Util.min((int) Math.pow(2, n - 2), 1) + 2] = bracket[side == 0 ? n - 1 : 13 - n][i + 2].play(bracket[side == 0 ? n - 1 : 13 - n][i + (int) Math.pow(2, n - 1) + 2]);
				}
			}
		}
		//final game
		bracket[6][18] = bracket[5][18].play(bracket[7][18]);
	}
	
	/** Visualizes this bracket as a string */
	public String toString() {
		String returnString = Util.repeat(" ", (int) (WIDTH * 6)) + year + " Simulation Bracket\n\n";
		for (int y = 0; y < bracket[0].length; y++) {
			if (y == 0 || y == 2) {
				returnString += Util.repeat(" ", WIDTH) + Util.repeat("-", WIDTH * 11) + Util.repeat(" ", WIDTH) + "\n";
				if (y == 2)
					returnString += "\n" + Util.repeat("#", WIDTH * 13 + 2) +  "\n";
			}
			if (y != 0 && y != 1)
				returnString += "#";
			for (int x = 0; x < bracket.length; x++) {
				if (x == 6 && y == 18)
					returnString += "#" + (bracket[6][18] == null ? Util.repeat(" ", WIDTH - 2) : Util.pad(bracket[6][18].name, WIDTH - 2)) + "#";
				else if (x == 6 && (y == 17 || y == 19))
					returnString += Util.repeat("=", WIDTH);
				else returnString += bracket[x][y] == null ? Util.repeat(" ", WIDTH) : Util.pad(bracket[x][y].name, WIDTH);
			}
			if (y != 0 && y != 1)
				returnString += "#";
			returnString += "\n";
			if (y == bracket[0].length - 1)
				returnString += Util.repeat("#", WIDTH * 13 + 2);
		}
		return returnString;
	}

	/** Saves this bracket as a string to "output.txt" */
	public void save() {
		try {
			FileWriter writer = new FileWriter("output.txt", false);
			writer.write(toString());
			writer.close();
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void printBracket() {
		System.out.println(toString());
	}
}