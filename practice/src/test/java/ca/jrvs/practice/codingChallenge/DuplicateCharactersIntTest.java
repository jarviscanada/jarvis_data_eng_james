package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class DuplicateCharactersIntTest {

    @Test
    public void duplicateChar() {
        DuplicateCharacters duplicateCharacters = new DuplicateCharacters();
        assertArrayEquals(new char[] {'a','c'}, duplicateCharacters.duplicateChar("A black cat"));
    }
}