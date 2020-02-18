package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class RotateStringIntTest {

    @Test
    public void rotateString() {
        RotateString rotateString = new RotateString();
        assertEquals(true, rotateString.rotateString("abcde","cdeab"));
        assertEquals(false, rotateString.rotateString("abcde","abced"));
    }
}