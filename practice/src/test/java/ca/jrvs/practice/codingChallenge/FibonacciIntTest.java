package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciIntTest {

    @Test
    public void fibonacci1() {
        Fibonacci fibonacci = new Fibonacci();
        assertEquals(fibonacci.fibonacci1(28), 317811);
    }

    @Test
    public void fibonacci2() {
        Fibonacci fibonacci = new Fibonacci();
        assertEquals(fibonacci.fibonacci2(28), 317811);
    }
}