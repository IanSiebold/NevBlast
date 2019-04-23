import java.io.Serializable;

public class SignatureBit implements Serializable {
    int lineNumber;
    char aminoAcid;

    public SignatureBit(int lineNumber, char aminoAcid) {
        this.lineNumber = lineNumber;
        this.aminoAcid = aminoAcid;
    }
    public SignatureBit(){
        //default constructor
    }

    @Override
    public String toString() {
        return "SignatureBit{" + "lineNumber=" + lineNumber + ", aminoAcid=" + aminoAcid + '}';
    }

    //----------------------------------------
    //Getters and setters
    //----------------------------------------
    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public char getAminoAcid() {
        return aminoAcid;
    }

    public void setAminoAcid(char aminoAcid) {
        this.aminoAcid = aminoAcid;
    }

}