package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket link: https://www.notion.so/Two-Sum-a8d6e3e8e4044ab89fdcbc947baea3e4
 */
public class TwoSum {

    /**
     * Brute force
     *Big-O: O(n^2) time
     *       O(1) space
     * Justification: for each element we need to loop through the list
     */
    public int[] twoSum1(int[] list, int target) {
        for (int i = 0; i < list.length; i++) {
            for (int j = i+1; j < list.length; j++) {
                if (list[i] + list[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {};
    }

    /**
     * using sort
     *Big-O: O(nlogn) time
     *       O(n) space
     * Justification: at most go through the list once , and the sort function takes nlogn
     */
    public int[] twoSum2(int[] list, int target) {
        Arrays.sort(list);
        int i = 0, j = list.length -1;
        while (i < j) {
            int sum = list[i] + list[j];
            if (sum == target) {
                return new int[] {i, j};
            }else if (sum < target) {
                i++;
            } else {
                j++;
            }
        }
        return new int[] {};
    }

    /**
     * using hashmap
     *Big-O: O(n) time
     *       O(n) space
     * Justification: At most go through the list once
     */
    public int[] twoSum3(int[] list, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < list.length; i++) {
            int next = target - list[i];
            if (map.containsKey(next)) {
                return new int[] {map.get(next), i};
            }
            map.put(list[i], i);
        }
        return new int[] {};
    }
}
