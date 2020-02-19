package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountPrimesIntTest {

    @Test
    public void countPrimes() {
        CountPrimes countPrimes = new CountPrimes();
        assertEquals(4, countPrimes.countPrimes(10));
    }
}