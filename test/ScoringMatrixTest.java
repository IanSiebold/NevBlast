import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class ScoringMatrixTest {

    ScoringMatrix sm;

    public ScoringMatrixTest(){
        this.sm = new ScoringMatrix();
    }

//    @Before
//    public void setup(){
//        sm = new ScoringMatrix();
//    }




    @DataProvider(name = "testScoringProvider")
    public static Object[][] testScoringProvider(){
        return new Object[][] {
            // sigBit, aminoAcid, expectedVal
                {'A', 'A', 4},
                {'K', 'L', -2},
                {'Z', 'R', 0}

        };
    }


    @Test(dataProvider = "testScoringProvider")
    public void testScoring(char sigBit, char aminoAcid, int expectedVal){
       assertEquals(sm.getCharScore(sigBit, aminoAcid), expectedVal);
    }

}
