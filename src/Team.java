
/**
 * 
 * @author Liam Snow
 */
public class Team {
	String name, conference;
	int rank, seed, wins, losses;
	double adjustedEfficiencyMargin, adjustedOffensiveEfficiency, adjustedDefensiveEfficiency, 
		   adjustedTempo, luck, strengthOfScheduleRating, averageAdjstedOffensiveEfficiencyOfOpposingOffenses, 
		   averageAdjustedDefensiveEfficiencyOfOpposingDefenses, nonConferenceStrengthOfScheduleRating;
	
	public Team(String name) {
		this.name = name;
	}
	
	public Team(int rank, String name, int seed, String conference, int wins, int losses, double adjustedEfficiencyMargin, 
			double adjustedOffensiveEfficiency, double adjustedDefensiveEfficiency, double adjustedTempo, 
			double luck, double strengthOfScheduleRating, double averageAdjstedOffensiveEfficiencyOfOpposingOffenses, 
			double averageAdjustedDefensiveEfficiencyOfOpposingDefenses, double nonConferenceStrengthOfScheduleRating) {
		this(name);
		setKenpomStats(
			rank, seed, conference, wins, losses, adjustedEfficiencyMargin, 
			adjustedOffensiveEfficiency, adjustedDefensiveEfficiency, adjustedTempo, 
			luck, strengthOfScheduleRating, averageAdjstedOffensiveEfficiencyOfOpposingOffenses, 
			averageAdjustedDefensiveEfficiencyOfOpposingDefenses, nonConferenceStrengthOfScheduleRating
		);
	}
	
	public void setKenpomStats(int rank, int seed, String conference, int wins, int losses, double adjustedEfficiencyMargin, 
			double adjustedOffensiveEfficiency, double adjustedDefensiveEfficiency, double adjustedTempo, 
			double luck, double strengthOfScheduleRating, double averageAdjstedOffensiveEfficiencyOfOpposingOffenses, 
			double averageAdjustedDefensiveEfficiencyOfOpposingDefenses, double nonConferenceStrengthOfScheduleRating) {
		this.rank = rank;
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
		if (getRating() > otherTeam.getRating())
			return this;
		else return otherTeam;
	}
	
	public static TeamRatingWeights weights = new TeamRatingWeights(0.0, 0.125, 0.15, 0.0, 0.0, 0.0, 0.0, 0.175, 0.675, 0.0);
	
	public double getRating() {
		double rating = 0;
		rating += weights.WL * ((double) wins / losses);
		rating += weights.AEM * adjustedEfficiencyMargin;
		rating += weights.AOE * adjustedOffensiveEfficiency;
		rating += weights.ADE * adjustedDefensiveEfficiency;
		rating += weights.AT * adjustedTempo;
		rating += weights.LUCK * luck;
		rating += weights.SOSR * strengthOfScheduleRating;
		rating += weights.AAOEOO * averageAdjstedOffensiveEfficiencyOfOpposingOffenses;
		rating += weights.AADEOO * averageAdjustedDefensiveEfficiencyOfOpposingDefenses;
		rating += weights.NCSOSR * nonConferenceStrengthOfScheduleRating;
		return rating;
	}
	
	public void merge(Team team) {
		this.rank = team.rank;
		this.name = team.name;
		this.seed = team.seed;
		this.conference = team.conference;
		this.wins = team.wins;
		this.losses = team.losses;
		this.adjustedEfficiencyMargin = team.adjustedEfficiencyMargin;
		this.adjustedOffensiveEfficiency = team.adjustedOffensiveEfficiency;
		this.adjustedDefensiveEfficiency = team.adjustedDefensiveEfficiency;
		this.adjustedTempo = team.adjustedTempo;
		this.luck = team.luck;
		this.strengthOfScheduleRating = team.strengthOfScheduleRating;
		this.averageAdjstedOffensiveEfficiencyOfOpposingOffenses = team.averageAdjstedOffensiveEfficiencyOfOpposingOffenses;
		this.averageAdjustedDefensiveEfficiencyOfOpposingDefenses = team.averageAdjustedDefensiveEfficiencyOfOpposingDefenses;
		this.nonConferenceStrengthOfScheduleRating = team.nonConferenceStrengthOfScheduleRating;
	}
	
	public boolean hasKenpomData() {
		return rank != 0 || conference != null;
	}

	public String toString() {
		return rank + " " + name + " " + (seed == -1 ? "" : seed + " ") + conference + " " + wins + "-" + losses;
	}
	
	public String getSaveString() {
		return  "NAME:" + name +
			   (seed == -1 ? ("") : (" SEED:" + seed)) +
			   " RANK:" + rank +
			   " CONF:" + conference +
			   " WINS:" + wins +
			   " LOSSES:" + losses +
			   " AEM:" + adjustedEfficiencyMargin +
			   " AOE:" + adjustedOffensiveEfficiency +
			   " ADE:" + adjustedDefensiveEfficiency +
			   " AT:" + adjustedTempo +
			   " LUCK:" + luck +
			   " SSR:" + strengthOfScheduleRating +
			   " AAOEOO:" + averageAdjstedOffensiveEfficiencyOfOpposingOffenses +
			   " AADEOO:" + averageAdjustedDefensiveEfficiencyOfOpposingDefenses +
			   " NCSSR:" + nonConferenceStrengthOfScheduleRating;
	}
}