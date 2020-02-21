package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringtoIntegerIntTest {

    @Test
    public void stringtoInteger1() {
        StringtoInteger stringtoInteger = new StringtoInteger();
        assertEquals(stringtoInteger.stringtoInteger1("42"), 42);
        assertEquals(stringtoInteger.stringtoInteger1("   -42"), -42);
        assertEquals(stringtoInteger.stringtoInteger1("4193 with words"), 4193);
        assertEquals(stringtoInteger.stringtoInteger1("words and 987"), 0);
    }

    @Test
    public void stringtoInteger2() {
        StringtoInteger stringtoInteger = new StringtoInteger();
        assertEquals(stringtoInteger.stringtoInteger2("42"), 42);
        assertEquals(stringtoInteger.stringtoInteger2("   -42"), -42);
        assertEquals(stringtoInteger.stringtoInteger2("4193 with words"), 4193);
        assertEquals(stringtoInteger.stringtoInteger2("words and 987"), 0);
    }
}