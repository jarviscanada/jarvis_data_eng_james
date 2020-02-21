package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * ticket link: https://www.notion.so/Fibonacci-Number-Climbing-Stairs-5b3e9b2a995441debf8fdfa0cfcf232c
 */
public class Fibonacci {

    Map<Integer, Integer> map = new HashMap<>();

    /**
     *Big-O: O(2^n) time
     *       O(n) space
     * Justification: it takes exponential time.
     * The amount of operations needed, for each
     * level of recursion, grows exponentially
     * as the depth approaches N.
     */
    public int fibonacci1(int n) {
        if (n == 2 || n == 1) {
            return 1;
        }
        return fibonacci1(n - 1) + fibonacci1(n - 2);
    }

    /**
     *Big-O: O(n) time
     *       O(n) space
     * Justification: Each number, starting
     * at 2 up to and including N, is visited,
     * computed and then stored for O(1)O(1)
     * access later on.
     */
    public int fibonacci2(int n) {
        if (n == 2 || n == 1) {
            return 1;
        }
        if (!map.containsKey(n)) {
            int sum = fibonacci2(n - 1) + fibonacci2(n - 2);
            map.put(n, sum);
            return sum;
        }

        return map.get(n);
    }
}
