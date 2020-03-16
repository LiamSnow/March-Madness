public class TeamRatingWeights {
	public double WL, AEM, AOE, ADE, AT, LUCK, SOSR, AAOEOO, AADEOO, NCSOSR;
	public TeamRatingWeights(double WL, double AEM, double AOE, double ADE, double AT, double LUCK, double SOSR, double AAOEOO, double AADEOO, double NCSOSR) {
		this.WL = WL;
		this.AEM = AEM;
		this.AOE = AOE;
		this.ADE = ADE;
		this.AT = AT;
		this.LUCK = LUCK;
		this.SOSR = SOSR;
		this.AAOEOO = AAOEOO;
		this.AADEOO = AADEOO;
		this.NCSOSR = NCSOSR;
	}
	
	public String toString() {
		return WL + ", " + AEM + ", " + AOE + ", " + ADE + ", " + AT + ", " + LUCK + ", " + 
			   SOSR + ", " + AAOEOO + ", " + AADEOO + ", " + NCSOSR;
	}

	public TeamRatingWeights copy() {
		return new TeamRatingWeights(WL, AEM, AOE, ADE, AT, LUCK, SOSR, AAOEOO, AADEOO, NCSOSR);
	}
}