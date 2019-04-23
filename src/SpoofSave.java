import java.io.File;
import java.math.BigDecimal;

public class SpoofSave {

    public static void main(String[] args) {

        BlastQuery blastQuery = new BlastQuery("test", "AAA", "A1", "B2", "0.1", "2", "worm");

        SequenceHits sequenceHits = new SequenceHits();
        sequenceHits.add(new SequenceHit("AAA", "0", "3", new BigDecimal(0.1), 10, 10, "ABC", "CBA", 0.8, 0.9, "hi"));
        sequenceHits.add(new SequenceHit("AAA", "0", "3", new BigDecimal(0.03), 5, 8, "ABC", "CBA", 1.0, 0.2, "hi"));

        Save save = new Save(blastQuery, sequenceHits);

        save.write(new File("spoofSave.blst"));
    }

}
