package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

/**
 * ticket link:https://www.notion.so/Valid-Anagram-7ceef7dd83624485a5fee7ea5cf41eb3
 */
public class ValidAnagram {

    /**
     * Using sort
     *Big-O: O(nlogn) time
     *       O(1) space
     * Justification: sort cost nlogn
     */
    public boolean validAnagram1(String s, String m) {
        if (s.length() !=  m.length()) return false;
        char[] sSort = s.toCharArray();
        char[] mSort = m.toCharArray();
        Arrays.sort(sSort);
        Arrays.sort(mSort);
        return Arrays.equals(mSort, sSort);
    }

    /**
     * O(n)
     *Big-O: O(n) time
     *       O(1) space
     * Justification: accessing table is constant time operation
     */
    public boolean validAnagram2(String s, String m) {
        if (s.length() !=  m.length()) return false;
        int[] list = new int[26];
        for (int i = 0; i < s.length(); i++) {
            list[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i <m.length(); i++) {
            list[m.charAt(i) - 'a']--;
            if (list[m.charAt(i) - 'a'] < 0) return false;
        }

        return true;
    }
}
