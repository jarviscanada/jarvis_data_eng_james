package ca.jrvs.practice.codingChallenge;

/**
 * Ticket link:https://www.notion.so/Count-Primes-2fe16d8736ac4b599780f184e8e4e33b
 */
public class CountPrimes {
    /**
     *Big-O: O(n^2) time
     *       O(1) space
     * Justification: for each element we need to loop through the list
     */
    public int countPrimes(int num) {
        int count = 0;
        for (int i = 2; i <= num; i++) {
            if (isPrime(i)) count++;
        }
        return count;
    }

    /**
     * helper function to test if number is prime
     */
    private boolean isPrime(int num) {
        for (int i =2;(int) i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
