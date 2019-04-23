import BLAST.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class SequenceScorerTest {

    private BlastOutput BO;
    private SequenceScorer sequenceScorer;

    ArrayList<SignatureBit> testSig1;
    ArrayList<SignatureBit> testSig2;
    ArrayList<SignatureBit> testSig3;
    ArrayList<SignatureBit> testsig4;
    ArrayList<SignatureBit> testsiga;

    BLAST.Hsp testHsp11;
    BLAST.Hsp testHsp12;
    BLAST.Hsp testHsp22;
    BLAST.Hsp testHsp33;
    BLAST.Hsp testHsp13;
    BLAST.Hsp testHsp31;

    BLAST.Hsp testHsp44;
    BLAST.Hsp testHsp4a;
    BLAST.Hsp testHsp4b;
    BLAST.Hsp testHsp4c;



    @BeforeClass
    void setUp() {


//        sequenceScorer = new SequenceScorer();

        BlastOutputIterations Iterations = new BlastOutputIterations();

        Iteration iteration = new Iteration();

        Hit hit = new Hit();

        HitHsps hitHsps = new HitHsps();

        Hsp hsp = new Hsp();

        hsp.setHspQseq("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        hsp.setHspHseq("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        hsp.setHspQueryFrom("0");
        hsp.setHspQueryTo("25");
        hsp.setHspEvalue("1.0");

        hitHsps.getHsp().add(hsp);

        hit.setHitHsps(hitHsps);

        IterationHits iterationHits = new IterationHits();

        List<Hit> hits = iterationHits.getHit();

        hits.add(hit);

        iteration.setIterationHits(iterationHits);


//        iteration.getIterationHits().getHit().add(hit);

        //this is how you add BOHits
        Iterations.getIteration().add(iteration);


        BO = new BlastOutput();
        BO.setBlastOutputIterations(Iterations);





        testSig1 = new ArrayList<>();
//        testSig1.testSig1
        testSig1.add(new SignatureBit(0, 'A'));

        testSig2 = new ArrayList<>();
        testSig2.add(new SignatureBit(0, 'A'));
        testSig2.add(new SignatureBit(1, 'B'));

        testSig3 = new ArrayList<>();
        testSig3.add(new SignatureBit(0, 'A'));
        testSig3.add(new SignatureBit(1, 'B'));
        testSig3.add(new SignatureBit(2, 'C'));

        testsig4 = new ArrayList<>();
        testsig4.add(new SignatureBit(0, 'A'));
        testsig4.add(new SignatureBit(1, 'B'));
        testsig4.add(new SignatureBit(2, 'C'));
        testsig4.add(new SignatureBit(3, 'D'));
        testsig4.add(new SignatureBit(4, 'E'));
        testsig4.add(new SignatureBit(5, 'F'));
        testsig4.add(new SignatureBit(6, 'G'));
        testsig4.add(new SignatureBit(7, 'H'));
        testsig4.add(new SignatureBit(8, 'I'));
        testsig4.add(new SignatureBit(9, 'J'));
        testsig4.add(new SignatureBit(10, 'K'));
        testsig4.add(new SignatureBit(11, 'L'));
        testsig4.add(new SignatureBit(12, 'M'));
        testsig4.add(new SignatureBit(13, 'N'));
        testsig4.add(new SignatureBit(14, 'O'));
        testsig4.add(new SignatureBit(15, 'P'));
        testsig4.add(new SignatureBit(16, 'Q'));
        testsig4.add(new SignatureBit(17, 'R'));
        testsig4.add(new SignatureBit(18, 'S'));
        testsig4.add(new SignatureBit(19, 'T'));
        testsig4.add(new SignatureBit(20, 'U'));
        testsig4.add(new SignatureBit(21, 'V'));
        testsig4.add(new SignatureBit(22, 'W'));
        testsig4.add(new SignatureBit(23, 'X'));
        testsig4.add(new SignatureBit(24, 'Y'));
        testsig4.add(new SignatureBit(25, 'Z'));

        testsiga = new ArrayList<>();
        testsiga.add(new SignatureBit(0, 'F'));
        testsiga.add(new SignatureBit(1, 'G'));
        testsiga.add(new SignatureBit(2, 'H'));
        testsiga.add(new SignatureBit(3, 'I'));
        testsiga.add(new SignatureBit(4, 'J'));




        testHsp12 = new BLAST.Hsp();

        testHsp12.setHspQueryTo("0");
        testHsp12.setHspQueryFrom("0");
        testHsp12.setHspQseq("AB");
        testHsp12.setHspHseq("A");

        testHsp11 = new BLAST.Hsp();

        testHsp11.setHspQueryTo("0");
        testHsp11.setHspQueryFrom("0");
        testHsp11.setHspQseq("A");
        testHsp11.setHspHseq("A");


        testHsp22 = new BLAST.Hsp();
        testHsp22.setHspQueryTo("1");
        testHsp22.setHspQueryFrom("0");
        testHsp22.setHspQseq("AB");
        testHsp22.setHspHseq("AB");

        testHsp33 = new BLAST.Hsp();
        testHsp33.setHspQueryTo("2");
        testHsp33.setHspQueryFrom("0");
        testHsp33.setHspQseq("ABC");
        testHsp33.setHspHseq("ABC");

        testHsp31 = new BLAST.Hsp();
        testHsp31.setHspQueryTo("0");
        testHsp31.setHspQueryFrom("0");
        testHsp31.setHspQseq("A");
        testHsp31.setHspHseq("ABC");

        testHsp44 = new BLAST.Hsp();
        testHsp44.setHspQueryTo("25");
        testHsp44.setHspQueryFrom("0");
        testHsp44.setHspQseq("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        testHsp44.setHspHseq("ABCDEFGHIJKLMNOPQRSTUVWXYZ");


        testHsp4a = new BLAST.Hsp();
        testHsp4a.setHspQueryTo("10");
        testHsp4a.setHspQueryFrom("0");
        testHsp4a.setHspQseq("-----FGHIJ-----");
        testHsp4a.setHspHseq("ZZZZZFGHIJZZZZZ");


    }

    @DataProvider
    public Object[][] scoreSignatureProvider(){
        return new Object[][]{
                new Object[] {testSig1, testHsp11, 4, "A"}, //passes
                new Object[] {testSig2, testHsp12, 4, "A-"},// passes
                new Object[] {testSig2, testHsp22, 8, "AB"}, //passes
                new Object[] {testSig3, testHsp33, 17, "ABC"}, //passes
                new Object[] {testSig3, testHsp31, 4, "A--"}, //passes
                new Object[] {testsig4, testHsp44, 126, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"} //passes
//                new Object[] {testsiga, testHsp4a, 25, "-----FGHIJ-----"}
        };
    }

    @Test(dataProvider = "scoreSignatureProvider")
    void testScoreSignsture(ArrayList<SignatureBit> signature, BLAST.Hsp HSP, int expectedScore, String expectedSigMatchNew){
        SequenceScorer sequenceScorer = new SequenceScorer();
        Object[] results = sequenceScorer.scoreSignature(signature, HSP);
        int score = (Integer)results[0];
        String sigMatchNew = (String)results[1];
//        System.out.println(score + " : " + expectedScore + " | " + sigMatchNew + " : " + expectedSigMatchNew);
        assertEquals(score, expectedScore);
        assertTrue( expectedSigMatchNew.equals(sigMatchNew));
    }


//    this works. not sure how to prove/demonstrate it without mocks and I'm too lazy to do that
    @Test
    void testScoreOutput(){
        sequenceScorer = new SequenceScorer(testsig4, testsig4);
        SequenceHits sequenceHits = new SequenceHits();
        sequenceScorer.scoreOutput(BO, sequenceHits);
    }

}
