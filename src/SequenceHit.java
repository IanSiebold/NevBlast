import java.math.BigDecimal;
import java.io.Serializable;

public class SequenceHit implements Serializable {

	String hitSequence;
	String hitFrom;
	String hitTo;
	String sigOneMatch;
	String sigTwoMatch;
	BigDecimal eValue;
	double scoreOne;
	double scoreTwo;
	double normalizedScoreOne;
	double normalizedScoreTwo;
	String accession;
	String error;   //used to report errors from blast

	public SequenceHit(String hitSequence, String hitFrom, String hitTo, BigDecimal eValue, double scoreOne, double scoreTwo, String sigOneMatch, String sigTwoMatch, double normalizedScoreOne, double normalizedScoreTwo, String accession) {
		this.hitSequence = hitSequence;
		this.hitFrom = hitFrom;
		this.hitTo = hitTo;
		this.eValue = eValue;
		this.scoreOne = scoreOne;
		this.scoreTwo = scoreTwo;
		this.error = "";
		this.sigOneMatch = sigOneMatch;
		this.sigTwoMatch = sigTwoMatch;
		this.normalizedScoreOne = normalizedScoreOne;
		this.normalizedScoreTwo = normalizedScoreTwo;
		this.accession = accession;
	}

	public SequenceHit() {
		//default constructor does nothing
	}

	/*public String getHitDef(){
		if(this.hitDef.contains(">gi"))
			return this.hitDef.substring(0,this.hitDef.indexOf(">gi"));
		return this.hitDef;
	}*/


	//-----------------------------------
	//Getters and Setters
	//-----------------------------------
	public double getNormalizedScoreOne() {
		return normalizedScoreOne;
	}

	public double getNormalizedScoreTwo() {
		return normalizedScoreTwo;
	}

	public String getError() {
		return error;
	}

	public String getHitSequence() {
		return hitSequence;
	}

	public String getHitFrom() {
		return hitFrom;
	}

	public String getHitTo() {
		return hitTo;
	}

	public BigDecimal geteValue() {
		return eValue;
	}

	public double getScoreOne() {
		return scoreOne;
	}

	public double getScoreTwo() {
		return scoreTwo;
	}

	public String getAccession() {
		return accession;
	}

	public String getSigOneMatch() {
		if(sigOneMatch.startsWith("-"))
			return " " + sigOneMatch;
		return sigOneMatch;
	}

	public String getSigTwoMatch() {
		if(sigTwoMatch.startsWith("-"))
			return " " + sigTwoMatch;
		return sigTwoMatch;
	}

	public boolean equals(SequenceHit sequenceHit){
		boolean sentinel = true;

		String hitSequence;
		String hitFrom;
		String hitTo;
		String sigOneMatch;
		String sigTwoMatch;
		BigDecimal eValue;
		double scoreOne;
		double scoreTwo;
		double normalizedScoreA;
		double normalizedScoreB;

		sentinel = sentinel && this.hitSequence.equals(sequenceHit.getHitSequence());
		sentinel = sentinel && this.hitFrom.equals(sequenceHit.getHitFrom());
		sentinel = sentinel && this.hitTo.equals(sequenceHit.getHitTo());
		sentinel = sentinel && this.sigOneMatch.equals(sequenceHit.getSigOneMatch());
		sentinel = sentinel && this.sigTwoMatch.equals(sequenceHit.getSigTwoMatch());
		sentinel = sentinel && this.eValue.equals(sequenceHit.geteValue());
		sentinel = sentinel && this.scoreOne == sequenceHit.getScoreOne();
		sentinel = sentinel && this.scoreTwo == sequenceHit.getScoreTwo();
		sentinel = sentinel && this.normalizedScoreOne == sequenceHit.getNormalizedScoreOne();
		sentinel = sentinel && this.normalizedScoreTwo == sequenceHit.getNormalizedScoreTwo();

		return sentinel;
	}

}