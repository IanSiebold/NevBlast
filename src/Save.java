import java.io.*;
import java.util.Collection;

public class Save implements Serializable{

    private SequenceHits sequenceHits;
    private BlastQuery blastQuery;

    public Save(){}

    public Save(BlastQuery blastQuery, SequenceHits sequenceHits){
        this.blastQuery = blastQuery;
        this.sequenceHits = sequenceHits;
    }

    public SequenceHits getSequenceHits() {
        return sequenceHits;
    }

    public BlastQuery getBlastQuery() {
        return blastQuery;
    }


    public boolean write(File file){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this);

            fileOutputStream.close();
            objectOutputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean read(File file){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);

            Save data = (Save)in.readObject();

            fileInputStream.close();
            in.close();

            this.sequenceHits = data.sequenceHits;
            this.blastQuery = data.blastQuery;
        }
        catch (IOException | ClassNotFoundException e){
            return false;
        }
        return true;
    }

    public boolean equals(Save save){
        return this.sequenceHits.equals(save.sequenceHits) && this.blastQuery.equals(save.blastQuery);
    }

}
