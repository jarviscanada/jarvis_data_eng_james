package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TwoSumIntTest {

    private int[] list;
    private int[] result;
    @Before
    public void setUp() {
        list = new int[]{2,7,6,3,9,1,4,8};
        result = new int[]{1,4};
    }

    @Test
    public void twoSum1() {
        TwoSum twoSum = new TwoSum();
        int[] temp = twoSum.twoSum1(list,16);
        assertEquals(result[0], temp[0]);
        assertEquals(result[1], temp[1]);
    }

    @Test
    public void twoSum2() {
        TwoSum twoSum = new TwoSum();
        int[] temp = twoSum.twoSum2(list,16);
        Arrays.sort(list);
        assertEquals(16, list[temp[0]] + list[temp[1]]);
    }

    @Test
    public void twoSum3() {
        TwoSum twoSum = new TwoSum();
        int[] temp = twoSum.twoSum3(list,16);
        assertEquals(result[0], temp[0]);
        assertEquals(result[1], temp[1]);
    }
}