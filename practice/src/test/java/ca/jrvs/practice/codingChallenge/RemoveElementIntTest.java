package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveElementIntTest {

    @Test
    public void remove() {
        RemoveElement removeElement = new RemoveElement();
        assertEquals(2,removeElement.remove(new int[] {2,2,3,3} , 3));
        assertEquals(5,removeElement.remove(new int[] {2,4,6,7,2,3,3} , 2));
    }
}