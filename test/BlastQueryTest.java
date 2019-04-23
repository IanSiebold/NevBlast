import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlastQueryTest {


    private BlastQuery bq;

    /**
     * This constructs the object before each test.
     */
    @BeforeEach
    void setUp() {
        bq = new BlastQuery();
    }

    /**
     * This method deconstructs the method after every test.
     */
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        bq = null;
    }

    /**
     * This method tests that an empty signature passed into the validateSignature method throws an Illegal
     * Argument Exception.
     */
    @Test
    void testEmptySignatureThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bq.validateSignature("", true);
        });
    }

    /**
     * This method tests that an out of order signature passed into validateSignature throws an Illegal
     * Argument Exception.
     */
    @Test
    void testOutOfOrderSpacesSignatureThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()-> {
            bq.validateSignature("C 4 4 R", true);
        });
    }

    /**
     * This method tests that a signature with no spaces passed into validateSignature throws an Illegal
     * Argument Exception.
     */
    @Test
    void testNoSpacesSignatureThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bq.validateSignature("C4C4", true);
        });
    }

    /**
     * This method tests that a signature missing an element passed into validateSignature throws an Illegal
     * Argument Exception.
     */
    @Test
    void testMissingElementThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bq.validateSignature("C 4 R", true);
        });
    }

    /**
     * This method tests that a valid signature padded into validateSignature does not throw an exception.
     */
    @Test
    void testValidSignatureDoesntThrowException() {
        assertDoesNotThrow(() -> {
            bq.validateSignature("C 4 R 23", true);
        });
    }

    /**
     * This method tests that an empty amino acid passed into validateAminoAcid throws an Illegal Argument Exception.
     */
    @Test
    void testEmptyAminoAcidThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bq.validateAminoAcid("");
        });
    }

    /**
     * This method tests that a nonempty amino acid passed into a validateAminoAcid does not throw an exception.
     */
    @Test
    void testNonEmptyAminoAcidDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            bq.validateAminoAcid("test");
        });
    }

    /**
     * This method tests that the correct signature bits are created when createBitsFromSignature is called.
     */
    @Test
    void testCreateBitsReturnsCorrectArrayList() {
        ArrayList<SignatureBit> bits = new ArrayList<>();
        bits.add(new SignatureBit(4, 'C'));
        bits.add(new SignatureBit(23, 'R'));

        ArrayList<String> tests = new ArrayList<>();
        tests.add("C");
        tests.add("4");
        tests.add("R");
        tests.add("23");
        ArrayList<SignatureBit> newBits = bq.createBitsFromSignature(tests);

        if(bits.size() != newBits.size()){
            fail();
        }

        for(int i = 0; i < bits.size(); i++) {
            if(!new Character(newBits.get(i).getAminoAcid()).equals(bits.get(i).getAminoAcid()) || newBits.get(i).getLineNumber() != bits.get(i).getLineNumber()) {
                fail();
            }
        }
    }

    /**
     * This method tests that a non-numeric result count passed into validateResultCount throws an Illegal
     * Argument Exception.
     */
    @Test
    void validateNonNumericResultCountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bq.validateResultCount("A");
        });
    }

    /**
     * This method tests that a valid, integer numeric result passed into the validateResultCount doesn't throw
     * an Exception.
     */
    @Test
    void validateNumericResultCountDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            bq.validateResultCount("4");
        });
    }

    /**
     * This method validates that an empty result count passed into validateResultCount throws an Illegal
     * Argument Exception.
     */
    @Test
    void validateEmptyResultCountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            bq.validateResultCount("");
        });
    }

    /**
     * This method validates that passing an empty EVal into the validateEVal method throws an Illegal Argument
     * Exception.
     */
    @Test
    void validateEmptyEValThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()-> {
            bq.validateEVal("");
        });
    }

    /**
     * This method tests that a number without a decimal throws an Illegal Argument Exception.
     */
    @Test
    void validateNoDecimalEValThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()-> {
            bq.validateEVal("34");
        });
    }

    /**
     * This method tests that a valid decimal number doesn't throw an exception.
     */
    @Test
    void validateDecimalEValDoesNotThrowException() {
        assertDoesNotThrow(()->{
            bq.validateEVal("2.3");
        });
    }
}