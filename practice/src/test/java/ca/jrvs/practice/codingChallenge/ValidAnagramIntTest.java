package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidAnagramIntTest {

    @Test
    public void validAnagram1() {
        ValidAnagram validAnagram = new ValidAnagram();
        assertEquals(true, validAnagram.validAnagram1("anagram", "nagaram"));
        assertEquals(false, validAnagram.validAnagram1("rat", "car"));
    }

    @Test
    public void validAnagram2() {
        ValidAnagram validAnagram = new ValidAnagram();
        assertEquals(true, validAnagram.validAnagram2("anagram", "nagaram"));
        assertEquals(false, validAnagram.validAnagram2("rat", "car"));
    }
}