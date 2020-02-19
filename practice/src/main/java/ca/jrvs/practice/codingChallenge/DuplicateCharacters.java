package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ticket link:https://www.notion.so/Duplicate-Characters-7483f45fab18499ca5a051896f805e87
 */
public class DuplicateCharacters {

    /**
     *Big-O: O(n) time
     *       O(n) space
     * Justification: travser through the string once
     */
    public char[] duplicateChar(String s) {
        Set<Character> set = new HashSet<>();
        String result="";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ' && set.contains(Character.toLowerCase(s.charAt(i))) && !result.contains(String.valueOf(Character.toLowerCase(s.charAt(i))))) {
                result = result + String.valueOf(Character.toLowerCase(s.charAt(i)));
            } else {
                set.add(Character.toLowerCase(s.charAt(i)));
            }
        }
        return result.toCharArray();
    }
}
