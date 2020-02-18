package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrintLetterWithNumberIntTest {

    @Test
    public void letterNumber() {
        PrintLetterWithNumber printLetterWithNumber = new PrintLetterWithNumber();
        assertEquals("a1b2c3e5e5", printLetterWithNumber.letterNumber("abcee"));
    }
}