import BLAST.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SequenceScorer {

    private String aminoAcid;
    private ArrayList<SignatureBit> sigOne;
    private ArrayList<SignatureBit> sigTwo;
    private double eVal;
    private ScoringMatrix blosum;
    private int optimumScoreOne;
    private int optimumScoreTwo;

    public SequenceScorer(){
        this.blosum = new ScoringMatrix();
        this.sigOne = new ArrayList<>();
        this.sigTwo = new ArrayList<>();
        this.eVal = 10.0;
        this.optimumScoreOne = 0;
        this.optimumScoreTwo = 0;
    }

    public SequenceScorer(ArrayList sigOne, ArrayList sigTwo){
        this();
        this.sigOne = sigOne;
        this.sigTwo = sigTwo;
    }

    public SequenceScorer(BlastQuery BQ){
        this.aminoAcid = BQ.getFastaSequence();
        this.sigOne = BQ.getSignatureOneBits();
        this.sigTwo = BQ.getSignatureTwoBits();
        this.eVal = BQ.getEVal();
        blosum = new ScoringMatrix();
        this.optimumScoreOne = 0;
        this.optimumScoreTwo = 0;
    }

    public void scoreOutput(BlastOutput BO, SequenceHits sequenceHits){
        optimumScoreOne = calculateOptimumScore(sigOne);

        optimumScoreTwo = calculateOptimumScore(sigTwo);

        List BOIteration = BO.getBlastOutputIterations().getIteration();

        for (int i = 0; i < BOIteration.size(); i++) {

            //Single creation of Hits aggregate to shorten method calls
            List<BLAST.Hit> BOHits =  BO.getBlastOutputIterations().getIteration().get(i).getIterationHits().getHit();

            processBOHits(BOHits, sequenceHits);


        }  //end for i
    }

    private void processBOHits(List<BLAST.Hit> BOHit, SequenceHits sequenceHits){
        //process all Hits
        for (int k = 0; k < BOHit.size(); k++) {
            //Single creation of Hit object to shorten method calls
            BLAST.Hit hit = BOHit.get(k);

            //maybe don't need. don't want to delete because I don't want to hunt for it in the "documentation"
            String accession = hit.getHitAccession();

            //this is dumb and bad. Alternative is to send sequenceHits to process hit
            SequenceHit sequenceHit = processHit(hit);
            if(sequenceHit != null)
                sequenceHits.add(sequenceHit);

        }//end for k
    }

    private SequenceHit processHit(BLAST.Hit hit) {
        SequenceHit tempHit = null;
        String acession = hit.getHitAccession();
        for (int j = 0; j < hit.getHitHsps().getHsp().size(); j++) {
            //Single creation of HSP object to shorten method calls
            BLAST.Hsp HSP = hit.getHitHsps().getHsp().get(j);

            BigDecimal tempEvalue = new BigDecimal(HSP.getHspEvalue());
            if (validEvalue(new BigDecimal(eVal), tempEvalue)) {

                //score Hit against Sig One
                Object[] sigOneScore = this.scoreSignature(sigOne, HSP);
                int scoreOne = (int)sigOneScore[0];
                String sigOneNewMatch = (String)sigOneScore[1];

                //score Hit against Sig two
                Object[] sigTwoScore = this.scoreSignature(sigTwo, HSP);
                int scoreTwo = (int)sigTwoScore[0];
                String sigTwoMatch = (String)sigTwoScore[1];

                String hitDef = hit.getHitDef();
                String hitSeq = HSP.getHspHseq(); //this might be the wrong seq
                String hitFrom = HSP.getHspHitFrom();
                String hitTo = HSP.getHspHitTo();

                double normalizedScoreOne = ((double)scoreOne / (double)optimumScoreOne);
                double normalizedScoreTwo = ((double)scoreTwo / (double)optimumScoreTwo);

                tempHit = new SequenceHit(hitSeq, hitFrom, hitTo, tempEvalue, scoreOne, scoreTwo, sigOneNewMatch, sigTwoMatch, normalizedScoreOne, normalizedScoreTwo, acession);
            }//end j - iterate HSPS
        }
        return tempHit;
    }



    //this code segment need to be responsible for returning
    //      The Score  (the score from the blosum matrix)
    //      The sigMatch (a string related to the signature sequence)
    public Object[] scoreSignature(ArrayList<SignatureBit> signature, BLAST.Hsp HSP){
        int score = 0;
        String sigMatchNew = "";
        for (int p = 0; p < signature.size(); p++) {
            //need to resolve this biojava Signature usage thing
            if(HSPInBoundsOfSignature(signature.get(p).getLineNumber(), Integer.valueOf(HSP.getHspQueryTo()),  Integer.valueOf(HSP.getHspQueryFrom()))) {
                //Number is valid!
                //position = lineNumber of search - line number query begins at
                int position = signature.get(p).getLineNumber() - Integer.valueOf(HSP.getHspQueryFrom());

                String qHit = HSP.getHspQseq();
                int originalPosition = position;
                for (int y = 0; y < originalPosition; y++) {
                    if (qHit.charAt(y) == '-') {
                        position++; //do this to add offset when getting the aminoAcidHit, so that it lines up with the sigAminoAcid?
                        // Basically offset aminoAcidHit by the number of misses in qHit that came before the current normalized position?
                    }
                }

                char aminoAcidHit = HSP.getHspHseq().charAt(position);

                char signatureAminoAcid = signature.get(p).getAminoAcid();

                sigMatchNew += aminoAcidHit;
                score += blosum.getCharScore(signatureAminoAcid, aminoAcidHit);
            }
            else {
                //outside of hit range (too high)
                sigMatchNew += "-";
            }
        }//end for p - sigA
        System.out.println(sigMatchNew);
        return new Object[] {score, sigMatchNew};
    }

    private boolean HSPInBoundsOfSignature(int signatureLineNumber, int HSPUpperBound, int HSPLowerBound){
        return (signatureLineNumber <= HSPUpperBound && signatureLineNumber >= HSPLowerBound);
    }
    private boolean validEvalue(BigDecimal eval, BigDecimal tempEvalue ){
        return tempEvalue.compareTo(new BigDecimal(eVal)) <= 0;
    }

    private int calculateOptimumScore(ArrayList<SignatureBit> sig){
        int optimumScore = 0;
        for (int i = 0; i < sig.size(); i++) {
            optimumScore += blosum.getCharScore(sig.get(i).getAminoAcid(), sig.get(i).getAminoAcid());
        }
        return optimumScore;
    }

}