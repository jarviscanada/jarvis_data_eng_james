package ca.jrvs.practice.codingChallenge;

/**
 * Ticket link: https://www.notion.so/String-to-Integer-atoi-a531800ca2b04789a663aadfccbb550c
 */
public class StringtoInteger {

    /**
     * using Api
     *Big-O: O(n) time
     *       O(1) space
     * Justification: At most go through the string once
     */
    public int stringtoInteger1(String s) {
        int curr = 0;
        int result = 0;
        //clear white space in front of the string
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                curr++;
            } else {
                break;
            }
        }

        //check if the string is only whitespace
        if (curr == s.length()) {
            return 0;
        }

        int sign = 1;
        //check if the next is a sign
        if (s.charAt(curr) == '-') {
            sign = -1;
            curr++;
        } else if (s.charAt(curr) == '+') {
            curr++;
        } else if (!Character.isDigit(s.charAt(curr))) {
            return 0;
        }

        for (int i = curr; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                result = result * 10 + Integer.parseInt(String.valueOf(c));
            } else {
                break;
            }
        }
        result = result * sign;
        if (result < (Math.pow(2,31) -1) && result > (-Math.pow(2,31))) {
            return result;
        }
        return 0;

    }

    /**
     * without using Api
     *Big-O: O(n) time
     *       O(1) space
     * Justification: At most go through the string once
     */
    public int stringtoInteger2(String s) {
        int curr = 0;
        int result = 0;
        //clear white space in front of the string
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                curr++;
            } else {
                break;
            }
        }

        //check if the string is only whitespace
        if (curr == s.length()) {
            return 0;
        }

        int sign = 1;
        //check if the next is a sign
        if (s.charAt(curr) == '-') {
            sign = -1;
            curr++;
        } else if (s.charAt(curr) == '+') {
            curr++;
        } else if (!Character.isDigit(s.charAt(curr))) {
            return 0;
        }

        for (int i = curr; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                result = result * 10 + (c - '0');
            } else {
                break;
            }
        }
        result = result * sign;
        if (result < (Math.pow(2,31) -1) && result > (-Math.pow(2,31))) {
            return result;
        }
        return 0;
    }
}
