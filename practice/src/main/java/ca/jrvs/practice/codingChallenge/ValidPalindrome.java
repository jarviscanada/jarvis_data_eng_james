package ca.jrvs.practice.codingChallenge;

/**
 * Ticket link: https://www.notion.so/Valid-Palindrome-e0d9c1007ac84bd8a21ae6248f009a47
 */
public class ValidPalindrome {

    /**
     * Using two pointer
     *Big-O: O(n) time
     *       O(1) space
     * Justification: At most traverse the string once
     */
    public boolean validPalindrome1(String s) {
        int head = 0;
        int tail = s.length() - 1;

        while (head < tail) {
            while (!Character.isLetter(s.charAt(head)) && !Character.isDigit(s.charAt(head))) {
                head ++;
            }
            while (!Character.isLetter(s.charAt(tail)) && !Character.isDigit(s.charAt(tail))) {
                tail --;
            }
            if (Character.toLowerCase(s.charAt(tail)) != Character.toLowerCase(s.charAt(head))) {
                return false;
            }
            head++;
            tail--;
        }

        return true;

    }

    /**
     * Using Recursion
     *Big-O: O(n^2) time
     *       O(1) space
     * Justification: Recursion cost n^2
     */
    public boolean validPalindrome2(String s) {
        if (s.length() <= 1) {
            return true;
        }
        if (!Character.isLetter(s.charAt(0)) && !Character.isDigit(s.charAt(0))){
            return validPalindrome2(s.substring(1));
        }
        if (!Character.isLetter(s.charAt(s.length() - 1)) && !Character.isDigit(s.charAt(s.length() - 1))) {
            return validPalindrome2(s.substring(0, s.length() - 1));
        }

        if (Character.toLowerCase(s.charAt(0)) != Character.toLowerCase(s.charAt(s.length() - 1))) {
            return false;
        }
        return validPalindrome2(s.substring(1, s.length() - 1));
    }

}
