package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FindTheDuplicateNumberIntTest {

    @Test
    public void findDuplicate1() {
        FindTheDuplicateNumber findTheDuplicateNumber = new FindTheDuplicateNumber();
        assertEquals(2, findTheDuplicateNumber.findDuplicate1(new int[] {1,3,2,4,2}));
        assertEquals(3, findTheDuplicateNumber.findDuplicate1(new int[] {1,3,2,4,5,3,8}));
    }

    @Test
    public void findDuplicate2() {
        FindTheDuplicateNumber findTheDuplicateNumber = new FindTheDuplicateNumber();
        assertEquals(2, findTheDuplicateNumber.findDuplicate2(new int[] {1,3,2,4,2}));
        assertEquals(3, findTheDuplicateNumber.findDuplicate2(new int[] {1,3,2,4,5,3,8}));
    }
}