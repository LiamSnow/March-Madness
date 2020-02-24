import java.io.FileWriter;
import java.util.List;

/**
 * 
 * @author Liam Snow
 */
public class Bracket {
	
	private Team[][] bracket;
	private int year;
	
	/** Runs a simulated bracket with the input teams
	 * @param teams The teams (in the order of the bracket) to play */
	public Bracket(List<Team> teams, int year) {
		this.year = year;
		bracket = new Team[13][32];
		
		//Fill in teams
		for (int i = 0; i < 64; i++) {
			bracket[i > 31 ? 12 : 0][i % 32] = teams.get(i);
		}
		
		//Play games
		//side of the bracket (0 = left, 1 = right)
		for (int s = 0; s < 2; s++) {
			//loop across the bracket (0 is edge, 6 is center)
			for (int n = 1; n < 6; n++) {
				//loop down the bracket and play the games
				for (int i = (int) Math.pow(2, n - 2); i < 32; i += Math.pow(2, n)) {
					bracket[s == 0 ? n : 12 - n][i + Util.min((int) Math.pow(2, n - 2), 1)] = bracket[s == 0 ? n - 1 : 13 - n][i].play(bracket[s == 0 ? n - 1 : 13 - n][i + (int) Math.pow(2, n - 1)]);
				}
			}
		}
		//final game
		bracket[6][16] = bracket[5][16].play(bracket[7][16]);
	}
	
	/** Visualizes this bracket as a string */
	public String toString() {
		String returnString = Util.repeat(" ", 110) + year + " Simulation Bracket\n";
		for (int y = 0; y < bracket[0].length; y++) {
			for (int x = 0; x < bracket.length; x++) {
				if (x == 6 && y == 16)
					returnString += "#" + (bracket[6][16] == null ? Util.repeat(" ", 17) : Util.pad(bracket[6][16].name, 17)) + "#";
				else if (x == 6 && (y == 15 || y == 17))
					returnString += Util.repeat("=", 19);
				else returnString += bracket[x][y] == null ? Util.repeat(" ", 19) : Util.pad(bracket[x][y].name, 19);
			}
			if (y < bracket[0].length - 1)
			returnString += "\n";
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
}