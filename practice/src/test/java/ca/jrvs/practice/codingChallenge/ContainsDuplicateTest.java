package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContainsDuplicateTest {

    @Test
    public void containsDuplicate1() {
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        assertEquals(true,  containsDuplicate.containsDuplicate1(new int[] {1,9,3,4,5,5,6}));
        assertEquals(false, containsDuplicate.containsDuplicate1(new int[] {1,2,9,5,6}));
    }

    @Test
    public void containsDuplicate2() {
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        assertEquals(true,  containsDuplicate.containsDuplicate2(new int[] {1,9,3,4,5,5,6}));
        assertEquals(false, containsDuplicate.containsDuplicate2(new int[] {1,2,9,5,6}));
    }
}