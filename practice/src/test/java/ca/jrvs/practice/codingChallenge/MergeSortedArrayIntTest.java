package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MergeSortedArrayIntTest {

    @Test
    public void mergeSortedArray1() {
        MergeSortedArray mergeSortedArray = new MergeSortedArray();
        assertTrue(Arrays.equals(new int[]{1,2,2,3,5,6}, mergeSortedArray.mergeSortedArray1(new int[]{1,2,3,0,0,0},3,new int[]{2,5,6}, 3)));
    }
}