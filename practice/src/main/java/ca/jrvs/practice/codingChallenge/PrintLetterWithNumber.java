package ca.jrvs.practice.codingChallenge;

/**
 * Ticket link:https://www.notion.so/Print-letter-with-number-5e5819396fa349cdbf3b0e5d47d665af
 */
public class PrintLetterWithNumber {
    /**
     *Big-O: O(n) time
     *       O(1) space
     * Justification: loop through the string once
     */

    public String letterNumber(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            result += String.valueOf(s.charAt(i));
            int temp = s.charAt(i) - 'a' + 1;
            result += String.valueOf(temp);
        }

        return result;
    }
}
