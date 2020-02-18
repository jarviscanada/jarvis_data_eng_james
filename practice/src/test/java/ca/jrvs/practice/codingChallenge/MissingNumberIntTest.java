package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class MissingNumberIntTest {

    @Test
    public void missingNumber1() {
        MissingNumber missingNumber = new MissingNumber();
        assertEquals(2, missingNumber.missingNumber1(new int[] {0,1,3}));
        assertEquals(8, missingNumber.missingNumber1(new int[] {9,6,4,2,3,5,7,0,1}));
    }

    @Test
    public void missingNumber2() {
        MissingNumber missingNumber = new MissingNumber();
        assertEquals(2, missingNumber.missingNumber2(new int[] {0,1,3}));
        assertEquals(8, missingNumber.missingNumber2(new int[] {9,6,4,2,3,5,7,0,1}));
    }

    @Test
    public void missingNumber3() {
        MissingNumber missingNumber = new MissingNumber();
        assertEquals(2, missingNumber.missingNumber3(new int[] {0,1,3}));
        assertEquals(8, missingNumber.missingNumber3(new int[] {9,6,4,2,3,5,7,0,1}));
    }
}