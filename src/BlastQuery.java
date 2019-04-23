import org.forester.archaeopteryx.tools.Blast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlastQuery implements Serializable {
    private String queryName;
    private String fastaSequence;
    private String sigOneString;
    private String sigTwoString;
    private ArrayList<SignatureBit> sigOneBits;
    private ArrayList<SignatureBit> sigTwoBits;
    private double eVal;
    private int resultCount;
    private String taxonomy;
    private String entrez;

    private static final int NO_ERROR = 0;
    private static final int SEQUENCE_ERROR = 1;
    private static final int COUNT_ERROR = 2;
    private static final int EMPTY_ERROR = 3;
    private static final int MISSING_ELEMENT_ERROR = 4;

    /**
     * This method constructs the BlastQuery object, and send info to be validated.
     * @author: Eric Albrecht (albrechter@msoe.edu)
     * @param queryName - name of query for file saving purposes.
     * @param fastaSequence - amino acid sequence to be requested.
     * @param signatureOne - first sequence to score, x axis of graph
     * @param signatureTwo - second sequence to score against, y axis of graph
     * @param eVal - E Value of the sequence
     * @param resultCount - desired number of results
     * @param taxonomy - saveable utility for scientists to review.
     */
    public BlastQuery(String queryName, String fastaSequence, String signatureOne, String signatureTwo, String eVal, String resultCount, String taxonomy) throws IllegalArgumentException{
        validateAminoAcid(fastaSequence);
        validateResultCount(resultCount);
        validateEVal(eVal);
        ArrayList<String> parsedStrings = validateSignature(signatureOne, true);
        ArrayList<String> secondParsedStrings = validateSignature(signatureTwo, false);

        this.sigOneString = signatureOne;
        this.sigTwoString = signatureTwo;
        this.sigOneBits = createBitsFromSignature(parsedStrings); //todo
        this.sigTwoBits = createBitsFromSignature(secondParsedStrings);
        setEntrez(taxonomy);
        setFasta(fastaSequence);
        this.eVal = Double.parseDouble(eVal);
        this.resultCount = Integer.parseInt(resultCount);
        this.queryName = queryName;
        this.fastaSequence = fastaSequence;
        this.taxonomy = taxonomy;
    }

    /**
     * This method validates the result count is present, and is an int.
     * @param resultCount number of results desired
     * @return True if the result count is valid, False if not
     */
    public boolean validateResultCount(String resultCount) throws IllegalArgumentException{
        if(!resultCount.equals("") && resultCount.matches("^[0-9]+$")) {
            return true;
        }
        handleUserEntryError("Make sure the Result count is not empty, and is a valid integer!");
        return false;
    }

    /**
     * This method validates the E Value to be used in the BLAST API.
     * @param eVal The string to be checked for if valid E Value
     * @return True if valid eVal, False if not.
     */
    public boolean validateEVal(String eVal) throws IllegalArgumentException{
        if(!eVal.equals("") && eVal.matches("^[0-9]+\\.+[0-9e]+$")) {
            return true;
        }
        handleUserEntryError("Make sure the E Value is not empty, and is a valid number!");
        return false;
    }

    public void setEntrez(String taxonomy){
        this.entrez= taxonomy;
    }
    public String getEntrez(){
        return this .entrez;
    }

    /**
     * This method validates the characteristics of an amino acid.
     * @author: Eric Albrecht (albrechter@msoe.edu)
     * @param aminoAcid - input sequence from user.
     * @return - True if the amino acid is valid, false otherwise.
     */
    public boolean validateAminoAcid(String aminoAcid) throws IllegalArgumentException{
        if(aminoAcid.equals("")) {
            handleUserEntryError("Sequence cannot be empty.");
            return false;
        } else if (!aminoAcid.matches("^[MSTVLFNQDECWKRPAIHG]+$")) {
            handleUserEntryError("Sequence must contain only characters from the following sequence: MSTVLFNQDECWKRPAIHG");
        }
        return true;
    }

    /**
     * This method validates the String is a proper sequence to searched by BLAST API
     * @param signature - letter/number sequence showing what bits to look for and how often
     * @return - True if valid String, false otherwise.
     */
    public ArrayList<String> validateSignature(String signature, boolean sigOne) throws IllegalArgumentException{
        ArrayList<String> inputList = new ArrayList<>();
        Pattern pattern = Pattern.compile("[A-Z](\\d+)");
        Matcher matcher = pattern.matcher(signature);
        while(matcher.find()){
            inputList.add(matcher.group());
        }
        /*
        while(inputList.remove(""));
        if(inputList.size() %2 != 0 || inputList.size() == 0) {
            System.out.println("cant be odd");
            handleUserEntryError("Make sure Signature " + (sigOne?" 1 ":" 2 ") + " is not empty, and has the sequence of LETTERS SPACE COUNT SPACE.");

        }
        for(int i = 0; i < inputList.size(); i+=2) {
            if(!inputList.get(i).matches("^[A-Za-z]+$")) {
                System.out.println("bad letter");
                handleUserEntryError("Error: Element " + i + " is not a letter sequence.");
            }
        }
        for(int i = 1; i < inputList.size(); i+=2) {
            if(!inputList.get(i).matches("^[0-9]+$")) {
                System.out.println("cant be odd");
                handleUserEntryError("Error: Element " + i + " is not a number");
            }
        }
        if(sigOne) {
            this.sigOneString = signature.toUpperCase();
        } else {
            this.sigTwoString = signature.toUpperCase();
        }
        handleUserEntryError("");
        */
        return inputList;
    }

    /**
     * This method creates letter/number pairings on the
     * @param signature - String signature the user input.
     * @return
     */
    public ArrayList<SignatureBit> createBitsFromSignature(ArrayList<String> signature) {
        ArrayList<SignatureBit> bits = new ArrayList<SignatureBit>();
        for(int i = 0; i < signature.size(); i++) {
            bits.add(new SignatureBit(Integer.parseInt(signature.get(i).substring(1)), signature.get(i).charAt(0)));
        }
        return bits;
    }

    /**
     * This method gets the amino acid.
     * @return String with amino acid.
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * This method sets the query name for the loaded files.
     * ONLY USE FOR LOADING, USE CONSTRUCTOR OTHERWISE
     * @param queryName -> name from loaded file
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    /**
     * This method sets the BlastQuery's signature from the loaded file.
     * ONLY USE FOR LOADING, ASSUMES FILE HAS BEEN VALIDATED.
     * @param signatureOne -
     */
    public void setSignatureOne(String signatureOne) {
        this.sigOneString = signatureOne;
        this.sigOneBits = this.createBitsFromSignature(validateSignature(signatureOne, true));//todo hard fucked
    }

    /**
     * This method gets the String representation of sig one to save off to a file.
     * @return - String of signature one.
     */
    public String getSignatureOneString() {
        return this.sigOneString;
    }

    /**
     * This method returns the Signature bits for signature one.
     * @return ArrayList of Signature Bits if instantiated.
     */
    public ArrayList<SignatureBit> getSignatureOneBits() {
        return sigOneBits;
    }

    /**
     * This method prints out the signature array
     * @return string with arraylist elements
     */
    public String getSignatureString(ArrayList<SignatureBit> bits) {
        String output = "";
        for(int i = 0; i < bits.size(); i++) {
            output += bits.get(i).getAminoAcid() + " " + bits.get(i).getLineNumber() + " ";
        }
        return output;
    }

    public String getSignatureTwoString() {
        return sigTwoString;
    }

    /**
     * This method returns the Signature bits for signature two.
     * @return Arraylist of Signature Bits for signature two.
     */
    public ArrayList<SignatureBit> getSignatureTwoBits() {
        return sigTwoBits;
    }

    /**
     * This method sets the signature two from the loaded file
     * @param signatureTwo - string loaded from the file.
     */
    public void setSignatureTwo(String signatureTwo) {
        this.sigTwoString = signatureTwo;
        this.sigTwoBits = createBitsFromSignature(validateSignature(signatureTwo, false));
    }

    /**
     * This method returns the E Val for the BLAST Query
     * @return double with eVal
     */
    public double getEVal() {
        return eVal;
    }

    /**
     * This method sets the EVal from the String read in.
     * @param eVal
     */
    public void setEVal(String eVal) {
        this.eVal = Double.parseDouble(eVal);
    }

    /**
     * This method gets the result count of desired results from Blast Query
     * @return number of results desired.
     */
    public int getResultCount() {
        return resultCount;
    }

    /**
     * This method sets the result count from the string read in from the load handler.
     * @param resultCount
     */
    public void setResultCount(String resultCount) {
        this.resultCount = Integer.parseInt(resultCount);
    }

    /**
     * This method sets the taxonomy, and function will be determined at later date.
     * @param taxonomy Taxonomy from NCBI database.
     */
    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    /**
     * This method returns the taxonomy.
     * @return desired taxonomy.
     */
    public String getTaxonomy() {
        return taxonomy;
    }

    /**
     * This method handles the errors, will be determined upon integration.
     * @param message message to be sent to user.
     */
    public void handleUserEntryError(String message) throws IllegalArgumentException{
        throw new IllegalArgumentException(message);
    }

    /**
     * This constructor is for testing purposes.
     */
    public BlastQuery() {

    }

    /**
     * This method gets the Fasta from the Blast Query to be saved off.
     * @return - String of fasta.
     */
    public String getFastaSequence() {
        return this.fastaSequence;
    }

    /**
     * This method sets the fasta from a read in String from the load handler.
     * @param fasta - String read in
     */
    public void setFasta(String fasta) {
        this.fastaSequence = fasta;
    }

    /**
     * this clears the object, so that new data can be read in, or old data cleared.
     */
    public void clearBlastQuery() {
        this.queryName = "";
        this.fastaSequence = "";
        this.sigOneBits = null;
        this.sigOneString = "";
        this.sigTwoBits = null;
        this.sigTwoString = null;
        this.eVal = 0.0;
        this.resultCount = 0;
    }

    public boolean equals(BlastQuery blastQuery){
        boolean sentinel = true;
        sentinel = sentinel && this.queryName.equals(blastQuery.getQueryName());
        sentinel = sentinel && this.fastaSequence.equals(blastQuery.getFastaSequence());
        sentinel = sentinel && this.sigOneString.equals(blastQuery.getSignatureOneString());
        sentinel = sentinel && this.sigTwoString.equals(blastQuery.getSignatureTwoString());
        sentinel = sentinel && this.eVal == blastQuery.getEVal();
        sentinel = sentinel && this.resultCount == blastQuery.getResultCount();
        return sentinel;
    }
}