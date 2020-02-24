
/**
 * 
 * @author Liam Snow
 */
public class Team {
	final String name, conference;
	final int rank, seed, wins, losses;
	final double adjustedEfficiencyMargin, adjustedOffensiveEfficiency, adjustedDefensiveEfficiency, 
		   adjustedTempo, luck, strengthOfScheduleRating, averageAdjstedOffensiveEfficiencyOfOpposingOffenses, 
		   averageAdjustedDefensiveEfficiencyOfOpposingDefenses, nonConferenceStrengthOfScheduleRating;
	
	public Team(int rank, String name, int seed, String conference, int wins, int losses, double adjustedEfficiencyMargin, 
			double adjustedOffensiveEfficiency, double adjustedDefensiveEfficiency, double adjustedTempo, 
			double luck, double strengthOfScheduleRating, double averageAdjstedOffensiveEfficiencyOfOpposingOffenses, 
			double averageAdjustedDefensiveEfficiencyOfOpposingDefenses, double nonConferenceStrengthOfScheduleRating) {
		this.rank = rank;
		this.name = name;
		this.seed = seed;
		this.conference = conference;
		this.wins = wins;
		this.losses = losses;
		this.adjustedEfficiencyMargin = adjustedEfficiencyMargin;
		this.adjustedOffensiveEfficiency = adjustedOffensiveEfficiency;
		this.adjustedDefensiveEfficiency = adjustedDefensiveEfficiency;
		this.adjustedTempo = adjustedTempo;
		this.luck = luck;
		this.strengthOfScheduleRating = strengthOfScheduleRating;
		this.averageAdjstedOffensiveEfficiencyOfOpposingOffenses = averageAdjstedOffensiveEfficiencyOfOpposingOffenses;
		this.averageAdjustedDefensiveEfficiencyOfOpposingDefenses = averageAdjustedDefensiveEfficiencyOfOpposingDefenses;
		this.nonConferenceStrengthOfScheduleRating = nonConferenceStrengthOfScheduleRating;
	}
	
	public Team play(Team otherTeam) {
		if (this.rank > otherTeam.rank)
			return this;
		else return otherTeam;
	}
	
	public String toString() {
		return rank + " " + name + " " + (seed == -1 ? "" : seed + " ") + conference + " " + wins + "-" + losses;
	}
}