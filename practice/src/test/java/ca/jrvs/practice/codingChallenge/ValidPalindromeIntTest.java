package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeIntTest {

    @Test
    public void validPalindrome1() {
        ValidPalindrome validPalindrome = new ValidPalindrome();
        assertEquals(true,validPalindrome.validPalindrome1("A man, a plan, a canal: Panama"));
        assertEquals(false, validPalindrome.validPalindrome1("race a car"));
    }

    @Test
    public void validPalindrome2() {
        ValidPalindrome validPalindrome = new ValidPalindrome();
        assertEquals(true,validPalindrome.validPalindrome2("A man, a plan, a canal: Panama"));
        assertEquals(false, validPalindrome.validPalindrome2("race a car"));
    }
}