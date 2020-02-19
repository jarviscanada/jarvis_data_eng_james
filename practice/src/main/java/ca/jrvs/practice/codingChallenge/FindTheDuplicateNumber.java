package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ticket link:https://www.notion.so/Find-the-Duplicate-Number-29f10d219dbf4e0e85431ef480b9fc89
 */
public class FindTheDuplicateNumber {

    /**
     * Sorting
     *Big-O: O(nlogn) time
     *       O(1) space
     * Justification: sort cost nlogn
     */
    public int findDuplicate1(int[] list) {
        Arrays.sort(list);
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] == list[i+1]) {
                return list[i];
            }
        }
        return 0;
    }

    /**
     * set
     *Big-O: O(n) time
     *       O(n) space
     * Justification: travser the list at most once
     */
    public int findDuplicate2(int[] list) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < list.length; i++) {
            if (set.contains(list[i])) {
                return list[i];
            }
            set.add(list[i]);
        }

        return 0;
    }
}
