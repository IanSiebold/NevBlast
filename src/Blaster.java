import BLAST.BlastOutput;

import java.util.ArrayList;
import java.util.Collection;

public class Blaster {

    private static BlastQuery blastQuery;
    private static BlastRequest blastRequest;
    private static SequenceScorer sequenceScorer;
    private static SequenceHits sequenceHits;

    public Blaster(){
        blastQuery = new BlastQuery();
    }

    public static void runBlast() {
        //if havent run
        if(!(blastQuery == null) && sequenceHits == null){
            blastRequest = new BlastRequest(blastQuery);
            sequenceScorer = new SequenceScorer(blastQuery);
            BlastOutput BO = blastRequest.toBlast();
            sequenceHits = new SequenceHits();
            sequenceScorer.scoreOutput(BO, sequenceHits);
        }
        //if it has been run (is a loaded save) dont do anything
    }

    public static SequenceHits getSequenceHits(){
        return sequenceHits;
    }

    public static void setSequenceHits(SequenceHits sequenceHits2){sequenceHits = sequenceHits2;}

    public static void setBlastQuery(BlastQuery blastQuery2){
        blastQuery = blastQuery2;
    }


}
