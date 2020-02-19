package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class DuplicatesFromSortedArrayIntTest {

    @Test
    public void countDuplicate() {
        DuplicatesFromSortedArray duplicatesFromSortedArray = new DuplicatesFromSortedArray();
        assertEquals(2, duplicatesFromSortedArray.countDuplicate(new int[] {1,1,2}));
        assertEquals(5, duplicatesFromSortedArray.countDuplicate(new int[] {0,0,1,1,1,2,2,3,3,4}));

    }
}