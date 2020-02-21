package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ticket link:https://www.notion.so/Contains-Duplicate-6fc4349819804d719cd173715c881c19
 */
public class ContainsDuplicate {

    /**
     * Brute force
     *Big-O: O(nlogn) time
     *       O(1) space
     * Justification: sorting cost nlogn
     */
    public boolean containsDuplicate1(int[] list) {
        Arrays.sort(list);
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] == list[i+1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Using set
     *Big-O: O(n) time
     *       O(n) space
     * Justification:traverse through the list
     */
    public boolean containsDuplicate2(int[] list) {

        Set<Integer> set = new HashSet<>(list.length);
        for (int i = 0; i < list.length; i++) {
            if (set.contains(list[i])) {
                return true;
            }
            set.add(list[i]);
        }
        return false;
    }
}
