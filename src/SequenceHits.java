import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SequenceHits implements Serializable {

    Collection<SequenceHit> sequenceHits;

    public SequenceHits() {
        this.sequenceHits = new ArrayList<>();
    }

    public Collection<SequenceHit> getSequenceHits() {
        return sequenceHits;
    }

    public boolean contains(SequenceHit sequenceHit) {
        return this.contains(sequenceHit);
    }

    public void add(SequenceHit sequenceHit) {
        sequenceHits.add(sequenceHit);
    }

    public boolean writeToFile(File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(sequenceHits);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public SequenceHit[] toArray() {
        int i = 0;
        SequenceHit[] hits = new SequenceHit[sequenceHits.size()];
        for (Iterator it = sequenceHits.iterator(); it.hasNext(); i++) {
            hits[i] = (SequenceHit) it.next();
        }
        return hits;
    }

    public boolean loadFromFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);

            this.sequenceHits = (Collection<SequenceHit>) in.readObject();

            fileInputStream.close();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean equals(SequenceHits sequenceHits) {
        SequenceHit[] hits = sequenceHits.toArray();
        for (SequenceHit hit : hits) {
            if(!hasHit(hit))
                return false;
        }
        return true;
    }

    public boolean hasHit(SequenceHit sequenceHit){
        for(SequenceHit hit : this.sequenceHits) {
            if (hit.equals(sequenceHit))
                return true;
        }
        return false;
    }
}