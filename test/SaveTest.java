import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;


public class SaveTest {

    @Test
    public void testSaveEquality(){/*
        SequenceHits sequenceHits = new SequenceHits();
        sequenceHits.add(new SequenceHit("AAA", "0", "3", new BigDecimal(0.1), 10, 10, "ABC", "CBA", "hit"));

        SequenceHits sequenceHits2 = new SequenceHits();
        sequenceHits2.add(new SequenceHit("AAA", "0", "3", new BigDecimal(0.1), 10, 10, "ABC", "CBA", "hi"));

        BlastQuery blastQuery = new BlastQuery("test", "AAA", "A1", "B2", "0.1", "2", "worm");
        BlastQuery blastQuery2 =  new BlastQuery("test", "AAA", "A1", "B2", "0.1", "2", "wom");

        Save save1 = new Save(blastQuery, sequenceHits);
        Save save2 = new Save();
        Save save3 = new Save(blastQuery, sequenceHits2);
        Save save4 = new Save(blastQuery2, sequenceHits);

        save1.write(new File("test.blst"));
        save2.read(new File("test.blst"));

        Assert.assertTrue(save1.equals(save2));
        Assert.assertFalse(save2.equals(save3));
        Assert.assertFalse(save3.equals(save4));
        */
    }

}
