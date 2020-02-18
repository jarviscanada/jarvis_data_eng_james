package ca.jrvs.practice.codingChallenge;

/**
 * Ticket Link:https://www.notion.so/Rotate-String-6a3e8d8c74f8416ca9acba7443584240
 */
public class RotateString {

    /**
     * Simple check
     *Big-O: O(n^2) time
     *       O(n) space
     * Justification:
     */
    public boolean rotateString(String s, String m) {
        if (s.length() != m .length()) {
            return false;
        }
        return (s + s).contains(m);
    }
}

